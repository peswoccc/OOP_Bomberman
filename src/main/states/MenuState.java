package main.states;

import main.Game;
import main.Handler;
import main.gfx.Assets;

import java.awt.*;

public class MenuState extends State {

    public MenuState(Handler handler){
        super(handler);
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(Assets.grass, 0, 0, null);
    }
}
