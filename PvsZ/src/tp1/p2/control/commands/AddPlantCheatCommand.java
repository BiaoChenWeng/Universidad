package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.InvalidPositionException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.view.Messages;
public class AddPlantCheatCommand extends AddPlantCommand implements Cloneable {

	private int col;

	private int row;

	private String plantName;

	private boolean consumeCoins;

	public AddPlantCheatCommand(String name, int col,int row) {
		super(name,col,row,false);	
		
	}
	public AddPlantCheatCommand() {
		this(false);
	}

	public AddPlantCheatCommand(boolean consumeCoins) {
		this.consumeCoins = consumeCoins;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_CHEAT_PLANT_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CHEAT_PLANT_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CHEAT_PLANT_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CHEAT_PLANT_HELP;
	}

	

	@Override
	public Command create(String[] parameters) throws GameException{
		if(parameters.length<=3  ) {
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
			
		}
		else if(!PlantFactory.isValidPlant(parameters[1])) {
			throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
		}
		
		try {
			this.plantName=parameters[1];
			this.col = Integer.parseInt(parameters[2]);
            this.row = Integer.parseInt(parameters[3]);
            
            
            return new AddPlantCheatCommand(plantName,col,row);
		}
		catch(NumberFormatException a ) {
			throw new CommandParseException(Messages.INVALID_POSITION,parameters[2],parameters[3],a);
			
		}

	}

}