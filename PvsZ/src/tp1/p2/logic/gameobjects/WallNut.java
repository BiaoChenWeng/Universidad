package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
public class WallNut extends Plant{
	public final static int FRECUENCIA=0;
	public final static int ENDURENCE=10;
	public final static int COST=50;
	public final static int DAMAGE=0;
	
	
	
	public WallNut(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	public WallNut() {
		
	}

	@Override
	protected String getName() {
		return Messages.WALL_NUT_NAME;
	}


	@Override
	protected String getShortcut() {
		return Messages.WALL_NUT_NAME_SHORTCUT;
	}
	@Override
	protected Plant copy() {
		return null;
	}


	@Override
	protected int getCost() {
		return COST;
	}
	
	@Override
	protected int getDamage() {
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
		return Messages.WALL_NUT_SYMBOL;
	}


	@Override
	public void update() {
		
		
	}
	
	@Override
	public Plant create(GameWorld game, int col, int row) {
		
		return new WallNut(game,col,row);
	}

	
	
	
}
