package main.entities.creatures.bot;

import main.AI.EnemyAI;
import main.Handler;
import main.TimeManage;
import main.entities.bomb.Bot4Fire;
import main.entities.creatures.Player;
import main.gfx.Animation;
import main.gfx.Assets;
import main.states.GameState;
import main.tiles.Tile;
import main.worlds.World;

import java.awt.*;
import java.util.List;

public class Bot4 extends Balloon {

    public static final int BOT4_SPEED = 1;
    public static final long TIME_BEFORE_FIRE = 15;

    private EnemyAI enemyAI;

    public Bot4(Handler handler, float x, float y) {
        super(handler, x, y);

        aniLeft = new Animation(500, Assets.bot4_left);
        aniRight = new Animation(500, Assets.bot4_right);
        aniUp = new Animation(500, Assets.bot4_up);
        aniDown = new Animation(500, Assets.bot4_down);

        speed = BOT4_SPEED;
    }

    private boolean isCatching = false;
    private long timeFire;
    private Bot4Fire bot4Fire;

    @Override
    public void tick() {
        super.tick();

        if (bot4Fire != null) {
            bot4Fire.tick();
            if (!bot4Fire.isAlive) bot4Fire = null;
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        if (bot4Fire != null) bot4Fire.render(g);

//        if (lastDirect == 0) System.out.println("left");
//        else if (lastDirect == 1) System.out.println("right");
//        else if (lastDirect == 2) System.out.println("up");
//        else System.out.println("right");
    }

    @Override
    protected void setAutoMove() {
        if (bot4Fire != null) return;

        if ((x % 36 == 0 || x % 36 == 1) && (y % 36 == 0 || y % 36 == 1)) {
            int tx = (int) x/36;
            int ty = (int) y/36;



            // catching player
            if (enemyAI == null) enemyAI = GameState.getEnemyAI();
            List<Player> players = handler.getGame().getGameState().getPlayers();
            if (players.size() != 0) catchPlayer(players, tx, ty);

            if (!isCatching) randomMove(tx, ty);

        }
    }

    private void randomMove(int tx, int ty) {
        boolean canLeft = !collisionTitle(tx-1,ty);
        boolean canRight = !collisionTitle(tx+1, ty);
        boolean canUp = !collisionTitle(tx, ty-1);
        boolean canDown = !collisionTitle(tx, ty+1);

        while(true) {
            int choose = random.nextInt(4);
            if (choose == 0 && canDown) {
                xMove = 0;
                yMove = speed;
                return;
            }
            if (choose == 1 && canUp) {
                xMove = 0;
                yMove = -speed;
                return;
            }
            if (choose == 2 && canRight) {
                xMove = speed;
                yMove = 0;
                return;
            }
            if (choose == 3 && canLeft) {
                xMove = -speed;
                yMove = 0;
                return;
            }
        }
    }

    private void catchPlayer(List<Player> players, int tx, int ty) {
        boolean isNear = isNearPlayer(players.get(0));
        if (!isNear) isCatching = false;

        if (isNear) {
            if (!isCatching) {
                timeFire = TimeManage.timeNow() + TIME_BEFORE_FIRE;
                isCatching = true;
            } else {
                if (TimeManage.timeNow() >= timeFire) {
//                    System.out.println("fired");
                    fire(players.get(0));
                    isCatching = false;
                }
            }

            int playerX = (int) players.get(0).getLeftX() / Tile.TILE_WIDTH;
            int playerY = (int) players.get(0).getUpY() / Tile.TILE_HEIGHT;
            List<World.Position> path = enemyAI.path(playerX, playerY, tx, ty);
            if (path.size() > 1) {
                World.Position catchingPosition = path.get(1);
                int newTx = catchingPosition.x;
                int newTy = catchingPosition.y;

                if (newTy == ty && newTx > tx) {
                    xMove = speed;
                    yMove = 0;
                    return;
                }

                if (newTy == ty && newTx < tx) {
                    xMove = -speed;
                    yMove = 0;
                    return;
                }

                if (newTx == tx && newTy > ty) {
                    xMove = 0;
                    yMove = speed;
                    return;
                }

                if (newTx == tx && newTy < ty) {
                    xMove = 0;
                    yMove = -speed;
                    return;
                }
            }
        }
    }

    private boolean isNearPlayer(Player player) {
        if (Math.abs(player.getLeftX() - x) >= 36 * 3) return false;
        if (Math.abs(player.getUpY() - y) >= 36 * 3) return false;

        int playerX = (int) player.getLeftX() / Tile.TILE_WIDTH;
        int playerY = (int) player.getUpY() / Tile.TILE_HEIGHT;
        int tx = (int) x/36;
        int ty = (int) y/36;

        if (tx != playerX && ty != playerY) return false;

        List<World.Position> path = enemyAI.path(playerX, playerY, tx, ty);
        return path.size() <= 5;
    }

    private void fire(Player player) {
        int x = (int) this.x / 36 * 36;
        int y = (int) this.y / 36 * 36;
        System.out.println(x + " " + y);
        bot4Fire = new Bot4Fire(handler, handler.getGame().getGameState(), x, y, 0, 0, lastDirect);
    }
}