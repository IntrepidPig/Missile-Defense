package missiledefense;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

import javax.swing.JPanel;


import missiledefense.objects.Enemy;
import missiledefense.objects.Laser;
import missiledefense.objects.Missile;
import missiledefense.objects.Munchkin;
import missiledefense.objects.Shield;
import missiledefense.objects.UFO;
import missiledefense.scoring.ScoreReader;
import missiledefense.scoring.ScoreWriter;



public class Game extends JPanel implements KeyListener, Runnable {
	
	private static final long serialVersionUID = 1L;
	
	// Main variables
	Thread thread;
	Settings settings = new Settings();
	public static final String gameName = "Missile Defense";
	public boolean isMatch;
	public boolean isPaused;
	private static final long FPS = 50;
	private static final long REFRESH_INTERVAL_MS = 1000 / FPS;
	private long startTime;
	private long endTime;
	private long elapsedTime;
	double realFps;
	
	// Gameplay variables
	public Shield player = new Shield(
			this, settings.shieldColor, settings.shieldSpeed,
			settings.shieldStartPositionX, settings.shieldStartPositionY,
			Math.PI, false, false,
			settings.shieldWidth, settings.shieldHeight);
	
	Missile[] missiles = new Missile[30];
	Munchkin[] munchkins = new Munchkin[2];
	UFO[] ufos = new UFO[2];
	Laser[] lasers = new Laser[8];
	
	boolean flagLeft = false;
	boolean flagRight = false;
	boolean autoplay = false;
	
	boolean shoudlGenerateExtremeMissiles = false;
	
	// Cheat codes
	boolean[] BEN = new boolean[3];
	boolean[] SLO = new boolean[3];
	boolean[] MEH = new boolean[3];
	boolean[] PLA = new boolean[3];
	
	boolean isHighScoreLegit = true;
	
	// Locks
	boolean extremeMissileLock = false;
	
	// Other variables
	Random rd = new Random();
	
	// Constructor
	public Game(){
		isMatch = false;
		init();
	}
	
	public void init(){
		
		if(!isMatch){
			isMatch = true;
			thread = new Thread(this);
			thread.start();
		}
		repaint();
		
	}
	
	public void checkPoints(){
		
		if(player.lives <= 0){
			isMatch = false;
		}
		
	}
	
	public void fireMissiles(){
		
		int checker = rd.nextInt(400);
		if(checker < 10){
			if(!shoudlGenerateExtremeMissiles){
				for(int i = 0; i < missiles.length; i++){
					if(missiles[i] == null){
						missiles[i] = new Missile(this, settings.missileColor, settings.missileSpeed, 
								rd.nextInt(Math.abs(settings.screenWidth)), 0, Math.PI, false, false,
								settings.missileWidth, settings.missileHeight, this);
						break;
					}
				}
			}else{
				for(int i = 0; i < missiles.length; i++){
					if(missiles[i] == null){
						missiles[i] = new Missile(this, settings.extremeMissileColor, settings.missileExtremeSpeed, 
								rd.nextInt(Math.abs(settings.screenWidth)), 0, Math.PI, false, false,
								settings.missileWidth, settings.missileHeight, this);
						break;
					}
				}
			}
		}
		
	}
	
	public void spawnEnemies(){
		int startPositionX = (rd.nextInt(Math.abs(settings.screenWidth - 40)));
		int startPositionY = rd.nextInt(350);
		
		int checker = rd.nextInt(2000);
		
		if(checker < 10){
			
			for(int i = 0; i < munchkins.length; i++){
				
				if(munchkins[i] == null){
					
					munchkins[i] = new Munchkin(this, settings.enemyColor, settings.enemySpeed,
							startPositionX, startPositionY, Math.PI, false, false);
					break;
					
				}
				
			}
			
		}
		
		if(checker > 1990){
			
			for(int i = 0; i < ufos.length; i++){
				
				if(ufos[i] == null){
					
					ufos[i] = new UFO(this, settings.enemyColor, settings.enemySpeed,
							startPositionX, startPositionY, Math.PI, false, false);
					break;
					
				}
				
			}
			
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent evt){
		
		switch(evt.getKeyCode()){
		
		case KeyEvent.VK_LEFT:
			flagLeft = true;
			break;
		case KeyEvent.VK_RIGHT:
			flagRight = true;
			break;
		case KeyEvent.VK_SPACE:
			shoot();
			break;
		case KeyEvent.VK_ESCAPE:
			isPaused = !isPaused;
			break;
		case KeyEvent.VK_B:
			BEN[0] = true;
			break;
		case KeyEvent.VK_E:
			BEN[1] = true;
			MEH[1] = true;
			break;
		case KeyEvent.VK_N:
			BEN[2] = true;
			break;
		case KeyEvent.VK_S:
			SLO[0] = true;
			break;
		case KeyEvent.VK_L:
			SLO[1] = true;
			PLA[1] = true;
			break;
		case KeyEvent.VK_O:
			SLO[2] = true;
			break;
		case KeyEvent.VK_M:
			MEH[0] = true;
			break;
		case KeyEvent.VK_H:
			MEH[2] = true;
			break;
		case KeyEvent.VK_P:
			PLA[0] = true;
			break;
		case KeyEvent.VK_A:
			PLA[2] = true;
			break;
			
		}
		
	}
	
	@Override
	public void keyReleased(KeyEvent evt) {
		
		switch(evt.getKeyCode()){
		
		case KeyEvent.VK_LEFT:
			flagLeft = false;
			break;
		case KeyEvent.VK_RIGHT:
			flagRight = false;
			break;
		case KeyEvent.VK_B:
			BEN[0] = false;
			break;
		case KeyEvent.VK_E:
			BEN[1] = false;
			MEH[1] = false;
			break;
		case KeyEvent.VK_N:
			BEN[2] = false;
			break;
		case KeyEvent.VK_S:
			SLO[0] = false;
			break;
		case KeyEvent.VK_L:
			SLO[1] = false;
			PLA[1] = false;
			break;
		case KeyEvent.VK_O:
			SLO[2] = false;
			break;
		case KeyEvent.VK_M:
			MEH[0] = false;
			break;
		case KeyEvent.VK_H:
			MEH[2] = false;
			break;
		case KeyEvent.VK_P:
			PLA[0] = false;
			break;
		case KeyEvent.VK_A:
			PLA[2] = false;
			break;
			
		}
		
	}

	@Override
	public void keyTyped(KeyEvent evt) {
		// Do nothing
	}
	
	public void cheatCheck(){
		
		if(BEN[0] && BEN[1] && BEN[2]){
			player.lives = 10000;
			isHighScoreLegit = false;
		}
		if(SLO[0] && SLO[1] && SLO[2]){
			for(Missile missile : missiles){
				if(missile != null){
					missile.speed = 1;
				}
			}
			for(Enemy enemy : munchkins){
				if(enemy != null){
					enemy.speed = 1;
				}
			}
			isHighScoreLegit = false;
		}
		if(MEH[0] && MEH[1] && MEH[2]){
			player.ammo = 10000;
			isHighScoreLegit = false;
		}
		
		if(PLA[0] && PLA[1] && PLA[2]){
			autoplay = true;
			isHighScoreLegit = false;
		}else{
			autoplay = false;
		}
		
	}
	
	public void shoot(){
		if(player.ammo > 0){	
			for(int i = 0; i < lasers.length; i++){
				if(lasers[i] == null){
					lasers[i] = new Laser(this,settings.shieldLaserColor, settings.laserSpeed,
							player.getCenterPointX(), player.getCenterPointY() - 20,
							Math.PI * 3, false, false,
							settings.laserWidth, settings.laserHeight, player);
					break;
				}
			}
			player.ammo--;
		}
		
	}
	
	public void fireEnemiesLasers(){
		
		for(Munchkin enemy : munchkins){
			if(enemy != null){
				if(enemy.getCenterPointX() >= player.posX - player.width / 2 && enemy.getCenterPointX() <= player.posX + player.width){
					enemy.shoot(this);
				}
			}
		}
		
		for(UFO enemy : ufos){
			if(enemy != null){
				if(enemy.getCenterPointX() >= player.posX - player.width / 2 && enemy.getCenterPointX() <= player.posX + player.width){
					enemy.shoot(this);
				}
			}
		}
		
	}
	
	public void collisionCheck(){
		
		Shape missileShape;
		Shape playerShape;
		Shape laserShape = null;
		Shape enemyShape = null;
		
		if(player.posX + player.width > this.getWidth()){
			player.posX = this.getWidth() - player.width;
		}
		if(player.posX < 0){
			player.posX = 0;
		}
		
		// Checks whether a missile has collided with the player
		for(int i = 0; i < missiles.length; i++){
			if(missiles[i] != null){
				missileShape = new Ellipse2D.Double(missiles[i].posX, missiles[i].posY, missiles[i].width, missiles[i].height);
				playerShape = new Rectangle2D.Double(player.posX, player.posY, player.width, player.height);
				if(missileShape.getBounds().intersects(playerShape.getBounds())){
					missiles[i] = null;
					player.score++;
					player.ammo++;
				}
			}
		}
		
		// Checks whether a missile has reached the end of the screen
		for(int i = 0; i < missiles.length; i++){
			if(missiles[i] != null){
				if(missiles[i].posY > this.getHeight()){
					missiles[i] = null;
					player.lives--;
				}
			}
		}
		
		// Checks whether a player's laser has hit a Munchkin
		for(int i = 0; i < lasers.length; i++){
			if(lasers[i] != null){
				for(int j = 0; j < munchkins.length; j++){
					if(munchkins[j] != null){
						laserShape = new Ellipse2D.Double(lasers[i].posX, lasers[i].posY, lasers[i].width, lasers[i].height);
						enemyShape = new Rectangle2D.Double(munchkins[j].posX, munchkins[j].posY, munchkins[j].image.getWidth(), munchkins[j].image.getHeight());
						if(laserShape.getBounds().intersects(enemyShape.getBounds())){
							lasers[i] = null;
							munchkins[j] = null;
							player.score += 2;
							player.ammo += 2;
							break;
						}
					}
				}
			}
		}
		
		// Checks whether a player's laser has hit a UFO
		for(int i = 0; i < lasers.length; i++){
			if(lasers[i] != null){
				for(int j = 0; j < ufos.length; j++){
					if(ufos[j] != null){
						laserShape = new Ellipse2D.Double(lasers[i].posX, lasers[i].posY, lasers[i].width, lasers[i].height);
						enemyShape = new Rectangle2D.Double(ufos[j].posX, ufos[j].posY, ufos[j].image.getWidth(), ufos[j].image.getHeight());
						if(laserShape.getBounds().intersects(enemyShape.getBounds())){
							lasers[i] = null;
							ufos[j] = null;
							player.score += 2;
							player.ammo += 2;
							break;
						}
					}
				}
			}
		}
		
		// Checks whether a Munchkin's laser has hit the player
		for(Munchkin enemy : munchkins){
			if(enemy != null){
				lasers:
				for(int i = 0; i < enemy.lasers.length; i++){
					if(enemy.lasers[i] != null){
						laserShape = new Ellipse2D.Double(enemy.lasers[i].posX, enemy.lasers[i].posY, enemy.lasers[i].width, enemy.lasers[i].height);
						playerShape = new Rectangle2D.Double(player.posX, player.posY, player.width, player.height);
						if(laserShape.getBounds().intersects(playerShape.getBounds())){
							enemy.lasers[i] = null;
							player.lives--;
							continue lasers; // Could possibly be removed
						}
					}
				}
			}
		}
		
		// Checks whether a UFO's laser has hit the player
		for(UFO enemy : ufos){
			if(enemy != null){
				lasers:
				for(int i = 0; i < enemy.lasers.length; i++){
					if(enemy.lasers[i] != null){
						laserShape = new Ellipse2D.Double(enemy.lasers[i].posX, enemy.lasers[i].posY, enemy.lasers[i].width, enemy.lasers[i].height);
						playerShape = new Rectangle2D.Double(player.posX, player.posY, player.width, player.height);
						if(laserShape.getBounds().intersects(playerShape.getBounds())){
							enemy.lasers[i] = null;
							player.lives--;
							continue lasers; // Could possibly be removed
						}
					}
				}
			}
		}
		
		// Checks whether a player's laser has reached the end of the screen
		for(int i = 0; i < lasers.length; i++){
			
			if(lasers[i] != null){
				if(lasers[i].posY < 0 || lasers[i].posY > settings.screenHeight){
					lasers[i] = null;
					
				}
			}
			
		}
		
		// Checks whether a Munchkin's laser has reached the end of the screen
		for(Munchkin enemy : munchkins){
			if(enemy != null){
				for(int i = 0; i < enemy.lasers.length; i++){
					if(enemy.lasers[i] != null){
						if(enemy.lasers[i].posY < 0 || enemy.lasers[i].posY > settings.screenHeight){
							enemy.lasers[i] = null;
						}
					}
				}

			}
		}
		
		// Checks whether a UFO's laser has reached the end of the screen
		for(UFO enemy : ufos){
			if(enemy != null){
				for(int i = 0; i < enemy.lasers.length; i++){
					if(enemy.lasers[i] != null){
						if(enemy.lasers[i].posY < 0 || enemy.lasers[i].posY > settings.screenHeight){
							enemy.lasers[i] = null;
						}
					}
				}

			}
		}
		
	}
	
	public void moveAll(){
		
		for(Missile missile : missiles){
			
			if(missile != null){
				missile.move();
			}
			
		}
		
		for(int i = 0; i < lasers.length; i++){
			
			if(lasers[i] != null){
				lasers[i].move();
			}
			
		}
		
		for(int i = 0; i < munchkins.length; i++){
			
			if(munchkins[i] != null){
				for(int j = 0; j < munchkins[i].lasers.length; j++){
					if(munchkins[i].lasers[j] != null){
						if(munchkins[i].lasers[j] != null){
							munchkins[i].lasers[j].move();
						}
					}
				}
			}
			
		}
		
		for(int i = 0; i < ufos.length; i++){
			
			if(ufos[i] != null){
				for(int j = 0; j < ufos[i].lasers.length; j++){
					if(ufos[i].lasers[j] != null){
						if(ufos[i].lasers[j] != null){
							ufos[i].lasers[j].move();
						}
					}
				}
			}
			
		}
		
		/* for(Enemy enemy : enemies){
			
			if(enemy != null){
				enemy.move();
			}
			
		} */ //To be implemented in the future
		
		if(!autoplay){
			player.move(flagLeft, flagRight);
		}else{
			if(getNearestMissile() != null){
				player.posX = getNearestMissile().posX - player.width / 2;
			}
			for(Munchkin enemy : munchkins){
				if(enemy != null){
					if(enemy.getCenterPointX() >= player.posX - player.width / 2 && enemy.getCenterPointX() <= player.posX + player.width){
						shoot();
					}
				}
			}
			
			for(UFO enemy : ufos){
				if(enemy != null){
					if(enemy.getCenterPointX() >= player.posX - player.width / 2 && enemy.getCenterPointX() <= player.posX + player.width){
						shoot();
					}
				}
			}
		}
		
	}
	
	public int getCenteredStringPositionX(String s, Graphics gc, Font f){
		
		int posX;
		FontMetrics fm = gc.getFontMetrics(f);
		posX = (settings.screenWidth / 2) - (fm.stringWidth(s) / 2);
		return posX;
		
	}
	
	public int getHighScore(){
		
		int highScore = ScoreReader.readHighScore();
		return highScore;
		
	}
	
	public void setHighScore(){
		if(isHighScoreLegit){
			if(player.score > getHighScore()){
				ScoreWriter.writeScoreToFile(player.score);
			}
		}
		
	}
	
	public int getMissilesOnField(){
		
		int missileCount = 0;
		for(Missile missile : missiles){
			if(missile != null){
				missileCount++;
			}
		}
		return missileCount;
		
	}
	
	public Missile getNearestMissile(){
		
		double nearestDistance = 100000;
		Missile nearestMissile = null;
		
		for(Missile missile : missiles){
			
			if(missile != null){
				
				if(settings.screenWidth - missile.posY < nearestDistance){
					
					nearestDistance = settings.screenWidth - missile.posY;
					nearestMissile = missile;
					
				}
				
			}
			
		}
		
		return nearestMissile;
		
	}
	
	public int getEnemiesLasersOnField(){
		
		int enemyLaserCount = 0;
		for(Enemy enemy : munchkins){
			if(enemy != null){
				for(int i = 0; i < enemy.lasers.length; i++){
					if(enemy.lasers[i] != null){
						enemyLaserCount++;
					}
				}
			}
		}
		
		for(Enemy enemy : ufos){
			if(enemy != null){
				for(int i = 0; i < enemy.lasers.length; i++){
					if(enemy.lasers[i] != null){
						enemyLaserCount++;
					}
				}
			}
		}
		return enemyLaserCount;
		
	}
	
	public void resetter(){
		
		extremeMissileLock = false;
		
	}
	
	public void invokeTricks(){
		
		// Extreme mode
		int random = rd.nextInt(100);
		if(!extremeMissileLock){
			if(random < 40){
				extremeMissileLock = true;
				shoudlGenerateExtremeMissiles = true;
				return;
			}
		}
		
		if(random > 40){
			shoudlGenerateExtremeMissiles = false;
		}
		
	}
	
	@Override
	public void paintComponent(Graphics gc){
		
		// Clear the screen
		super.paintComponent(gc);
		
		gc.setColor(player.color);
		gc.fillRect((int) player.posX, (int) player.posY, (int) player.width, (int) player.height);
		
		for(Missile missile : missiles){
			if(missile != null){
				gc.setColor(missile.color);
				gc.fillOval((int) missile.posX, (int) missile.posY, (int) missile.width, (int) missile.height);
			}
		}
		
		for(Enemy enemy : munchkins){
			if(enemy != null){
				gc.drawImage(enemy.image, (int) enemy.posX, (int) enemy.posY, 40, 40, null);
			}
		}
		
		for(Enemy enemy : ufos){
			if(enemy != null){
				gc.drawImage(enemy.image, (int) enemy.posX, (int) enemy.posY, 40, 40, null);
			}
		}
		
		for(Laser laser : lasers){
			if(laser != null){
				gc.setColor(laser.color);
				gc.fillOval((int) laser.posX, (int) laser.posY, (int) laser.width, (int) laser.height);
			}
		}
		
		for(Enemy enemy : munchkins){
			if(enemy != null){
				for(Laser laser : enemy.lasers){
					if(laser != null){
						gc.setColor(laser.color);
						gc.fillOval((int) laser.posX, (int) laser.posY, (int) laser.width, (int) laser.height);
					}
				}
			}
		}
		
		for(Enemy enemy : ufos){
			if(enemy != null){
				for(Laser laser : enemy.lasers){
					if(laser != null){
						gc.setColor(laser.color);
						gc.fillOval((int) laser.posX, (int) laser.posY, (int) laser.width, (int) laser.height);
					}
				}
			}
		}
		
		gc.setColor(Color.GREEN);
		gc.drawString(player.lives + " Lives", 10, 20);
		if(getMissilesOnField() != 1){
			gc.drawString(getMissilesOnField() + " Missiles", 10, 40);
		}else{
			gc.drawString(getMissilesOnField() + " Missile", 10, 40);
		}
		if(getEnemiesLasersOnField() != 1){
			gc.drawString(getEnemiesLasersOnField() + " Enemies' Lasers", 10, 60);
		}else{
			gc.drawString(getEnemiesLasersOnField() + " Enemies' Laser", 10, 60);
		}
		gc.drawString("Time: " + elapsedTime / 1000, 10, 80);
		gc.drawString("Score: " + player.score, 10, 100);
		gc.drawString("High Score: " + getHighScore(), 10, 120);
		gc.drawString("Ammo: " + player.ammo, 10, 140);
		// gc.drawString("FPS: " + (new DecimalFormat("#.0")).format(realFPS), 10, 160);
		if(!isMatch){
			gc.setColor(Color.RED);
			gc.setFont(new Font("Calibri", Font.BOLD, 18));
			gc.drawString("Game Over", getCenteredStringPositionX("Game Over", gc, new Font("Calibri", Font.BOLD, 18)), this.getHeight() / 2);
		}
		
	}

	@Override
	public void run() {
		startTime = System.currentTimeMillis();
		while(isMatch){
			if(!isPaused){
				
				invokeTricks();
				
				spawnEnemies();
				
				checkPoints();
				
				moveAll();
				
				fireMissiles();
				
				fireEnemiesLasers();
				
				collisionCheck();
				
				cheatCheck();
				
				setHighScore();
				
				resetter();
				
				repaint();
				
				try{
					Thread.sleep(Math.max(1, REFRESH_INTERVAL_MS));
				}catch(InterruptedException ie){
					System.out.println("Thread interrupted. There will be a temporary FPS change.");
					System.out.println(ie);
				}
				
				endTime = System.currentTimeMillis();
				elapsedTime = endTime - startTime;
				
				// realFPS = 1000 / (currentTime - endTime);
			}
		}
		repaint();
		
	}

	

}
