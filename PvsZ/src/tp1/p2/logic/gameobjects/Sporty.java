package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
public class Sporty extends Zombie {
	public final static int FRECUENCIA=1;
	public final static int ENDURENCE=2;
	
	public final static int DAMAGE=1;

	public Sporty(GameWorld game, int col,int row) {
		super(game,col,row,ENDURENCE);
		
	}
	public Sporty() {
	}
	@Override
	protected String getName() {
		
		return Messages.SPORTY_ZOMBIE_NAME;
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
		return Messages.SPORTY_ZOMBIE_SYMBOL;
	}


	@Override
	public Zombie create(GameWorld game, int col, int row) {
		
		return new Sporty(game,col,row);
	}
}
