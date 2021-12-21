package main.entities.bomb;

import main.Handler;
import main.TimeManage;
import main.entities.Entity;
import main.gfx.Animation;
import main.gfx.Assets;
import main.states.GameState;
import main.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Bot4Fire extends Entity {

    public static int flameSize = 1;
    public static final int MAX_FLAME_SIZE = 5;
    public static final long FIRE_TIME = Bomb.FLAME_TIME;

    private Animation flameGifLeft, flameGifRight, flameGifUp, flameGifDown, flameGifMid;
    private int left, right, up, down;
    private final GameState gameState;
    private final List<World.Position> changePositions = new ArrayList<>();
    private final int direct;
    private final long deadTime;
    public boolean isAlive;

    public Bot4Fire(Handler handler, GameState gameState, float x, float y, int width, int height, int direct) {
        super(handler, x, y, width, height);
        this.gameState = gameState;
        this.direct = direct;

        setFlame4Size();
        System.out.println(left);
        setAnimation();

        super.x = x;
        super.y = y;

        deadTime = TimeManage.timeNow() + FIRE_TIME;
        isAlive = true;
    }

    @Override
    public void tick() {
        flameGifMid.tick();
        if (left != 0 && direct == 0) flameGifLeft.tick();
        else if (right != 0 && direct == 1) flameGifRight.tick();
        else if (up != 0 && direct == 2) flameGifUp.tick();
        else if (down != 0 && direct == 3) flameGifDown.tick();

        if (TimeManage.timeNow() == deadTime) {
            isAlive = false;
        }
    }

    @Override
    public void render(Graphics g) {
//        g.drawImage(getCurrentAnimationMid(), (int) x, (int) y, 36, 36, null);
        if (left != 0 && direct == 0) {
            g.drawImage(getCurrentAnimationLeft(), (int) x-left*36, (int) y, left*36, 36, null);
        }else if (right != 0 && direct == 1) {
            g.drawImage(getCurrentAnimationRight(), (int) x+36, (int) y, right*36, 36, null);
        } else if (up != 0 && direct == 2) {
            g.drawImage(getCurrentAnimationUp(), (int) x, (int) y-up*36, 36, up*36, null);
        } else if (down != 0 && direct == 3) {
            g.drawImage(getCurrentAnimationDown(), (int) x, (int) y+36, 36, down*36, null);
        }
    }

    private BufferedImage getCurrentAnimationLeft(){
        return flameGifLeft.getCurrentFrame();
    }

    private BufferedImage getCurrentAnimationRight(){
        return flameGifRight.getCurrentFrame();
    }

    private BufferedImage getCurrentAnimationUp(){
        return flameGifUp.getCurrentFrame();
    }

    private BufferedImage getCurrentAnimationDown(){
        return flameGifDown.getCurrentFrame();
    }

    private BufferedImage getCurrentAnimationMid() {
        return flameGifMid.getCurrentFrame();
    }

    public List<World.Position> getChangePositions() {
        return changePositions;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }

    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public void setAnimation() {
        flameGifMid = new Animation(100, Assets.flameMid());
        if (this.left != 0 && direct == 0) {
            flameGifLeft = new Animation(100, Assets.flameLeft(this.left));
        }
        else if (this.right != 0 && direct == 1) {
            flameGifRight = new Animation(100, Assets.flameRight(this.right));
        }
        else if (this.up != 0 && direct == 2) {
            flameGifUp = new Animation(100, Assets.flameUp(this.up));
        } else if (this.down != 0 && direct == 3) {
            flameGifDown = new Animation(100, Assets.flameDown(this.down));
        }
    }

    private boolean haveDuplicatePosition(List<World.Position> changePositions, int x, int y) {
        for (int i = 0; i < changePositions.size(); i++) {
            if (x == changePositions.get(i).x
                    && y == changePositions.get(i).y) {
                return true;
            }
        }
        return false;
    }

    public void setFlame4Size() {
        left = 0;
        while (left != flameSize) {
            int xx = (int) x / 36 - left - 1;
            int yy =  (int) y / 36;
            if(gameState.getWorld().getTile(xx, yy).isSolidToBomb()) {
                if (!haveDuplicatePosition(changePositions, xx, yy))
                    changePositions.add(new World.Position(xx, yy));
                break;
            }
            left++;
        }

        right = 0;
        while (right != flameSize) {
            int xx = (int) x / 36 + right + 1;
            int yy =  (int) y / 36;
            if(gameState.getWorld().getTile(xx, yy).isSolidToBomb()) {
                if (!haveDuplicatePosition(changePositions, xx, yy))
                    changePositions.add(new World.Position(xx, yy));
                break;
            }
            right++;
        }

        up = 0;
        while (up != flameSize) {
            int xx = (int) x / 36;
            int yy =  (int) y / 36 - up - 1;
            if(gameState.getWorld().getTile(xx, yy).isSolidToBomb()) {
                if (!haveDuplicatePosition(changePositions, xx, yy))
                    changePositions.add(new World.Position(xx, yy));
                break;
            }
            up++;
        }

        down = 0;
        while (down != flameSize) {
            int xx = (int) x / 36;
            int yy =  (int) y / 36 + down + 1;
            if(gameState.getWorld().getTile(xx, yy).isSolidToBomb()) {
                if (!haveDuplicatePosition(changePositions, xx, yy))
                    changePositions.add(new World.Position(xx, yy));
                break;
            }
            down++;
        }

        if (direct != 0) left = 0;
        if (direct != 1) right = 0;
        if (direct != 2) up = 0;
        if (direct != 3) down = 0;
    }
}
