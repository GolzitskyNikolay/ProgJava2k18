package golzitsky.task3.GUI;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

import static golzitsky.task3.GUI.SapperLauncher.playSound;

public class PlaySound {
    static void playSound(String string) {
        if (playSound) {
            try {
                File soundFile = new File(string);
                AudioInputStream inAudio = AudioSystem.getAudioInputStream(soundFile);
                Clip clip = AudioSystem.getClip();
                clip.open(inAudio);
                clip.setFramePosition(0);
                clip.start();
            } catch (Exception e) {
                System.out.println("Error! Can't play sound.");
            }
        }
    }
}
