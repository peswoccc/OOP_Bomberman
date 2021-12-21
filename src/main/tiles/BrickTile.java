package main.tiles;

import main.gfx.Assets;

public class BrickTile extends Tile {

    public BrickTile(char id) {
        super(Assets.brick, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
