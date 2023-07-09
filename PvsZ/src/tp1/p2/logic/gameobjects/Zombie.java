package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public abstract class Zombie extends GameObject{
	protected int point;
	public final int POINT_PER_ZOMBIE=10;
	public Zombie() {
		
	}
	public Zombie(GameWorld game ,int col,int row,int hp) {
		super(game ,col,row, hp);
		this.point=10;
	}
	
	
	abstract protected int getFrecuencia();
	
	abstract protected String getName();
	//abstract protected Zombie copy();
	abstract protected int getDamage();
	abstract protected int getEndurence();
	abstract protected Zombie create(GameWorld game,int col,int row) ;
	
	@Override
	public void onEnter() {
		game.newZombieAppear();
		
	}
	

	@Override
	public void onExit() {
		game.ZombieDied(this.point);
		
	}
	

	protected int getCost() {
		return 0;
	}

	@Override
	public boolean receiveZombieAttack(int damage) {	
		return false;			
	}
	


	@Override
	public boolean receiveCherryBombAttack(int damage) {		
		if(this.isAlive()) {
			this.receiveDamage(damage);
			if(!this.isAlive()) {
				this.point=20;
			}
			return true;	
		}
		else {
			return false;
		}
	}
	@Override
	public boolean receivePlantAttack(int damage) {
		if(this.isAlive()) {
			this.receiveDamage(damage);
			return true;	
		}
		else {
			return false;
		}
		
	}
	
	@Override
	public void receiveDamage(int damage) {
		this.hp-=damage;
		
	}
	@Override
	public String getDescription() {		
		return Messages.zombieDescription(this.getName(), this.getFrecuencia(), this.getDamage(), this.getEndurence());	
	}
	public int get_point() {
		return this.point;
	}
	
	public void walk() {	
		this.col-=1;
		this.clk=0;
		if(this.col<0) {
			game.zombieArrived();
		}
	}

	@Override
	public boolean catchObject() {
		return false;
	}

	@Override
	public boolean fillPosition() {
		return true;
	}
	@Override
	public void update() {
		if(this.isAlive()) {
			GameItem other= game.getGameObjectInPosition(this.col-1,this.row);
			
			if(this.clk>=this.getFrecuencia()&& other== null) {
				this.walk();
			}
			
			
			this.attack();
			this.clk+=1;
		}
		
	}
	public void attack() {
		GameItem other= game.getGameObjectInPosition(this.col-1, this.row);
		
		if(other!= null && other.receiveZombieAttack(this.getDamage())) {
		
		}
		
	}


}
