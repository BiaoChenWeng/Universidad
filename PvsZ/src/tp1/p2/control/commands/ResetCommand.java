package tp1.p2.control.commands;

import tp1.p2.control.Command;

import tp1.p2.control.Level;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.CommandParseException;

public class ResetCommand extends Command {

	private Level level;

	private long seed;

	public ResetCommand() {
		
	}

	public ResetCommand(Level level, long seed) {
		this.level = level;
		this.seed = seed;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_RESET_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_RESET_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_RESET_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_RESET_HELP;
	}

	@Override
	public boolean execute(GameWorld game)throws GameException{

		if(this.level== null ) {
			game.reset();	
		}
		else {
			game.reset(this.level,this.seed);
		}
		System.out.println(String.format(Messages.CONFIGURED_LEVEL, game.getLevel()));
		System.out.println(String.format(Messages.CONFIGURED_SEED,game.getSeed()));		
		return (true);
		
	}

	@Override
	public Command create(String[] parameters) throws GameException {
		if(parameters.length==1) {
			return new ResetCommand();
		}
		else {
			try {
				Level level= Level.valueOfIgnoreCase(parameters[1]);
				if(level==null)
					throw new CommandParseException(Messages.INVALID_COMMAND);
				long seed =Long.parseLong(parameters[2]);				
				return new ResetCommand(level,seed);
			}
			catch(NumberFormatException a ) {
				throw new CommandParseException(Messages.INVALID_COMMAND);
			}
		}
	}

}
