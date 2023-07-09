package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.control.exceptions.GameException;
public abstract class Plant extends GameObject{
	
	
	
	public Plant(GameWorld game ,int col,int row,int hp) {
		super(game,col,row,hp);
	}
	public Plant() {
		
	}
	
	abstract protected String getShortcut();
	abstract protected String getName();
	abstract protected Plant copy();
	abstract protected int getCost();
	abstract protected int getDamage();
	abstract protected int getEndurence();
	abstract protected Plant create(GameWorld game,int col,int row) ;
	
	
	
	
	@Override
	public void onEnter() {
	
		
	}


	@Override
	public void onExit() {
		
		
	}


	@Override
	public boolean receiveZombieAttack(int damage) {
		if(this.isAlive()) {
			this.receiveDamage(damage);
			return true;			
		}
		else {
			return false;
		}
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		return false;
	}
	
	
	@Override
	public boolean receiveCherryBombAttack(int damage) {
		return false;
	}
	public boolean compareName(String name) {
		
		if(this.getName().equalsIgnoreCase(name)) {
			
			return true;
		}
		
		else if(this.getSymbol().equalsIgnoreCase(name)){
			
			
			return true;
		}
		return false;
	}
	@Override
	public String getDescription() {

		return Messages.plantDescription(this.getShortcut(),this.getCost(),this.getDamage(),this.getEndurence());
		
	}
	
	public int position(int tam) {
		
		return 0;
	}
	
	public void CanBuy() throws GameException{
		try {
			game.tryToBuy(this.getCost());	
		}
		catch (GameException e) {
			throw e;
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
	
}
