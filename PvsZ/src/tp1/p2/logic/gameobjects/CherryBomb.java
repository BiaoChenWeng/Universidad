package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
import tp1.p2.logic.actions.ExplosionAction;
public class CherryBomb  extends Plant{
	public final static int FRECUENCIA=2;
	public final static int ENDURENCE=2;
	public final static int COST=50;
	public final static int DAMAGE=10;
	
	private CherryBomb(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	
	public CherryBomb() {

	}
	@Override
	protected String getName() {
		
		return Messages.CHERRY_BOMB_NAME;
	}
	@Override
	protected String getShortcut() {
		
		return Messages.CHERRY_BOMB_NAME_SHORTCUT;
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
		
		return DAMAGE;
	}

	@Override
	protected int getEndurence() {
		// TODO Auto-generated method stub
		return ENDURENCE;
	}

	@Override
	public void receiveDamage(int damage) {
		
		this.hp-=damage;
		
	}


	@Override
	protected String getSymbol() {
		if(this.clk==1) {
			return Messages.CHERRY_BOMB_SYMBOL;
		}
		else {
			return Messages.CHERRY_BOMB_SYMBOL.toUpperCase();	
		}
		
	}


	@Override
	public void update() {
		
		if(this.isAlive()&&this.clk==FRECUENCIA) {				
			game.pushAction(new ExplosionAction(this.col,this.row,DAMAGE,true));
			this.hp=0;
		}
		else {
			this.clk+=1;	
		}
			
	}
	
	
		
	
		
		
	
	@Override
	public Plant create(GameWorld game, int col, int row) {
		
		return new CherryBomb(game,col,row);
	}
	
	
}
