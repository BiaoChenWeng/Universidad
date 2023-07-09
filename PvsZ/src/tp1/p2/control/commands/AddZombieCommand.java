package tp1.p2.control.commands;

import tp1.p2.control.Command;

import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.Zombie;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;

public class AddZombieCommand extends Command implements Cloneable {

	private int col;

	private int row;

	private int ZombieName;

	public AddZombieCommand(int name, int col, int row) {
		this.ZombieName = name;
		this.col = col;
		this.row = row;
	}

	public AddZombieCommand() {

	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_ZOMBIE_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_ZOMBIE_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_ZOMBIE_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_ZOMBIE_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException {
		if(!game.isPositionEmpty(col, row)) {
			throw new InvalidPositionException(Integer.toString(col),Integer.toString(row));
		}
		
		
		Zombie a = ZombieFactory.spawnZombie(this.ZombieName, game, this.col, this.row);
		game.isPositionLimitZombie(this.row, this.col);
		
		game.addGameObject(a);
		game.update();
		return (true);


	}

	@Override
	public Command create(String[] parameters) throws GameException {
		if (parameters.length <= 3) {
			throw new CommandParseException(Messages.COMMAND_PARAMETERS_MISSING);
		} else if (!ZombieFactory.isValidZombie(Integer.parseInt(parameters[1]))) {
			throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
		}
		try {

			this.ZombieName = Integer.parseInt(parameters[1]);
			this.col = Integer.parseInt(parameters[2]);
			this.row = Integer.parseInt(parameters[3]);

			return new AddZombieCommand(ZombieName, col, row);
		} catch (NumberFormatException a) {
			throw new CommandParseException(Messages.INVALID_POSITION,parameters[2], parameters[3],a);
		}

	}

}