package tp1.p2.logic.actions;

import tp1.p2.logic.GameItem;
import tp1.p2.logic.GameWorld;

public class ExplosionAction implements GameAction {

	private int col;

	private int row;

	private int damage;
	private boolean attackZombie;

	public ExplosionAction(int col, int row, int damage,boolean attackZombie) {
		this.col = col;
		this.row = row;
		this.damage = damage;
		this.attackZombie= attackZombie;
	}

	@Override
	public void execute(GameWorld game) {
		GameItem other=null;
		for(int i=this.row-1;i<this.row+2;i++) {
			for(int j=this.col-1;j<this.col+2;j++) {
				other= game.getGameObjectInPosition(j, i);
				if(other!=null) {
					if(this.attackZombie) {
						other.receiveCherryBombAttack(this.damage);
					}
					else {
						other.receiveZombieAttack(this.damage);
					}				
				}
					
			}
			
		}
	}
	

}