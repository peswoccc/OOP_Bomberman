package main.tiles;

import main.gfx.Assets;

public class SpeedItem extends Tile {

    public SpeedItem(char id) {
        super(Assets.speedItem, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
