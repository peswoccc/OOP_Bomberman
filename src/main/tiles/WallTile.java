package main.tiles;

import main.gfx.Assets;

public class WallTile extends Tile {

    public WallTile(char id) {
        super(Assets.wall, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
