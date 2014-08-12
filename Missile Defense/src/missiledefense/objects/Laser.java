package missiledefense.objects;

import java.awt.Color;

import missiledefense.Game;

public class Laser extends Projectile{
	
	public Laser(Game game, Color color, double speed, double posX, double posY,
			double aim, boolean motionLock, boolean collisionLock, double width, double height, Object sender){
		
		super(game, color, speed, posX, posY, aim, motionLock, collisionLock, width, height, sender);
		
	}
	
	public void move(){
		
		if(sender instanceof Shield){
			posY -= speed;
		}else if(sender instanceof Enemy){
			posY += speed;
		}else{
			System.out.println("Unknown source!");
		}
		
	}
	
}
