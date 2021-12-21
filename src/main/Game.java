package main;

import main.display.Display;
import main.gfx.Assets;
import main.input.KeyManager;
import main.sound.SoundEffect;
import main.states.GameState;
import main.states.MenuState;
import main.states.State;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;


public class Game implements Runnable {

    private Display display;
    public int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bs;
    private Graphics g;

//    private BufferedImage testImage;
//    private SpriteSheet spriteSheet;

    // States
    private State gameState;
    private State menuState;

    // Inputs
    private KeyManager keyManager = new KeyManager();

    //Handler
    private Handler handler;

    public static SoundEffect backgroundMusic = new SoundEffect(SoundEffect.BACKGROUND_THEME);

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
    }

    private void init(){
        display = new Display(title, width, height);
//        testImage = ImageLoader.loadImage("/image/uncut/bomber4.png");
//        spriteSheet = new SpriteSheet(testImage);
        display.getFrame().addKeyListener(keyManager);
        Assets.init();

        handler = new Handler(this);

        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        State.setState(gameState);

        //  backgroundMusic.play();
        backgroundMusic.loop();
    }

    private int x = 0;

    private void tick(){
//        x += 4;
        keyManager.tick();
        if (State.getState() != null) State.getState().tick();
    }

    private void render(){
        bs = display.getCanvas().getBufferStrategy();
        if(bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }
        g = bs.getDrawGraphics();

        //Clear Screen
        g.clearRect(0, 0, width, height);

        //Draw Here!
//        g.setColor(Color.red);
//        g.fillRect(10, 50, 50, 70);
//        g.drawRect(10, 50, 50, 70);
//        g.setColor(Color.green);
//        g.fillRect(0, 0, 10, 10);

//        g.drawImage(spriteSheet.crop(33, 0, 30, 46), 0, 0, null);

//        g.drawImage(Assets.grass, x, 0, null);
//        g.drawImage(Assets.wall, x, 40, null);
//        g.drawImage(Assets.brick, x, 80, null);

        if (State.getState() != null) State.getState().render(g);

        //End Drawing!
        bs.show();
        g.dispose();
    }

    public void run(){

        init();

        int fps = 60;
        double timePerTick = 1000000000 / fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;

            if(delta >= 1){
                tick();
                render();
                ticks++;
                delta--;
            }

            if(timer >= 1000000000){
//                System.out.println("Ticks and Frames: " + ticks);
                ticks = 0;
                timer = 0;
            }
        }

        stop();

    }

    public synchronized void start(){
        if(running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if(!running)
            return;
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public GameState getGameState() {
        return (GameState) gameState;
    }
}










