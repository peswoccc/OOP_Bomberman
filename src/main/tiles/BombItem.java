package main.tiles;

import main.gfx.Assets;

public class BombItem extends Tile {

    public BombItem(char id) {
        super(Assets.bombItem, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
