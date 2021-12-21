package main.tiles;

import main.gfx.Assets;

public class FakeTile extends Tile {

    public FakeTile(char id) {
        super(Assets.fake, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
