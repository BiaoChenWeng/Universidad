package tp1.p2.logic;

public interface GameStatus {

	int getCycle();

	int getSuncoins();
	int getRemainingZombies();
	
	boolean isPlayerQuits();
	boolean isPlayerDied();
	boolean allZombiesDied();
	int getGameScore();
	boolean NewRecord();
	String positionToString(int col, int row);
	int getGeneratedSuns();
	int getCaughtSuns();
	// TODO add your code here

}
