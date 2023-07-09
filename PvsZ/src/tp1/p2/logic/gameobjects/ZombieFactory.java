package tp1.p2.logic.gameobjects;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.CommandParseException;
import tp1.p2.control.exceptions.GameException;
public class ZombieFactory {
	
	private static final List<Zombie> AVAILABLE_Zombie = Arrays.asList(
			new ZombieNormal(),
			new BucketHead(),
			new Sporty(),
			new ExplosiveZombie()
		);
	
	
	
	private ZombieFactory() {
		
	}

	public static boolean isValidZombie(int zombieIdx) {
		return zombieIdx >= 0 && zombieIdx < AVAILABLE_Zombie.size();
	}
	//throws GameException
	public static Zombie spawnZombie(int zombie_pos,GameWorld game,int col,int row) throws GameException{
	
		if(isValidZombie(zombie_pos)) {
			
			return AVAILABLE_Zombie.get(zombie_pos).create(game,col, row);		
		}
		else {
			throw new CommandParseException(Messages.INVALID_GAME_OBJECT);
				
		}
		
		//return null;
	}
	
	
	public static List<Zombie> getAvailableZombies() {
		return Collections.unmodifiableList(AVAILABLE_Zombie);
	}
	
}
