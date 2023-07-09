package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.logic.gameobjects.ZombieFactory;
import tp1.p2.view.Messages;
import tp1.p2.logic.GameItem;
public class BucketHead extends Zombie {
	public final static int FRECUENCIA=4;
	public final static int ENDURENCE=8;

	public final static int DAMAGE=1;
	
	
	
	public BucketHead(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	public BucketHead() {
		
	}

	@Override
	protected String getName() {
		
		return Messages.BUCKET_HEAD_ZOMBIE_NAME;
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
	protected String getSymbol() {
		// TODO Auto-generated method stub
		return Messages.BUCKET_HEAD_ZOMBIE_SYMBOL;
	}



	
	@Override
	public Zombie create(GameWorld game, int col, int row) {
		
		return new BucketHead(game,col,row);
	}
	
	

	
	

}
