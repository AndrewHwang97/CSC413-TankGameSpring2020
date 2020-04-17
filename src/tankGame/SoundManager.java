package tankGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {

    public SoundManager(){

    }

    public void playSound(String soundName){
        try{
          Clip clip = AudioSystem.getClip();
            InputStream audioSrc = SoundManager.class.getClassLoader().getResourceAsStream(soundName);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
          AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedIn);
          clip.open(inputStream);
          clip.start();
        }catch (Exception e){
            System.out.println(e);
        }
    }

    /*
    public static void main(String args[]){
        playSound("Sounds/tankShot.wav");
        while (true){

        }
    }

     */
}
