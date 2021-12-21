package main.tiles;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Tile {

    //STATIC STUFF HERE

    public static Tile[] tiles = new Tile[256];
    public static Tile grassTile = new GrassTile(' ');
    public static Tile brickTile = new BrickTile('*');
    public static Tile wallTile = new WallTile('#');
    public static Tile fakeTile = new FakeTile('!');
    public static Tile flameItem = new FlameItem('g');
    public static Tile speedItem = new SpeedItem('e');
    public static Tile bombItem = new BombItem('n');
    public static Tile bombTile = new BombTile('v');
    public static Tile bombTileSolid = new BombTileSolid('V');


//    public static Tile dirtTile = new DirtTile(1);
//    public static Tile rockTile = new RockTile(2);

    //CLASS

    public static final int TILE_WIDTH = 36, TILE_HEIGHT = 36;

    protected BufferedImage texture;
    protected final char id;

    protected boolean solidToPlayer = false;

    public Tile(BufferedImage texture, char id){
        this.texture = texture;
        this.id = id;

        tiles[id] = this;
    }

    public void tick(){

    }

    public void render(Graphics g, int x, int y){
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isSolid(){
        return false;
    }

    public boolean isSolidToPlayer() {
        return isSolid();
    }

    public boolean isSolidToBomb() {
        return isSolid();
    }

    public char getId(){
        return id;
    }

    public void setSolidToPlayer() {
        this.solidToPlayer = true;
    }

    public void setUnSolidToPlayer() {
        this.solidToPlayer = false;
    }
}
