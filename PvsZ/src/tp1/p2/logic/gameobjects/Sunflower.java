package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

public class Sunflower  extends Plant{
	public final static int FRECUENCIA=3;
	public final static int ENDURENCE=1;
	public final static int COST=20;
	public final static int DAMAGE=0;
	
	private Sunflower(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	
	public Sunflower() {

	}
	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return Messages.SUNFLOWER_NAME;
	}
	@Override
	protected String getShortcut() {
		// TODO Auto-generated method stub
		return Messages.SUNFLOWER_NAME_SHORTCUT;
	}


	@Override
	protected Plant copy() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	protected int getCost() {
		// TODO Auto-generated method stub
		return COST;
	}


	@Override
	protected int getDamage() {
		// TODO Auto-generated method stub
		return DAMAGE;
	}

	@Override
	protected int getEndurence() {
		// TODO Auto-generated method stub
		return ENDURENCE;
	}

	@Override
	public void receiveDamage(int damage) {
		// TODO Auto-generated method stub
		this.hp-=damage;
		
	}


	@Override
	protected String getSymbol() {
		// TODO Auto-generated method stub
		return Messages.SUNFLOWER_SYMBOL;
	}




	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(this.isAlive()&&this.clk==FRECUENCIA) {		
			game.addSunCoins();
			this.clk=0;
		}
		
		this.clk+=1;		
	}
	
	
	@Override
	public Plant create(GameWorld game, int col, int row) {
		
		return new Sunflower(game,col,row);
	}
	
	
}
