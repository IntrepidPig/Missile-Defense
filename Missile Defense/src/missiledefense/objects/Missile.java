package missiledefense.objects;

import java.awt.Color;

import missiledefense.Game;

public class Missile extends Projectile {
	
	public Missile(Game game, Color color, double speed, double posX, double posY, 
			double aim, boolean motionLock, boolean collsionLock, double width, double height, Object sender) {
		
		super(game, color, speed, posX, posY, aim, motionLock, collsionLock, width, height, sender);
		
	}
	
	public void move(){
		if(!motionLock){
			posY += speed;
		}
	}
	
}
