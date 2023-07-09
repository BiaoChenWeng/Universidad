package tp1.p2.logic.gameobjects;

import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.PlantFactory;
import tp1.p2.view.Messages;
import tp1.p2.logic.GameItem;
public class Peashooter extends Plant{
	public final static int FRECUENCIA=1;
	public final static int ENDURENCE=3;
	public final static int COST=50;
	public final static int DAMAGE=1;
	
	
	
	public Peashooter(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	public Peashooter() {
		
	}

	@Override
	protected String getName() {
		// TODO Auto-generated method stub
		return Messages.PEASHOOTER_NAME;
	}


	@Override
	protected String getShortcut() {
		// TODO Auto-generated method stub
		return Messages.PEASHOOTER_NAME_SHORTCUT;
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
		return Messages.PEASHOOTER_SYMBOL;
	}


	@Override
	public void update() {
		int i=this.col;
		boolean atacado=false;
		GameItem other=null;
		if(this.isAlive()) {
			while(i<game.NUM_COLS && !atacado) {
				other=game.getGameObjectInPosition(i, this.row);
				if(other!=null) {
					atacado=other.receivePlantAttack(DAMAGE);
				}
				i++;
			}
			
		}
	
		// TODO Auto-generated method stub		
	}
	
	@Override
	public Plant create(GameWorld game, int col, int row) {
		
		return new Peashooter(game,col,row);
	}

	
	
	
}
