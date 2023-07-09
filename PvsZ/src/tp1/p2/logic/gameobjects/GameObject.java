package tp1.p2.logic.gameobjects;

import static tp1.p2.view.Messages.status;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;
import tp1.p2.view.Messages;

/**
 * Base class for game non playable character in the game.
 *
 */
public abstract class GameObject implements GameItem {

	protected GameWorld game;

	protected int col;
	
	protected int row;
	protected int hp;
	protected int clk;

	

	GameObject(GameWorld game, int col, int row,int hp) {
		this.game = game;
		this.col = col;
		this.row = row;
		this.hp = hp;
		this.clk=0;
	}
	GameObject(){
		
	}

	public boolean isInPosition(int col, int row) {
		return this.col == col && this.row == row ;
	}

	public int getCol() {
		
		return col;
	}

	public int getRow() {
		return row;
	}
	
	public int getClk() {
		return this.clk;
	}
	

	
	public boolean isAlive() {
		return this.hp>0;
	}

	public String toString() {
		if (this.isAlive()) {// imprimir el objeto en el tablero
			
			return this.getStatus();// devolver el spray
		} else {
			
			return "";
		}
	}
	
	protected String getStatus() {
		return Messages.status(this.getSymbol(), this.hp);
	}
	
	
	abstract protected String getSymbol();

	abstract public String getDescription();

	abstract public void update();
	
	abstract public void onEnter();
	
	abstract public void onExit();
	abstract public void receiveDamage(int damge);
	

	abstract public boolean catchObject() ;

	
	abstract public boolean fillPosition() ;

	//abstract public int position(int tam);
	
}
