package missiledefense.objects;

import java.awt.Color;

import missiledefense.Game;

public abstract class MovingObject {
	Game game;
	public Color color;
	public double speed;
	public double posX;
	public double posY;
	double cPosX;
	double cPosY;
	double aim;
	boolean motionLock;
	boolean collisionLock;
	
	
	public MovingObject(Game game, Color color, double speed, double posX, double posY, 
			double aim, boolean motionLock, boolean collsionLock){
		
		this.game = game;
		this.color = color;
		this.speed = speed;
		this.posX = posX;
		this.posY = posY;
		this.aim = aim;
		this.motionLock = motionLock;
		
	}
	
	public void setAim(double angle){
		if(!motionLock){
			this.aim = angle % (Math.PI * 2);
		}
	}
	
}
