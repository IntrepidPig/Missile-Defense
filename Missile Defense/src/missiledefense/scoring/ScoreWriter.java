package missiledefense.scoring;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

public class ScoreWriter {
	
public static void writeScoreToFile(int score){
		
		String stringScore = String.valueOf(score);
		File file = new File("highscore.dat");
		Writer fw = null;
		try{
			fw = new FileWriter(file);
			fw.write(stringScore);
			fw.close();
		}catch(IOException ioe){
			System.out.println("Couldn'thread write high score to file");
			System.out.println(ioe);
		}
		
	}
	
}
