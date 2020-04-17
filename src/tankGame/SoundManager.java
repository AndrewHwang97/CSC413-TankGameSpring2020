package tankGame;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class SoundManager {
    InputStream audioSrc;
    InputStream bufferedIn;
    AudioInputStream inputStream;
    Clip clip;
    public SoundManager(){

    }

    public void playSound(String soundName){
        try{
          clip = AudioSystem.getClip();
          audioSrc = SoundManager.class.getClassLoader().getResourceAsStream(soundName);
          bufferedIn = new BufferedInputStream(audioSrc);
          inputStream = AudioSystem.getAudioInputStream(bufferedIn);
          clip.open(inputStream);
          clip.start();

        }catch (Exception e){
            System.out.println(e);
        }
    }

    public void close(){
        try {
            inputStream.close();
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
