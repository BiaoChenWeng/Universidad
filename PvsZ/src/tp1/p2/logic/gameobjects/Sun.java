package tp1.p2.logic.gameobjects;
import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;
public class Sun extends GameObject{

	public final static int SUN_LIVE=11; 
	
	public Sun(GameWorld game,int col,int  row) {
		super(game,col,row,SUN_LIVE);
	}

	@Override
	public boolean receiveZombieAttack(int damage) {
		return false;
	}

	
	@Override
	public boolean receiveCherryBombAttack(int damage) {
		return false;
	}

	@Override
	public boolean receivePlantAttack(int damage) {
		return false;
	}


	@Override
	protected String getSymbol() {
		return Messages.SUN_SYMBOL;
	}


	@Override
	public String getDescription() {
		return Messages.SUN_DESCRIPTION;
	}

	@Override
	public void update() {
		this.hp--;
	}

	@Override
	public void onEnter() {
		
	}

	@Override
	public void onExit() {
		
	}


	@Override
	public void receiveDamage(int damge) {	
	}
		
	@Override
	public boolean catchObject() {
		this.hp=0;
		game.CatchSuncoin();
		return true;
	}

	@Override
	public boolean fillPosition() {
		return false;
	}
	
	
	
	
	
}
