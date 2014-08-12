package missiledefense.objects;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import missiledefense.Game;

public class UFO extends Enemy {

	public UFO(Game game, Color color, double speed, double posX, double posY,
			double aim, boolean motionLock, boolean collsionLock) {
		super(game, color, speed, posX, posY, aim, motionLock, collsionLock);
			initialize();
	}
	
	public void initialize(){

		imagePath =  "ufo.png";
		imageFile = new File(imagePath);
		try{
			 image = ImageIO.read(imageFile);
		}catch(IOException ioe){
			System.out.println("Couldn'thread load the image file.");
			System.out.println(ioe);
		}
		
	}
	
	@SuppressWarnings("unused")
	public void move(){
		if(!motionLock){
			double stepX;
			double stepY;
			double angle;
			stepX = new Random().nextInt(3);
			stepY = new Random().nextInt(3);
			while(!(posX + stepX > 0) &&
					!(posX + stepX < settings.screenWidth - 40) &&
					!(posY + stepY > 0) &&
					!(posY + stepY < (settings.screenWidth / 2) - 40)){
				
				stepX = new Random().nextInt(6) - 3;
				stepY = new Random().nextInt(6) - 3;
				
			}
			
			posX += stepX;
			posY += stepY;
			/* if(hasTarget){
				double dx = posX - target[0];
				double dy = posY - target[1];
				if(dx > 0){
					angle = Math.atan(dy / dx);
				}else{
					angle = Math.atan(dy / dx) + Math.PI;
				}
				stepX = new Random().nextInt(3);
				stepY = new Random().nextInt(3);
				posX += stepX;
				posY += stepY;
				isNearTarget = isNearTarget();
				if(isNearTarget()){
					hasTarget = false;
				}
			}else{
				generateRandomTarget();
			} */ // WIP Code
		}
		
	}
	
	public void generateRandomTarget(){
		
		Random rd = new Random();
		if(!hasTarget){
			target = new double[] {rd.nextInt(settings.screenWidth), rd.nextInt(350)};
			hasTarget = true;
		}
		
	}
	
	public boolean isNearTarget(){
		if(Math.abs(posX - target[0]) < 20 && Math.abs(posY - target[1]) < 20){
			return true;
		}else{
			return false;
		}
	}
	
	public void shoot(Game game){
		
		for(int i = 0; i < lasers.length; i++){
			
			if(lasers[i] == null){
				
				lasers[i] = new Laser(game, settings.enemyLaserColor, settings.laserSpeed,
						this.getCenterPointX(), this.getCenterPointY() + 50,
						Math.PI, false, false,
						settings.laserWidth, settings.laserHeight, this);
				
			}
			
		}
		
	}

	public double getCenterPointX() {
		
		return posX + image.getWidth() / 2;
		
	}
	
	public double getCenterPointY() {
		
		return posY + image.getHeight() / 2;
		
	}

}
