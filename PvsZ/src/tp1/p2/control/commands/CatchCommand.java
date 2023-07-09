package tp1.p2.control.commands;

import tp1.p2.control.Command;
import tp1.p2.logic.GameWorld;

import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandExecuteException;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;

public class CatchCommand extends Command {

	private static boolean caughtSunThisCycle = false;

	private int col;

	private int row;

	public CatchCommand() {
		caughtSunThisCycle = false;
	}
	
	@Override
	protected void newCycleStarted() {
		caughtSunThisCycle = false;
	}

	private CatchCommand(int col, int row) {
		this.col = col;
		this.row = row;
		
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_CATCH_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_CATCH_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_CATCH_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_CATCH_HELP;
	}

	@Override
	public boolean execute(GameWorld game)throws GameException {
		if(this.caughtSunThisCycle) {
			throw new CommandExecuteException(Messages.SUN_ALREADY_CAUGHT);
		}
		else  {
			game.isPositionLimit(this.row,this.col);
			game.tryToCatchObject(col, row);			
			this.caughtSunThisCycle=true;
			return (true);
		}

	}

	@Override
	public Command create(String[] parameters) throws GameException{
		if(parameters.length<=2  ) {
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);	
		}
		
		try {
			this.col = Integer.parseInt(parameters[1]);
            this.row = Integer.parseInt(parameters[2]);
            
            return new CatchCommand(col,row);
		}
		catch(NumberFormatException a ) {
			throw new CommandParseException(Messages.INVALID_POSITION,parameters[1],parameters[2],a);
		}
	}

}