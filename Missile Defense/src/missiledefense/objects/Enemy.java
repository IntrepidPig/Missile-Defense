package missiledefense.objects;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import missiledefense.Game;
import missiledefense.Settings;

public abstract class Enemy extends MovingObject {
	
	public String imagePath;
	public File imageFile;
	public BufferedImage image;
	
	public double[] target;
	public boolean hasTarget = false;
	public Laser[] lasers = new Laser[1];
	boolean isNearTarget;
	
	Settings settings  = new Settings();
	
	public Enemy(Game game, Color color, double speed, double posX,
			double posY, double aim, boolean motionLock, boolean collsionLock) {
		super(game, color, speed, posX, posY, aim, motionLock, collsionLock);
	}
	
	

}
