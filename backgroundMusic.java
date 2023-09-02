import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.sound.sampled.*;  //only works with .wav files //

public class backgroundMusic{
	public static void main(String[] args){
		try {
		Scanner input = new Scanner(System.in);
		
		File game_music = new File("in_game_music2.wav");
		AudioInputStream audio = AudioSystem.getAudioInputStream(game_music);
		/* AudioSystem is a class  that provides methods for reading and writing audio data
			getAudioInputStream reads the audio data frpm in_game_music2 and creates an AudioInputStream object used to read the audio data 
				AudioInputStream reads audio data from a specified source */
		
		Clip clip = AudioSystem.getClip();
		clip.open(audio);
		
		String prompt = "";
		
		while (!prompt.equals("Q")){
			System.out.println("P = Play; S = Stop; R = Reset; Q = Quit");
			System.out.println("enter your prompt");
			
			prompt = input.next();
		    prompt = prompt.toUpperCase(); //??
			
			switch(prompt) {
				case ("P"): clip.start();
				break;
				case ("S"): clip.stop();
				break;
				case ("R"): clip.setMicrosecondPosition(0);  //sets the clip at a certain position in milliseconds, 0 being the beginning
				break;
				case ("Q"): clip.close();
				break;
				default: System.out.println("invalid prompt");
			}
			
			
		}
		
		
		clip.start(); //you must keep the program open and running so that it doesn't terminate when code runs out
		
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException  e){
			e.printStackTrace();
		}
	}
}

