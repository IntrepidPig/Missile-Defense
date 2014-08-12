package missiledefense;

import java.awt.Color;

public class Settings {
	
	// Game settings
	public double shieldWidth;
	public double shieldHeight;
	public double shieldStartPositionX;
	public double shieldStartPositionY;
	public Color shieldColor;
	public double missileWidth;
	public double missileHeight;
	public Color missileColor;
	public Color extremeMissileColor;
	public int screenWidth;
	public int screenHeight;
	public int screenPositionX;
	public int screenPositionY;
	public Color screenBackground;
	public double shieldSpeed;
	public double missileSpeed;
	public double missileExtremeSpeed;
	public Color enemyColor; // Currently unused
	public double enemySpeed;
	public double laserWidth;
	public double laserHeight;
	public double laserSpeed;
	public Color enemyLaserColor;
	public Color shieldLaserColor;
	public int shieldLives;
	public int shieldAmmo;
	
	public Settings(){
		initialize();
	}
	
	public void initialize(){
		
		// Initialize the settings
		shieldWidth = 30;
		shieldHeight = 7;
		shieldColor = Color.CYAN;
		missileWidth = 5;
		missileHeight = 15;
		missileColor = Color.YELLOW;
		extremeMissileColor = Color.RED;
		screenWidth = 200;
		screenHeight = 700;
		screenPositionX = 0;
		screenPositionY = 0;
		screenBackground = Color.BLUE;
		shieldSpeed = 5;
		missileSpeed = 2;
		missileExtremeSpeed = 5;
		shieldStartPositionX = (screenWidth / 2) - (shieldWidth / 2);
		shieldStartPositionY = screenHeight - 40;
		enemyColor = Color.ORANGE; // Currently unused
		enemySpeed = 7;
		laserWidth = 5;
		laserHeight = 25;
		laserSpeed = 7;
		enemyLaserColor = Color.WHITE;
		shieldLaserColor = Color.BLUE;
		shieldLives = 30;
		shieldAmmo = 6;
		
	}
	
}
