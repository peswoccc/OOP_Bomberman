package main;

import main.tiles.Tile;
import main.worlds.World;

public class Launcher {
    private static final String LEVEL1 = ".\\src\\resource\\map\\level1.txt";
    private static final String LEVEL2 = ".\\src\\resource\\map\\level2.txt";
    public static String PATH;
    private static int width, height;

    public static void findWidthHeight() {
        World world = new World(PATH);
        width = world.getWidth() * Tile.TILE_WIDTH;
        height = world.getHeight() * Tile.TILE_HEIGHT;
    }

    public static void main(String[] args){
        findWidthHeight();
        Game game = new Game("Tile Game!", width, height);
        game.start();
    }

    public static void launch(int level) {
        if (level == 1) PATH = LEVEL1;
        else PATH = LEVEL2;

        findWidthHeight();
        Game game = new Game("Tile Game!", width, height);
        game.start();
    }

}