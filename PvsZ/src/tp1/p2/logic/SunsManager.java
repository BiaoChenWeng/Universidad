package tp1.p2.logic;

import java.util.Random;

import tp1.p2.logic.gameobjects.Sun;

public class SunsManager {

	private static final int COOLDOWN_RANDOM_SUN = 5;

	private GameWorld game;

	private Random rand;

	private int cooldown;
	private int CatchedSuns;
	private int GeneratedSuns;
	
	public SunsManager(GameWorld game, Random rand) {
		this.game = game;
		this.rand = rand;
		this.cooldown = COOLDOWN_RANDOM_SUN;
		this.CatchedSuns=0;
		this.GeneratedSuns=0;
		// TODO add your code here
	}

	public int getCatchedSuns() {
		return this.CatchedSuns;
		
	}

	public int getGeneratedSuns() {
		return this.GeneratedSuns;
	}

	public void update() {
		if (cooldown <= 0) {
			addSun();
			cooldown = COOLDOWN_RANDOM_SUN;
		} else {
			cooldown--;
		}
	}

	private int getRandomInt(int bound) {
		return this.rand.nextInt(bound);
	}

	public void addSun() {
		int col = getRandomInt(GameWorld.NUM_COLS);
		int row = getRandomInt(GameWorld.NUM_ROWS);
		this.GeneratedSuns+=1;
		game.addGameObject(new Sun(this.game, col, row));
	}
	
	public void CatchSuncoin() {
		this.CatchedSuns++;
		
	}
	
}