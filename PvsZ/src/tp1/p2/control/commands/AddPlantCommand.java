package tp1.p2.control.commands;

import tp1.p2.control.Command;

import tp1.p2.logic.GameWorld;

import tp1.p2.view.Messages;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.gameobjects.PlantFactory;

import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.InvalidPositionException;

public class AddPlantCommand extends Command implements Cloneable {

	private int col;

	private int row;

	private String plantName;

	private boolean consumeCoins;

	public AddPlantCommand(String name, int col,int row,boolean consumeCoin) {
		this(consumeCoin);
		this.plantName=name;
		this.col=col;
		this.row=row;
	}
	
	public AddPlantCommand() {
		this(true);
	}

	public AddPlantCommand(boolean consumeCoins) {
		this.consumeCoins = consumeCoins;
	}

	@Override
	protected String getName() {
		return Messages.COMMAND_ADD_NAME;
	}

	@Override
	protected String getShortcut() {
		return Messages.COMMAND_ADD_SHORTCUT;
	}

	@Override
	public String getDetails() {
		return Messages.COMMAND_ADD_DETAILS;
	}

	@Override
	public String getHelp() {
		return Messages.COMMAND_ADD_HELP;
	}

	@Override
	public boolean execute(GameWorld game) throws GameException{
		if(!game.isPositionEmpty(col, row)) {
			throw new InvalidPositionException(Integer.toString(col),Integer.toString(row));
		}
		Plant a= PlantFactory.spawnPlant(this.plantName, game, this.col, this.row);
		game.isPositionLimit(this.row,this.col);
		if(this.consumeCoins ) {		
			a.CanBuy();
		}
		game.addGameObject(a);
		game.update();
		return (true);
		
		
		
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
            return new AddPlantCommand(plantName,col,row,true);
		}
		catch(NumberFormatException a ) {
			throw new CommandParseException(Messages.INVALID_POSITION,parameters[2],parameters[3],a);
		}

	}

}