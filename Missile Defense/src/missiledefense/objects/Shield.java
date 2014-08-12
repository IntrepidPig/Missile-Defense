package missiledefense.objects;

import java.awt.Color;

import missiledefense.Game;
import missiledefense.Settings;

public class Shield extends MovingObject {
	
	private Settings settings = new Settings();
	public double width;
	public double height;
	public int score = 0;
	public int lives = settings.shieldLives;
	public int ammo = settings.shieldAmmo;
	
	public Shield(Game game, Color color, double speed, double posX, double posY, 
			double aim, boolean motionLock, boolean collsionLock, double width, double height) {
		
		super(game, color, speed, posX, posY, aim, motionLock, collsionLock);
		this.width = width;
		this.height = height;
		
	}
	
	public double getCenterPointX(){
		
		return posX + width / 2;
		
	}
	
	public double getCenterPointY(){
		
		return posY + height / 2;
		
	}
	
	public void move(boolean flagLeft, boolean flagRight){
		
		if(flagLeft && !flagRight){
			posX -= speed;
		}
		if(flagRight && !flagLeft){
			posX += speed;
		}
		if(flagLeft && flagRight){
			System.out.println("Whoa there!");
		}
		
	}
	
}
