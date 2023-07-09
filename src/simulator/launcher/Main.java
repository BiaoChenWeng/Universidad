package simulator.launcher;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.json.JSONObject;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.Body;
import simulator.model.ForceLaws;
import simulator.model.PhysicsSimulator;
import simulator.view.MainWindow;

public class Main {

	// default values for some parameters
	//
	private final static Integer _stepsDefaultValue = 150;
	private final static Double _dtimeDefaultValue = 2500.0;
	private final static String _forceLawsDefaultValue = "nlug";
	private final static String _outputDefaultValue = null;
	private final static String _modeDefaultValue = "gui";
	// some attributes to stores values corresponding to command-line parameters
	//
	private static Integer _steps = null;
	private static Double _dtime = null;
	private static String _inFile = null;
	private static String _outFile = null;
	private static JSONObject _forceLawsInfo = null;
	private static String _executionMode = null;

	// factories
	private static Factory<Body> _bodyFactory;
	private static Factory<ForceLaws> _forceLawsFactory;

	/**
	 * Create and Initialize the factories
	 * 
	 */

	private static void initFactories() {
		List<Builder<Body>> bodyBuilders = new ArrayList<>();
		bodyBuilders.add(new MovingBodyBuilder());
		bodyBuilders.add(new StationaryBodyBuilder());
		bodyBuilders.add(new TempSensBodyBuilder());
		Main._bodyFactory = new BuilderBasedFactory<Body>(bodyBuilders);

		List<Builder<ForceLaws>> forceLawsBuilders = new ArrayList<>();
		forceLawsBuilders.add(new MovingTowardsFixedPointBuilder());
		forceLawsBuilders.add(new NewtonUniversalGravitationBuilder());
		forceLawsBuilders.add(new NoForceBuilder());
		forceLawsBuilders.add(new MovingAroundBodyGreaterMassBuilder());
		Main._forceLawsFactory = new BuilderBasedFactory<ForceLaws>(forceLawsBuilders);

	}

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseExecutionModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseDeltaTimeOption(line);
			parseForceLawsOption(line);
			Main.parseOutFileOption(line);
			Main.parseStepOption(line);
			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		// execute mode
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc(
				"Execution Mode. Possible values: 'batch' (Batch mode), 'gui' (Graphical User Interface mode).Default value:'"
						+ Main._modeDefaultValue + "'.")
				.build());

		// output
		cmdLineOptions
				.addOption(Option.builder("o").longOpt("output").hasArg().desc("Bodies JSON output file.").build());
		// steps
		cmdLineOptions.addOption(Option.builder("s").longOpt("step").hasArg()
				.desc("A Integer representing the simulation step. Default value:" + Main._stepsDefaultValue + ".")
				.build());

		// help
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());

		// input file
		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Bodies JSON input file.").build());

		// delta-time
		cmdLineOptions.addOption(Option.builder("dt").longOpt("delta-time").hasArg()
				.desc("A double representing actual time, in seconds, per simulation step. Default value: "
						+ _dtimeDefaultValue + ".")
				.build());

		// force laws
		cmdLineOptions.addOption(Option.builder("fl").longOpt("force-laws").hasArg()
				.desc("Force laws to be used in the simulator. Possible values: "
						+ factoryPossibleValues(_forceLawsFactory) + ". Default value: '" + _forceLawsDefaultValue
						+ "'.")
				.build());

		return cmdLineOptions;
	}

	/**
	 * @param factory Factory
	 * @return a string with all the possible value for a given factory
	 */
	public static String factoryPossibleValues(Factory<?> factory) {
		String s = "";
		if (factory != null) {

			for (JSONObject fe : factory.getInfo()) {
				if (s.length() > 0) {
					s = s + ", ";
				}
				s = s + "'" + fe.getString("type") + "' (" + fe.getString("desc") + ")";
			}

			s = s + ". You can provide the 'data' json attaching :{...} to the tag, but without spaces.";
		} else {
			s = "No values found";
		}
		return s;
	}

	/**
	 * @param CommandLine if the commandLine does have the parameter h it will print
	 *                    a help text
	 * 
	 */

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	/**
	 * @param CommandLine
	 * 
	 * @throws ParseException if the CommandLine does not have the argument i
	 */

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && !_executionMode.equals("gui") ) {
			throw new ParseException("In batch mode an input file of bodies is required");
		}
	}

	/**
	 * @param CommandLine if the commandLine does not have the parameter o it will
	 *                    take a default value: the console
	 */

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		Main._outFile = line.getOptionValue("o", Main._outputDefaultValue);
	}

	/**
	 * @param put a value in _steps, if the value does not exist it will put a
	 *            default value
	 * @throws throw a ParseException if the value you give is not valid
	 */

	private static void parseStepOption(CommandLine line) throws ParseException {
		String steps = line.getOptionValue("s", Main._stepsDefaultValue.toString());
		try {
			Main._steps = Integer.parseInt(steps);
			assert (Main._steps > 0);
		} catch (AssertionError | NumberFormatException exception) {
			throw new ParseException("Invalid steps values: " + steps);
		}
	}

	/**
	 * @param CommandLine if the commandLine does not have the parameter dt it will
	 *                    take a default value
	 * @throws ParseException if the value is invalid or is zero or negative
	 */

	private static void parseDeltaTimeOption(CommandLine line) throws ParseException {
		String dt = line.getOptionValue("dt", _dtimeDefaultValue.toString());
		try {
			_dtime = Double.parseDouble(dt);
			assert (_dtime > 0);
		} catch (Exception e) {
			throw new ParseException("Invalid delta-time value: " + dt);
		}
	}

	private static void validateMode(String mode) throws ParseException{
		switch (mode){
		case "gui":	
			break;
		case "hui":
			break;
		default:
			throw new ParseException("Invalid mode: "+_executionMode);	
		}
	}
	
	private static void parseExecutionModeOption(CommandLine line) throws ParseException {
		_executionMode = line.getOptionValue("m", _modeDefaultValue);
		validateMode(_executionMode);
	}

	private static JSONObject parseWRTFactory(String v, Factory<?> factory) {

		// the value of v is either a tag for the type, or a tag:data where data is a
		// JSON structure corresponding to the data of that type. We split this
		// information
		// into variables 'type' and 'data'
		//
		int i = v.indexOf(":");
		String type = null;
		String data = null;
		if (i != -1) {
			type = v.substring(0, i);
			data = v.substring(i + 1);
		} else {
			type = v;
			data = "{}";
		}

		// look if the type is supported by the factory
		boolean found = false;
		if (factory != null) {
			for (JSONObject fe : factory.getInfo()) {
				if (type.equals(fe.getString("type"))) {
					found = true;
					break;
				}
			}
		}

		// build a corresponding JSON for that data, if found
		JSONObject jo = null;
		if (found) {
			jo = new JSONObject();
			jo.put("type", type);
			jo.put("data", new JSONObject(data));
		}
		return jo;

	}

	/**
	 * @param put a value in _forceLawsInfo, if the value does not exist it will put
	 *            a default value
	 * @throws throw a ParseException if the forceLaws is invalid
	 */
	private static void parseForceLawsOption(CommandLine line) throws ParseException {
		String fl = line.getOptionValue("fl", _forceLawsDefaultValue);
		_forceLawsInfo = parseWRTFactory(fl, _forceLawsFactory);
		if (_forceLawsInfo == null) {
			throw new ParseException("Invalid force laws: " + fl);
		}
	}

	/**
	 * Create the instance of the simulator and controller load the data and write
	 * in a output file the result in json format
	 */

	private static void startBatchMode() throws Exception {

		PhysicsSimulator simulator = new PhysicsSimulator(Main._forceLawsFactory.createInstance(_forceLawsInfo),
				Main._dtime);
		Controller controlador = new Controller(simulator, Main._forceLawsFactory, Main._bodyFactory);
		controlador.loadData(new FileInputStream(Main._inFile));

		if (Main._outputDefaultValue == Main._outFile) {
			controlador.run(Main._steps, System.out);
		} else {
			controlador.run(Main._steps, new FileOutputStream(Main._outFile));
		}

	}

	private static void startGUIMode() throws Exception {

		PhysicsSimulator simulator = new PhysicsSimulator(Main._forceLawsFactory.createInstance(_forceLawsInfo),
				Main._dtime);
		Controller controlador = new Controller(simulator, Main._forceLawsFactory, Main._bodyFactory);
		if (Main._inFile != null) {
			controlador.loadData(new FileInputStream(Main._inFile));
		}
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				new MainWindow(controlador);
			}
		});
	}

	private static void start(String[] args) throws Exception {
		parseArgs(args);
		switch (_executionMode){
		case "gui":
			startGUIMode();
			break;
		case "hui":
			startBatchMode();
			break;
		default:
			throw new ParseException("Invalid mode: "+_executionMode);
		}
	
	}

	public static void main(String[] args) {

		try {
			initFactories();
			start(args);
		} catch (Exception e) {
			System.err.println("Something went wrong ...");
			System.err.println();
			e.printStackTrace();
		}
	}
}
