package tp1.p2.logic;
import tp1.p2.control.Level;

import tp1.p2.control.Record;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import tp1.p2.control.Command;
import tp1.p2.view.Messages;

import java.util.Random;
import tp1.p2.logic.GameObjectContainer;
import tp1.p2.logic.gameobjects.GameObject;
import tp1.p2.logic.gameobjects.Plant;
import tp1.p2.logic.actions.GameAction;

import tp1.p2.control.exceptions.*;

public class Game implements GameWorld,GameStatus{
	public static final int INITIAL_SUNCOINS=50;
	private int suncoins;
	private long seed;
	private Level level;
	
	private int cycle;
	private Random rand;
	private SunsManager SunsManager;
	private ZombiesManager ZombiesManager;
	private boolean quit_game;
	private int score;		
	private boolean playerDied;
	private GameObjectContainer container;
	private Deque<GameAction> actions;
	private Record record;
	public Game(long seed , Level level) throws GameException{//inicializar el game
		this.score=0;
		this.seed=seed;//=seed
		this.level=level;
		this.record = new Record(this.level);
		this.rand= new Random(seed);
		this.suncoins=INITIAL_SUNCOINS;
		this.cycle=0;
		this.quit_game=false;
		this.playerDied=false;
		this.container=new GameObjectContainer();
		this.ZombiesManager=new ZombiesManager(this,level,this.rand);// inicaliaza zombie manager		
		this.SunsManager=new SunsManager(this,rand);
		this.actions = new ArrayDeque<>();
		
	}	
	//getter
	public  int getSuncoins(){// devuelve el numero de soles que hay
		return this.suncoins;
	}
	public int getCycle() {// devuelve el ciclo actual
		return this.cycle;
	}
	
	public boolean execute(Command command) throws GameException{
		boolean order=command.execute(this);		
		return order;

	}	
	
	public String positionToString(int col, int row) {	
		return this.container.positionToString(row, col);
	}
	//@overide
		
	public void playerQuits() {	
		this.quit_game=true;			
	}
	@Override
	public void update() throws GameException{	
		executePendingActions();

		this.cycle+=1;// aumenta el ciclo 
		this.ZombiesManager.addZombie();
		this.SunsManager.update();
		this.container.update();
		Command.newCycle();
		boolean deadRemoved = true;
		while (deadRemoved || areTherePendingActions()) {
			executePendingActions();	
			deadRemoved = this.container.removeDead();
		}								
		
	}
	
	
	//methos
	public boolean isPlayerQuits() {
		return this.quit_game;
	}
	public boolean isFinished(){
		return this.isPlayerDied()|| this.allZombiesDied();
	}
	@Override
	public void reset(Level level, long seed) throws GameException{
		try {
			
			this.seed=seed;
			this.level=level;
			
		}
		catch (NumberFormatException number){
			throw new CommandExecuteException(Messages.INVALID_COMMAND);
		}
		
		reset();
	}
	@Override
	public void reset() throws GameException{
		this.record.save_file(this.score);
		this.score=0;
		this.cycle=0;
		this.record=new Record(this.level);
		this.suncoins=INITIAL_SUNCOINS;
		this.rand=new Random(seed);
		this.ZombiesManager=new ZombiesManager(this,level,this.rand);// inicaliaza zombie manager		
		this.container.reset();
		this.actions = new ArrayDeque<>();
		this.SunsManager=new SunsManager(this,rand);
	}
	@Override
	public boolean isPositionEmpty(int col,int row) {
		return this.container.isPositionEmpty(col, row);
	}

	@Override
	public void addSunCoins() {
		this.SunsManager.addSun();
	}
	@Override
	public boolean addGameObject(GameObject object) {
		this.container.add(object);
		return true ;
	
	}
	@Override
	public void tryToBuy(int cost) throws GameException {
		if(cost<=this.suncoins) {			
			this.suncoins-=cost;			
		}
		else {
			throw new NotEnoughCoinsException();
		}

	}
	@Override
	public void zombieArrived() {		
		this.playerDied=true;	
	}

	@Override
	public int getRemainingZombies() {
		return this.ZombiesManager.getRemainingZombies();
	}
	@Override
	public boolean isPlayerDied() {
		return this.playerDied;
	}
	@Override
	public boolean allZombiesDied() {
		
		return this.ZombiesManager.AllZombiesDied();
	}
	
	public GameItem getGameObjectInPosition(int col,int row) {
		
		return this.container.getObject(col, row);
	}
	

	public void isPositionLimit(int row,int col) throws GameException{
		 if(!(row<NUM_ROWS&& row>=0 && col >=0 && col<NUM_COLS)) {
			 throw new InvalidPositionException(Integer.toString(col),Integer.toString(row));
		 }
	}
	
	@Override
	public void isPositionLimitZombie(int row, int col) throws GameException{
		if(!(row<NUM_ROWS&& row>=0 && col >=0 && col<=NUM_COLS)) {
			throw new InvalidPositionException(Integer.toString(col),Integer.toString(row));
		}
		
		
	}
	@Override
	public void ZombieDied(int point) {
		this.score+= point;
		this.ZombiesManager.reduceOne();
	}
	@Override
	public int getGeneratedSuns() {		
		return this.SunsManager.getGeneratedSuns();
	}
	@Override
	public int getCaughtSuns() {
		
		return this.SunsManager.getCatchedSuns();
	}
	@Override
	public void tryToCatchObject(int col, int row) throws GameException{	
		if(!this.container.catchObject(col, row)) {
			throw new NotCatchablePositionException(Integer.toString(col),Integer.toString(row));
		}
		
	}
	@Override
	public void CatchSuncoin() {
		this.SunsManager.CatchSuncoin();	
		this.suncoins+=10;
	}
	
	private void executePendingActions() {
		while (!this.actions.isEmpty()) {		
			GameAction action = this.actions.removeLast();
			action.execute(this);
		}
	}

	private boolean areTherePendingActions() {
		return this.actions.size() > 0;
	}
	@Override
	public void pushAction(GameAction gameAction) {
		this.actions.add(gameAction);
	}
	@Override
	public void newZombieAppear() {
		this.ZombiesManager.newZombieAppear();		
	}
	@Override
	public String getThisLevelScore() {		
		return this.record.getScore();
	}
	
	public void finish_game()throws  GameException{
		this.record.save_file(this.score);
	}
	@Override
	public int getGameScore() {
		return this.score;
	}
	@Override
	public boolean NewRecord() {
		
		return record.newScore();
	}
	@Override
	public Level getLevel() {
		return this.level;
	}
	@Override
	public long getSeed() {
		return this.seed;
	}
	
}
