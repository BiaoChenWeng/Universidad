package tp1.p2.view;

import static tp1.utils.StringUtils.repeat;

import tp1.p2.view.Messages;
import tp1.p2.logic.GameStatus;
import tp1.p2.logic.GameWorld;
import tp1.utils.StringUtils;

public class GamePrinter {

	private static final String SPACE = " ";

	private static final String CELL_BORDER_CHAR = "─";

	private static final String VERTICAL_DELIMITER = "|";

	private static final String NEW_LINE = System.lineSeparator();

	private static final int MARGIN_SIZE = 2;

	private static final String MARGIN = repeat(SPACE, MARGIN_SIZE);

	private static final String EXTENDED_MARGIN = repeat(SPACE, MARGIN_SIZE+2);

	private static final int CELL_SIZE = 8+6;

	private static final String CELL_BORDER = repeat(CELL_BORDER_CHAR, CELL_SIZE);

	private static final String ROW_BORDER = SPACE + repeat(CELL_BORDER + SPACE, GameWorld.NUM_COLS);

	private static final String INDENTED_ROW_BORDER = String.format("%n%s%s%n", EXTENDED_MARGIN, ROW_BORDER);

	private GameStatus game;

	public GamePrinter(GameStatus game) {
		this.game = game;

	}

	/**
	 * Builds a string that represent the game status: cycles, suncoins, remaining
	 * zombies.
	 * 
	 * @return the string that represents the game status.
	 */
	protected String getInfo() {
		StringBuilder buffer = new StringBuilder();
		
		buffer.append(String.format("%s%d%s",Messages.NUMBER_OF_CYCLES,game.getCycle(),NEW_LINE));
		buffer.append(String.format("%s%d%s",Messages.NUMBER_OF_COINS,game.getSuncoins(),NEW_LINE));
		buffer.append(String.format("%s %d%s",Messages.REMAINING_ZOMBIES,game.getRemainingZombies(),NEW_LINE));
		buffer.append(String.format("%s %d%s",Messages.GENERATED_SUNS,game.getGeneratedSuns(),NEW_LINE));
		buffer.append(String.format("%s %d%s",Messages.CAUGHT_SUNS,game.getCaughtSuns(),NEW_LINE));
		buffer.append(String.format("%s %d%s",Messages.SCORE,game.getGameScore(),NEW_LINE));
		return buffer.toString();
	}

	/**
	 * Builds complete representation (status+board) of the game.
	 * 
	 * @return a string representation of the game.
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();

		// Game Status

		str.append(getInfo());

		// Cols numbering
		str.append(EXTENDED_MARGIN);
		for (int col = 0; col <= GameWorld.NUM_COLS; col++) {
			str.append(StringUtils.centre(Integer.toString(col), CELL_SIZE+1));
		}

		// Paint game board
		str.append(INDENTED_ROW_BORDER);

		for (int row = 0; row < GameWorld.NUM_ROWS; row++) {
			str.append(MARGIN).append(Integer.toString(row)).append(SPACE).append(VERTICAL_DELIMITER);
			for (int col = 0; col <=GameWorld.NUM_COLS; col++) {
				str.append(StringUtils.centre(game.positionToString(col, row), CELL_SIZE));
				if (col < GameWorld.NUM_COLS) {
					str.append(VERTICAL_DELIMITER);
				}
				
				
			}
			str.append(INDENTED_ROW_BORDER);
		}

		return str.toString();
	}

	/**
	 * Builds the message to be printed once the game has finished.
	 * 
	 * @return a string representing a message to be printed once the game has
	 *         finished.
	 */
	public String endMessage() {
		StringBuilder buffer = new StringBuilder(Messages.GAME_OVER+NEW_LINE);	
		if(game.isPlayerQuits()) {
			buffer.append(Messages.PLAYER_QUITS);
		}
		else {
			if(!game.allZombiesDied()) {
				buffer.append(Messages.ZOMBIES_WIN);
			}
			else {
				buffer.append(Messages.PLAYER_WINS);
				if(game.NewRecord()) {
					
					buffer.append(NEW_LINE+Messages.NEW_RECORD+game.getGameScore());
				}
				else {
					buffer.append(NEW_LINE+Messages.RECORD+game.getGameScore());
				}
			}
		}
		return buffer.toString();
	}
}
