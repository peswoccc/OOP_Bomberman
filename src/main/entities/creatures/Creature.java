package main.entities.creatures;

import main.Handler;
import main.entities.bomb.BombSet;
import main.entities.Entity;
import main.entities.bomb.Flame;
import main.tiles.Tile;

public abstract class Creature extends Entity {

    public static final int DEFAULT_HEALTH = 10;
    public static final float DEFAULT_SPEED = 2.0f;
    public static final int DEFAULT_CREATURE_WIDTH = 32, DEFAULT_CREATURE_HEIGHT = 32;

    protected int health;
    protected float speed;
    protected float xMove, yMove;

    public Creature(Handler handler, float x, float y, int width, int height) {
        super(handler, x, y, width, height);
        health = DEFAULT_HEALTH;
        speed = DEFAULT_SPEED;
    }

    public void move() {
        moveX();
        moveY();
    }

    public void moveX(){
        if(xMove > 0){
            //Moving right
            int tx = (int) (x + xMove + bounds.x + bounds.width) / Tile.TILE_WIDTH;

            char currentTileChar = handler.getWorld().getCharTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT);
            if (currentTileChar == 'g') {
                handler.getWorld().setTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT, ' ');
                if (Flame.flameSize < Flame.MAX_FLAME_SIZE) Flame.flameSize++;
            }
            if (currentTileChar == 'e') {
                handler.getWorld().setTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT, ' ');
                handler.getGame().getGameState().getPlayers().get(0).speed += Player.INCREASE_PLAYER_SPEED;
            }
            if (currentTileChar == 'n') {
                handler.getWorld().setTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT, ' ');
                BombSet.maxBombNumber++;
            }

            if(!collisionTitle(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionTitle(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
                x += xMove;
            }
        }

        if(xMove < 0){
            int tx = (int) (x + xMove + bounds.x) / Tile.TILE_WIDTH;

            char currentTileChar = handler.getWorld().getCharTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT);
            if (currentTileChar == 'g') {
                handler.getWorld().setTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT, ' ');
                if (Flame.flameSize < Flame.MAX_FLAME_SIZE) Flame.flameSize++;
            }
            if (currentTileChar == 'e') {
                handler.getWorld().setTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT, ' ');
                handler.getGame().getGameState().getPlayers().get(0).speed += Player.INCREASE_PLAYER_SPEED;
            }
            if (currentTileChar == 'n') {
                handler.getWorld().setTile(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT, ' ');
                BombSet.maxBombNumber++;
            }

            if(!collisionTitle(tx, (int) (y + bounds.y) / Tile.TILE_HEIGHT) &&
                    !collisionTitle(tx, (int) (y + bounds.y + bounds.height) / Tile.TILE_HEIGHT)){
                x += xMove;
            }
        }
    }

    public void moveY(){
        if(yMove < 0){
            //Move up
            int ty = (int) (y + yMove + bounds.y) / Tile.TILE_HEIGHT;

            char currentTileChar = handler.getWorld().getCharTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty);
            if (currentTileChar == 'g') {
                handler.getWorld().setTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty, ' ');
                if (Flame.flameSize < Flame.MAX_FLAME_SIZE) Flame.flameSize++;
            }
            if (currentTileChar == 'e') {
                handler.getWorld().setTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty, ' ');
                handler.getGame().getGameState().getPlayers().get(0).speed += Player.INCREASE_PLAYER_SPEED;
            }
            if (currentTileChar == 'n') {
                handler.getWorld().setTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty, ' ');
                BombSet.maxBombNumber++;
            }

            if(!collisionTitle((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionTitle((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
                y += yMove;
            }
        }

        if(yMove > 0){
            //Move down
            int ty = (int) (y + yMove + bounds.y + bounds.height) / Tile.TILE_HEIGHT;

            char currentTileChar = handler.getWorld().getCharTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty);
            if (currentTileChar == 'g') {
                handler.getWorld().setTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty, ' ');
                if (Flame.flameSize < Flame.MAX_FLAME_SIZE) Flame.flameSize++;
            }
            if (currentTileChar == 'e') {
                handler.getWorld().setTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty, ' ');
                handler.getGame().getGameState().getPlayers().get(0).speed += Player.INCREASE_PLAYER_SPEED;
            }
            if (currentTileChar == 'n') {
                handler.getWorld().setTile((int) (x + bounds.x) / Tile.TILE_WIDTH, ty, ' ');
                BombSet.maxBombNumber++;
            }

            if(!collisionTitle((int) (x + bounds.x) / Tile.TILE_WIDTH, ty) &&
                    !collisionTitle((int) (x + bounds.x + bounds.width) / Tile.TILE_WIDTH, ty)){
                y += yMove;
            }
        }
    }

    protected boolean collisionTitle(int x, int y){
        return handler.getWorld().getTile(x,y).isSolid();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
