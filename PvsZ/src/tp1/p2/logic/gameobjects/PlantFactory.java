package tp1.p2.logic.gameobjects;
import tp1.p2.logic.gameobjects.Peashooter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.view.Messages;
import tp1.p2.logic.GameWorld;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.control.exceptions.CommandParseException;
public class PlantFactory {
	
	/* @formatter:off */
	private static final List<Plant> AVAILABLE_PLANTS = Arrays.asList(
		new Sunflower(),
		new Peashooter(),
		new WallNut(),
		new CherryBomb()
	);
	/* @formatter:on */

	public static boolean isValidPlant(String plantName) {
		for (Plant p : AVAILABLE_PLANTS) {			
			if(p.compareName(plantName)) {
				return true;
			}			
		}
		return false;
	}

	public static Plant spawnPlant(String plantName, GameWorld game, int col, int row) throws GameException{
	
		for (Plant p : AVAILABLE_PLANTS) {
			
			
			if(p.compareName(plantName)) {
				
				return p.create(game, col, row);
			}
			
		}

		throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
		//return null;	
	}

	public static List<Plant> getAvailablePlants() {
		return Collections.unmodifiableList(AVAILABLE_PLANTS);
	}

	/*
	 * Avoid creating instances of this class
	 */
	protected PlantFactory(GameWorld game, int col,int row){
		
	}

}
