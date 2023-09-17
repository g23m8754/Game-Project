//implementing all sound effects 

import java.net.URL;
import javax.sound.sampled.*;

public class sEffects{
	static Clip clip;
	static URL soundURL[] = new URL[30];
	
	
	public static void main(String[] args){
		
	}


	public sEffects() {
		soundURL[0] = getClass().getResource("background_music.wav"); 
		soundURL[1] = getClass().getResource("ball_off_paddles.wav");
		soundURL[2] = getClass().getResource("ball_off_wall.wav");
		soundURL[3] = getClass().getResource("scoring_point.wav");
		soundURL[4] = getClass().getResource("winner_announced.wav");		
	}


	public static void setFile(int x) {
        try{
            AudioInputStream audio = AudioSystem.getAudioInputStream(soundURL[x]);
            clip = AudioSystem.getClip();
            clip.open(audio);

        } catch (Exception e) {
        }
    }


    public static void Play(){
        clip.start();
    }

    public static void Loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public static void stop(){
        clip.stop();
    }




}