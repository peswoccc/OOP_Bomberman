package main.gfx;

import main.TimeManage;
import main.sound.SoundEffect;

import java.awt.*;
import java.awt.image.BufferedImage;

public class CreatureDieAnimation extends Animation {

    private float x;
    private float y;
    private final long startTime, time;
    private boolean isAlive = true;
    private int width = 0, height = 0;

    public static SoundEffect playerDie = new SoundEffect(SoundEffect.PLAYER_DEAD);

    public CreatureDieAnimation(int speed, BufferedImage[] frames, long time, float x, float y) {
        super(speed, frames);
        startTime = TimeManage.timeNow();
        this.time = time;
        this.x = x;
        this.y = y;
    }

    public CreatureDieAnimation(int speed, BufferedImage[] frames, long time, float x, float y, int width, int height) {
        super(speed, frames);
        startTime = TimeManage.timeNow();
        this.time = time;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }



    @Override
    public void tick() {
        if (isAlive) {
            super.tick();

            long elapseTime = TimeManage.timeNow() - startTime;
            if (elapseTime >= time) {
                isAlive = false;
            }
        }
    }

    public void render(Graphics g) {
        if (isAlive) {
            if (width != 0) g.drawImage(getCurrentFrame(), (int) x, (int) y, width, height, null);
            else g.drawImage(getCurrentFrame(), (int) x, (int) y, null);
        }
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }
}
