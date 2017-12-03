import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
/**Implements audio for the game.
 */

/**
 * @author Alberto Canela (original author)
 * @author Maria Novoa (contributor)*
 */

public class AudioEffect {

	public static void playBackgroundSound() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds//goodElectronic.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.loop(Clip.LOOP_CONTINUOUSLY);;
	    } catch(Exception ex) {
	        System.out.println("Error with playing background sound.");
	        ex.printStackTrace();
	    }
	}
	
	public static void playCardSelectionSFX() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds//131658__bertrof__game-sound-selection.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing card selection sound.");
	        ex.printStackTrace();
	    }
	}
	
	public static void playWrongSFX() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds//131657__bertrof__game-sound-wrong.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing card Wrong sound.");
	        ex.printStackTrace();
	    }
	}
	
	public static void playCorrectSFX() {
		try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Sounds//131662__bertrof__game-sound-correct-v2.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing card Correct sound.");
	        ex.printStackTrace();
	    }
	}
}
