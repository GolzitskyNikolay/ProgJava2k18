package golzitsky.botForSapper.GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class PlaySound {

    boolean playSound = true;

    static void playSound(String string) {
        PlaySound playSoundObject = new PlaySound();
        if (playSoundObject.playSound) {
            try {
                File soundFile = new File(string);
                AudioInputStream inAudio = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(inAudio);
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                System.out.println("Error! Can't play sound!");
            }
        }
    }
}
