package main.tiles;

import main.gfx.Assets;

public class BombTileSolid extends Tile {

    public BombTileSolid(char id) {
        super(Assets.grass, id);
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isSolidToPlayer() {
        return true;
    }

    @Override
    public boolean isSolidToBomb() {
        return false;
    }
}
