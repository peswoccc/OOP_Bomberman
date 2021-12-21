package main.entities.creatures;

import main.AI.EnemyAI;
import main.AI.PlayerAI;
import main.Handler;
import main.TimeManage;
import main.entities.bomb.Bomb;
import main.entities.creatures.bot.Balloon;
import main.entities.creatures.bot.Bot2;
import main.entities.creatures.bot.Bot3;
import main.entities.creatures.bot.Bot4;
import main.gfx.Animation;
import main.gfx.Assets;
import main.states.GameState;
import main.tiles.Tile;
import main.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Player extends Creature {

    public static final double INCREASE_PLAYER_SPEED = 1.0f;
    public static final float PLAYER_SPEED = 2.0f;
//    public static final float PLAYER_SPEED = 1.0f;

    private final Animation aniDown, aniUp, aniLeft, aniRight;

    private boolean isAlive = true;

    private boolean modeAI = false;
    private PlayerAI playerAI;
    private EnemyAI enemyAI;

    public Player(Handler handler, float x, float y) {
        super(handler, x, y, 32, 48);
        speed = PLAYER_SPEED;

        bounds.x = 2;
        bounds.y = 20;
        bounds.width = 28;
        bounds.height = 28;

        aniDown = new Animation(250, Assets.player_down);
        aniUp = new Animation(250, Assets.player_up);
        aniLeft = new Animation(250, Assets.player_left);
        aniRight = new Animation(250, Assets.player_right);
    }

    @Override
    public void tick() {
        aniDown.tick();
        aniUp.tick();
        aniLeft.tick();
        aniRight.tick();
        getInput();
        move();
        checkAlive();

        if (playerAI == null) playerAI = handler.getGame().getGameState().getPlayerAI();
        autoMove();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimation(), (int) x, (int) y, width, height, null);
        g.setColor(Color.red);
//        g.fillRect((int) x+bounds.x, (int) y+bounds.y, bounds.width, bounds.height);
//        g.fillRect((int) x, (int) y, bounds.width + bounds.x, bounds.height + bounds.y);
//        System.out.println(isAlive);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if(handler.getKeyManager().up)
            yMove = -speed;
        if(handler.getKeyManager().down)
            yMove = speed;
        if(handler.getKeyManager().left)
            xMove = -speed;
        if(handler.getKeyManager().right)
            xMove = speed;

    }

    private int lastDirect = 3;

    private BufferedImage getCurrentAnimation(){
        if(xMove < 0) {
            lastDirect = 0;
            return aniLeft.getCurrentFrame();
        }
        if(xMove > 0) {
            lastDirect = 1;
            return aniRight.getCurrentFrame();
        }
        if(yMove < 0) {
            lastDirect = 2;
            return aniUp.getCurrentFrame();
        }
        if(yMove > 0) {
            lastDirect = 3;
            return aniDown.getCurrentFrame();
        }

        if (lastDirect == 0) return Assets.player_left[0];
        if (lastDirect == 1) return Assets.player_right[0];
        if (lastDirect == 2) return Assets.player_up[0];
        if (lastDirect == 3) return Assets.player_down[0];

        return Assets.player;
    }

    public float getLeftX() {
        return x + bounds.x;
    }

    public float getUpY() {
        return y + bounds.y;
    }

    public float getRightX() {
        return x + bounds.x + bounds.width;
    }

    public float getDownY() {
        return y + bounds.y + bounds.height;
    }

    private void checkAlive() {
        List<Balloon> balloons = handler.getGame().getGameState().getBalloons();
        for (Balloon balloon : balloons) {
            if (isCollision((float) balloon.getCurrentTopLeftX(), (float) balloon.getCurrentTopLeftY())) {
                isAlive = false;
                return;
            }
        }

        List<Bot2> bot2s = handler.getGame().getGameState().getBot2s();
        for (int i =0; i < bot2s.size(); i++) {
            if (isCollision((float) bot2s.get(i).getCurrentTopLeftX(), (float) bot2s.get(i).getCurrentTopLeftY())) {
                isAlive = false;
                return;
            }
        }

        List<Bot3> bot3s = handler.getGame().getGameState().getBot3s();
        for (int i =0; i < bot3s.size(); i++) {
            if (isCollision((float) bot3s.get(i).getCurrentTopLeftX(), (float) bot3s.get(i).getCurrentTopLeftY())) {
                isAlive = false;
                return;
            }
        }

        List<Bot4> bot4s = handler.getGame().getGameState().getBot4s();
        for (int i =0; i < bot4s.size(); i++) {
            if (isCollision((float) bot4s.get(i).getCurrentTopLeftX(), (float) bot4s.get(i).getCurrentTopLeftY())) {
                isAlive = false;
                return;
            }
        }
    }

    private boolean isCollision(float balloonX, float balloonY) {
//        System.out.println(getUpY() + " " + balloonY + " " + getLeftX() + " " + balloonX);
        return  getUpY() +  bounds.height >= balloonY
                && getUpY() <= balloonY + Tile.TILE_HEIGHT
                && getLeftX() + bounds.width >= balloonX
                && getLeftX() <= balloonX + Tile.TILE_WIDTH;
    }

    @Override
    protected boolean collisionTitle(int x, int y) {
        return handler.getWorld().getTile(x,y).isSolidToPlayer();
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void turnOffAI() {
        modeAI = false;
    }

    public void turnOnAI() {
        modeAI = true;
    }

    private boolean isRunningFromBomb;
    private long timeStopRunningFromBomb;
    private List<Balloon> balloons;
    private World world;
    private World.Position lastSafeArea;

    private void autoBombNearBot() {
        float botX = balloons.get(0).getX();
        float botY = balloons.get(0).getY();

        float playerX = getLeftX(), playerY = getUpY();

        if (isPlayerNearBot(playerX, playerY, botX, botY)) {
            playerAI.bomb();
            isRunningFromBomb = true;
            timeStopRunningFromBomb = TimeManage.timeNow() + Bomb.BOMB_TIME;
        }
    }

    private boolean isPlayerNearBot(float playerX, float playerY, float botX, float botY) {
        return Math.abs(playerX - botX) <= Tile.TILE_WIDTH * 2
                && Math.abs(playerY - botY) <= Tile.TILE_HEIGHT * 2;
    }

    private void autoMove() {
        if (!modeAI) return;

        if (getLeftX() % 36 != 0 || getUpY() % 36 != 0) return;

        if (world == null) world = handler.getGame().getGameState().getWorld();

        if (balloons == null) balloons = handler.getGame().getGameState().getBalloons();
        if (balloons.size() == 0) {
            playerAI.stop();
            return;
        }

        if (enemyAI == null) enemyAI = GameState.getEnemyAI();

        autoBombNearBot();

        if (!isRunningFromBomb) {
            lastSafeArea = null;
            isRunToCorner = false;
            if (isPlayerNearBot(getLeftX(), getUpY(), balloons.get(0).getX(), balloons.get(0).getY())) {
                playerAI.stop();
            } else {
                catchingBalloon(balloons.get(0));
//                System.out.println("catch");
            }
        } else {
            if (TimeManage.timeNow() >= timeStopRunningFromBomb) {
                isRunningFromBomb = false;
            }

            if (lastSafeArea == null || !isSafe()) {
                runToSafeArea();
            }
            else {
                playerAI.stop();
//                lastSafeArea = null;
            }
        }
    }

    private void catchingBalloon(Balloon balloon) {
        int playerX = (int) getLeftX() / Tile.TILE_WIDTH;
        int playerY = (int) getUpY() / Tile.TILE_HEIGHT;
        int tx = (int) balloon.getX() / 36;
        int ty = (int) balloon.getY() / 36;

        movePlayerToPosition(tx, ty);
    }

    private boolean isRunToCorner = false;

    private void runToSafeArea() {

        int playerX = (int) getLeftX() / Tile.TILE_WIDTH;
        int playerY = (int) getUpY() / Tile.TILE_HEIGHT;

        if (lastSafeArea != null) {
            if (!isRunToCorner) {
                runToCorner();
            } else {
                movePlayerToPosition(lastSafeArea.x, lastSafeArea.y);
            }
        } else {
            List<World.Position> safePositionList = new ArrayList<>();

//            if (playerX - 2 >= 0 && world.getCharTile(playerX - 2, playerY) == ' ')
//                safePositionList.add(new World.Position(playerX - 2, playerY));
//            if (playerX + 2 <= world.getWidth()-1 && world.getCharTile(playerX + 2, playerY) == ' ')
//                safePositionList.add(new World.Position(playerX + 2, playerY));
//            if (playerY - 2 >= 0 && world.getCharTile(playerX, playerY - 2) == ' ')
//                safePositionList.add(new World.Position(playerX, playerY - 2));
//            if (playerY + 2 <= world.getHeight()-1 && world.getCharTile(playerX, playerY + 2) == ' ')
//                safePositionList.add(new World.Position(playerX, playerY + 2));
//            if (playerX )

            List<Bomb> bombList = handler.getGame().getGameState().getBombSet().getBombList();
            if (bombList.size() > 0){
                Bomb bomb = handler.getGame().getGameState().getBombSet().getBombList().get(0);
                playerX = bomb.flameI();
                playerY = bomb.flameJ();
                System.out.println("bomb at " + playerX + " " + playerY);
            }
            if (!tryAdd(safePositionList, playerX - 2, playerY)) {
                tryAdd(safePositionList, playerX - 3, playerY);
            }
            tryAdd(safePositionList, playerX + 2, playerY);
//            tryAdd(safePositionList, playerX, playerY - 2);
//            tryAdd(safePositionList, playerX, playerY + 2);
//            tryAdd(safePositionList, playerX - 1, playerY - 1);
//            tryAdd(safePositionList, playerX - 1, playerY + 1);
//            tryAdd(safePositionList, playerX + 1, playerY - 1);
//            tryAdd(safePositionList, playerX + 1, playerY + 1);


            World.Position bestPosition = bestPosition(safePositionList);
            lastSafeArea = bestPosition;
            System.out.println(lastSafeArea.x + " " + lastSafeArea.y);

//            System.out.println(bestPosition.x + " " + bestPosition.y);

            System.out.println(isRunToCorner);
            if (!isRunToCorner) {
                runToCorner();
            } else {
                movePlayerToPosition(bestPosition.x, bestPosition.y);
            }
        }
    }

    private boolean tryAdd(List<World.Position> list, int placeX, int placeY) {
        if (!validPlaceX(placeX) || !validPlaceY(placeY)) return false;
        if (world.getCharTile(placeX, placeY) == ' ') {
            list.add(new World.Position(placeX, placeY));
            return true;
        }
        return false;
    }

    private boolean validPlaceX(int placeX) {
        return placeX >= 0 && placeX <= world.getWidth()-1;
    }

    private boolean validPlaceY(int placeY) {
        return placeY >= 0 && placeY <= world.getHeight()-1;
    }

    private World.Position bestPosition(List<World.Position> positionList) {
        World.Position[] array = new World.Position[positionList.size()];
        for (int i = 0; i < positionList.size(); i++) {
            array[i] = positionList.get(i);
        }

        Arrays.sort(array, positionComparator());

        return array[array.length-1];
    }

    private Comparator<World.Position> positionComparator() {
        return new Comparator<World.Position>() {
            @Override
            public int compare(World.Position position1, World.Position position2) {
                int x1 = position1.x, y1 = position1.y;
                int x2 = position2.x, y2 = position2.y;

                int botX = (int) balloons.get(0).getX() / 36;
                int botY = (int) balloons.get(0).getY() / 36;

                int distance1ToBot = Math.abs(x1 - botX) + Math.abs(y1 - botY);
                int distance2ToBot = Math.abs(x2 - botX) + Math.abs(y2 - botY);

                if (distance1ToBot - distance2ToBot > 0)
                    return 1;
                else if (distance1ToBot - distance2ToBot < 0)
                    return -1;
                return 0;
            }
        };
    }

    private void movePlayerToPosition(int tx, int ty) {

        int playerX = (int) getLeftX() / Tile.TILE_WIDTH;
        int playerY = (int) getUpY() / Tile.TILE_HEIGHT;

        List<World.Position> path = enemyAI.path(playerX, playerY, tx, ty);


        if (path.size() == 0) return;
        while (path.get(path.size() - 1).x == playerX && path.get(path.size()-1).y == playerY) {
            path.remove(path.size() -1);
            if (path.size() == 0) return;
        }

//        System.out.println(path);

        World.Position catchingPosition = path.get(path.size() - 1);
        int newPlayerX = catchingPosition.x;
        int newPlayerY = catchingPosition.y;

//        System.out.println(playerX + " " + newPlayerX + " " + playerY + " " + newPlayerY);
//        System.out.println(getLeftX() + " " + getUpY());


        if (newPlayerY == playerY && newPlayerX > playerX) {
//            System.out.println("move right");
            playerAI.stop();
            playerAI.moveRight();
            return;
        }

        if (newPlayerY == playerY && newPlayerX < playerX) {
//            System.out.println("move left");
            playerAI.stop();
            playerAI.moveLeft();
            return;
        }

        if (newPlayerX == playerX && newPlayerY > playerY) {
//            System.out.println("move down");
            playerAI.stop();
            playerAI.moveDown();
            return;
        }

        if (newPlayerX == playerX && newPlayerY < playerY) {
//            System.out.println("move up");
            playerAI.stop();
            playerAI.moveUp();
            return;
        }
    }

    private boolean isSafe() {
        return Math.abs(getLeftX() - lastSafeArea.x * Tile.TILE_WIDTH) <= 2
                && Math.abs(getUpY() - lastSafeArea.y * Tile.TILE_HEIGHT) <= 2;
    }

    private void runToCorner() {
        int playerX = (int) getLeftX() / 36 * 36;
        int playerY = (int) getUpY() / 36 * 36;
//        System.out.println("corner " + playerX / 36 + " " + playerY / 36);

        System.out.println(playerX + " " + playerY + " " + getLeftX() + " " + getUpY());
        if (Math.abs(playerX - getLeftX()) <= 2 && Math.abs(playerY - getUpY()) <= 2) {
            isRunToCorner = true;
            return;
        }

        if (getUpY() == playerY && getLeftX() < playerX) {
//            System.out.println("right");
            playerAI.stop();
            playerAI.moveRight();
            return;
        }

        if (getUpY() == playerY && getLeftX() > playerX) {
//            System.out.println("left");
            playerAI.stop();
            playerAI.moveLeft();
            return;
        }

        if (getLeftX() == playerX && getUpY() < playerY) {
//            System.out.println("down");
            playerAI.stop();
            playerAI.moveDown();
            return;
        }

        if (getLeftX() == playerX && getUpY() > playerY) {
//            System.out.println("up");
            playerAI.stop();
            playerAI.moveUp();
            return;
        }
    }
}