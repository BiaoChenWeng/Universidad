package tp1.p2.logic;

import tp1.p2.control.Command;

import tp1.p2.control.Level;
import tp1.p2.control.exceptions.GameException;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.actions.GameAction;
public interface GameWorld {

	public static final int NUM_ROWS = 4;

	public static final int NUM_COLS = 8;
	
	
	void playerQuits();
	void update()throws GameException;
	
	void reset(Level level, long seed)throws GameException;
	void reset()throws GameException;
	boolean isPositionEmpty(int col,int row);
	
	boolean addGameObject(GameObject object);
	void tryToBuy(int coste)throws GameException;
	void zombieArrived();
	void isPositionLimit(int row,int col)throws GameException;
	void isPositionLimitZombie(int row,int col)throws GameException;
	GameItem getGameObjectInPosition(int col,int row);
	
	void ZombieDied(int point);
	
	void addSunCoins();
	void tryToCatchObject(int col, int row)throws GameException;
	void CatchSuncoin();
	void pushAction(GameAction gameAction);
	void newZombieAppear();
	String getThisLevelScore();
	Level getLevel() ;
	long getSeed();
	// TODO add your code here

}
