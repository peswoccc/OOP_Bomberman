package main.sound;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class SoundEffect {
    public static final String BACKGROUND_THEME = "03_Stage Theme";
    public static final String ENEMY_DEAD = "enemy_dead";
    public static final String EXPLOSION = "explosion";
    public static final String PLACE_BOMB = "se_assist_bomberman_bombset";
    public static final String PLAYER_DEAD = "08_Life Lost";
    public static final String POWER_UP = "power_up";
    public static final String STAGE_COMPLETE = "05_Stage Complete";
    public static final String MENU = "01_Title Screen";

    private Clip clip;
    public float preVolume = 0;
    public float curVolume = 0;
    public FloatControl fc;
    boolean mute = false;

    public SoundEffect(String name) {
        String path = ".\\src\\resource\\sound\\" + name + ".wav";
        try {
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(path));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        clip.setFramePosition(0);
        fc.setValue(curVolume);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }

    public void volumeUp() {
        curVolume += 1.0f;
        if(curVolume > 6.0f){
            curVolume = 6.0f;
        }
        fc.setValue(curVolume);
    }

    public void volumeDown() {
        curVolume -= 1.0f;
        if(curVolume < -80.0f){
            curVolume = - 80.0f;
        }
        fc.setValue(curVolume);
    }

    public void volumeMute() {
        if(mute == false){
            preVolume = curVolume;
            curVolume = -80.0f;
            fc.setValue(curVolume);
            mute = true;
        }
        else if (mute == true) {
            curVolume = preVolume;
            fc.setValue(curVolume);
            mute = false;
        }
    }
}



