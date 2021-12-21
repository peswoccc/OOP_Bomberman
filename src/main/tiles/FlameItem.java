package main.tiles;

import main.gfx.Assets;

public class FlameItem extends Tile {

    public FlameItem(char id) {
        super(Assets.flameItem, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
