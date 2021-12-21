package main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {

    private final boolean[] keys;
    public boolean up, down, left, right, bombed;

    public KeyManager(){
        keys = new boolean[256];
    }

    public void tick(){
        up = keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
        bombed = keys[KeyEvent.VK_B];
    }

    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;

//        if (e.getKeyCode() == KeyEvent.VK_B) {
//            System.out.println("Bomb");
//        } else System.out.println("Press!");
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    public void bombedDone() {
        bombed = false;
    }

}
