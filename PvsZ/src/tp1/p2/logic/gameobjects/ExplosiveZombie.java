package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.actions.ExplosionAction;
public class ExplosiveZombie extends Zombie {
	public final static int FRECUENCIA=2;
	public final static int ENDURENCE=5;
	public final static int EXPLOSION_DAMAGE=3;
	public final static int DAMAGE=1;
	
	
	
	public ExplosiveZombie(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	public ExplosiveZombie() {
		
	}

	@Override
	protected String getName() {
		
		return Messages.EXPLOSIVE_ZOMBIE_NAME;
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
		return Messages.EXPLOSIVE_ZOMBIE_SYMBOL;
	}


	
	
	
	@Override
	public Zombie create(GameWorld game, int col, int row) {
		
		return new ExplosiveZombie(game,col,row);
	}
	

	
	@Override
	public void onExit() {
		
		super.onExit();
		game.pushAction(new ExplosionAction(this.col,this.row,EXPLOSION_DAMAGE,false));
	}
	
	
}
