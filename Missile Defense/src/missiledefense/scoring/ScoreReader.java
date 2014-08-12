package missiledefense.scoring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ScoreReader {
	
	public static int readHighScore(){
		
		int highScore;
		String stringHighScore = null;
		File file = new File("highscore.dat");
		
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			stringHighScore = br.readLine();
			br.close();
		}catch(IOException ioe){
			System.out.println("Could not read high score.\n" +
					"If this is your first time playing or you deleted the high score file then this is normal.");
			System.out.println(ioe);
		}
		
		if(stringHighScore != null){
			highScore = Integer.valueOf(stringHighScore);
		}else{
			highScore = 0;
		}
		return highScore;
		
	}
	
}
