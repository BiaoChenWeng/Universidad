package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.logic.GameItem;
public class ZombieNormal extends Zombie {
	public final static int FRECUENCIA=2;
	public final static int ENDURENCE=5;

	public final static int DAMAGE=1;
	
	
	
	public ZombieNormal(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	public ZombieNormal() {
		
	}

	@Override
	protected String getName() {
		
		return Messages.ZOMBIE_NAME;
	}



	@Override
	protected int getFrecuencia() {
		return FRECUENCIA;
	}
	@Override
	protected int getDamage() {
		
		return DAMAGE;
	}

	@Override
	protected int getEndurence() {

		return ENDURENCE;
	}





	@Override
	protected String getSymbol() {
		// TODO Auto-generated method stub
		return Messages.ZOMBIE_SYMBOL;
	}


	
	
	@Override
	public Zombie create(GameWorld game, int col, int row) {
		
		return new ZombieNormal(game,col,row);
	}
	
	public void update() {
		
		if(this.isAlive()) {
			GameItem other= game.getGameObjectInPosition(this.col-1,this.row);
			
			if(this.clk>=FRECUENCIA && other== null) {
				this.walk();
			}
			else if(other!= null) {
				this.clk=0;
			}
			other= game.getGameObjectInPosition(this.col-1, this.row);
			
			if(other!= null && other.receiveZombieAttack(DAMAGE)) {
				this.clk=0;
			}
			this.clk+=1;
		}
		
		// TODO Auto-generated method stub		
	}


	
	/*
	 * @Override
	public void update() {
	
		if(this.isAlive()) {
			GameItem other= game.getGameObjectInPosition(this.col-1,this.row);
			
			if(this.clk>=FRECUENCIA && other== null) {
				this.walk();
			}
			else if(other!= null) {
				this.clk=0;
			}
			other= game.getGameObjectInPosition(this.col-1, this.row);
			
			if(other!= null && other.receiveZombieAttack(DAMAGE)) {
				this.clk=0;
			}
			this.clk+=1;
		}
		
		
	}
	 */
	

}
