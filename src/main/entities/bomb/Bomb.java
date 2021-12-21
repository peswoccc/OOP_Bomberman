package main.entities.bomb;

import main.Handler;
import main.TimeManage;
import main.entities.creatures.Creature;
import main.entities.creatures.Player;
import main.gfx.Animation;
import main.gfx.Assets;
import main.sound.SoundEffect;
import main.states.GameState;
import main.tiles.Tile;
import main.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Bomb extends Creature {

    public static final int BOMB_TIME = 20;
    public static final int FLAME_TIME = 3;

    private final Animation bombGif;
    private long startTime;
    private boolean alive = true;
    private final Flame flame;
    private boolean flameAlive = false, flameEarlier = false;

    private boolean isPlayerOutOfBombTile = false;

    public static SoundEffect explosion;

    public Bomb(Handler handler, GameState gameState, float x, float y, int width, int height) {
        super(handler, ((int) x ) / 36 * 36, ((int) y ) / 36 * 36, width, height);
        bombGif = new Animation(250, Assets.bombGif);
        startTime = TimeManage.timeNow();
        flame = new Flame(handler, gameState, x, y, -1, -1, this);
//        handler.getGame().getGameState().getWorld().setTile((int) flame.getX()/36, (int) flame.getY()/36, 'B');

        // create bomb Tile (not solid)
        handler.getWorld().setTile(flameI(), flameJ(), 'v');

        explosion = null;
    }



    @Override
    public void tick() {
        long timeNow = TimeManage.timeNow();
        if (flameEarlier) {
            startTime = timeNow - (BOMB_TIME-FLAME_TIME);
            flameEarlier = false;
        }
        bombGif.tick();

        long elapsedTime = timeNow - startTime;

        if (elapsedTime >= BOMB_TIME)  {

            // delete bomb tile
            handler.getWorld().setTile((int) (getFlame().getX() / 36), (int) (getFlame().getY() / 36), ' ');

            // delete players
            deletePlayerAtFlame();

            alive = false;
        }

        if (elapsedTime >= BOMB_TIME - FLAME_TIME) {
            flameAlive = true;
            flame.tick();
        }

        if (handler.getGame().getGameState().getPlayers().size() != 0 &&!isPlayerOutOfBombTile) checkPlayerOutOfBombTile();

    }


    public void setFlameRightNow() {
        flameEarlier = true;
    }

    @Override
    public void render(Graphics g) {
        if(!flameAlive) {
            if (bombGif.getIndex() == 0)
                g.drawImage(getCurrentAnimation(), (int) x+2, (int) y+2, width-5, height-5, null);
            else
                g.drawImage(getCurrentAnimation(), (int) x, (int) y, width, height, null);
        }

        if(flameAlive) {
            flame.render(g);

            if (explosion == null) {
                explosion = new SoundEffect(SoundEffect.EXPLOSION);
                explosion.play();
            }
        }
    }

    private BufferedImage getCurrentAnimation(){
        return bombGif.getCurrentFrame();
    }

    public boolean isAlive() {
        return alive;
    }

    public long getStartTime() {
        return startTime;
    }

    public List<World.Position> getChangePositions() {
        return flame.getChangePositions();
    }

    public Flame getFlame() {
        return flame;
    }

    private void checkPlayerOutOfBombTile() {
        if (isPlayerOutOfBombTile()) {
            // set bomb tile solid
            isPlayerOutOfBombTile = true;
            handler.getWorld().setTile(flameI(), flameJ(), 'V');
        }
    }

    private boolean isPlayerOutOfBombTile() {
        Player player = handler.getGame().getGameState().getPlayers().get(0);

        float playerX = player.getLeftX();
        float playerY = player.getUpY();

        float bomX = flame.getX();
        float bomY = flame.getY();


        return playerX < bomX - Tile.TILE_HEIGHT * 0.8
                || playerX > bomX + Tile.TILE_HEIGHT * 0.8
                || playerY < bomY - Tile.TILE_HEIGHT * 0.8
                || playerY > bomY + Tile.TILE_HEIGHT * 0.8;
    }

    public int flameI() {
        return (int) (getFlame().getX() / 36);
    }

    public int flameJ() {
        return (int) (getFlame().getY() / 36);
    }

    private boolean playerIsAtFlame(Player player) {
        float flameX = flame.getX();
        float flameY = flame.getY();

        return (player.getDownY() >= flameY && player.getUpY() <= flameY + Tile.TILE_HEIGHT
                && player.getRightX() >= flameX - flame.getLeft() * Tile.TILE_WIDTH
                && player.getLeftX() <= flameX + (flame.getRight() + 1) * Tile.TILE_WIDTH)
                ||
                (player.getRightX() >= flameX && player.getLeftX() <= flameX + Tile.TILE_WIDTH
                && player.getDownY() >= flameY - flame.getUp() * Tile.TILE_HEIGHT
                && player.getUpY() <= flameY + (flame.getDown() + 1) * Tile.TILE_HEIGHT);
    }

    private void deletePlayerAtFlame() {
        List<Player> players = handler.getGame().getGameState().getPlayers();
        for (int i = 0; i < players.size(); i++) {
            Player playerI =  players.get(i);
            if (playerIsAtFlame(playerI)) {
                playerI.setAlive(false);
            }
        }
    }
}
