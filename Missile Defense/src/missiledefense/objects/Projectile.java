package missiledefense.objects;

import java.awt.Color;

import missiledefense.Game;

public abstract class Projectile extends MovingObject {
	
	public double width;
	public double height;
	public Object sender;
	
	public Projectile(Game game, Color color, double speed, double posX, double posY, 
			double aim, boolean motionLock, boolean collsionLock, double width, double height, Object sender) {
		
		super(game, color, speed, posX, posY, aim, motionLock, collsionLock);
		this.width = width;
		this.height = height;
		this.sender = sender;
		
	}
	
}
