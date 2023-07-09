package simulator.model;

import simulator.misc.Vector2D;

public class TempSensBody extends MovingBody{

	private double minT;
	private double redF;
	private double incF;
	private double maxT;
	private double temp;
	boolean modo_moving;
	public TempSensBody(String id, String gid, Vector2D position, Vector2D velocity, double mass,double minT,double maxT,double redF,double incF) {
		super(id, gid, position, velocity, mass);
		
		if(minT <0 || maxT <0)
			throw new IllegalArgumentException("Error con temsensBody");

		validate(incF);
		validate(redF);
		this.minT= minT;
		this.maxT = maxT;
		this.redF=redF;
		this.incF=incF;
		this.temp=0;
		this.modo_moving=true;
	}
	
	private void validate(double x) {
		if(x>1|| x<0)
			throw new IllegalArgumentException("Error");
	}

	@Override
	protected void advance(double deltaTime) {
		if(modo_moving) {
			this.moving(deltaTime);
		}
		else {
			this.cooling();
		}
		
	}
	
	protected void moving(double deltaTime) {
		Vector2D p1 = this._position;
		super.advance(deltaTime);
		Vector2D p2 = this._position;
		this.temp += this.incF*p1.distanceTo(p2);
		this.modo_moving= this.temp<= this.maxT;
	}

	protected void cooling() {
		this.temp*=(1-this.redF);
		this.modo_moving =temp<this.minT;
	}
	
}
