package main.entities.bomb;

import main.Handler;
import main.entities.Entity;
import main.gfx.Animation;
import main.gfx.Assets;
import main.gfx.CreatureDieAnimation;
import main.states.GameState;
import main.worlds.World;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Flame extends Entity {

    public static int flameSize = 1;
    public static final int MAX_FLAME_SIZE = 5;

    private Animation flameGifLeft, flameGifRight, flameGifUp, flameGifDown, flameGifMid;
    private int left, right, up, down;
    private final GameState gameState;
    private final List<World.Position> changePositions = new ArrayList<>();
    private boolean createBrickDie = false;

    public Flame(Handler handler, GameState gameState, float x, float y, int width, int height, Bomb bomb) {
        super(handler, x, y, width, height);
        this.gameState = gameState;

        setFlame4Size();
        setAnimation();

        super.x = bomb.getX();
        super.y = bomb.getY();
    }

    @Override
    public void tick() {
        flameGifMid.tick();
        if (left != 0) flameGifLeft.tick();
        if (right != 0) flameGifRight.tick();
        if (up != 0) flameGifUp.tick();
        if (down != 0) flameGifDown.tick();
    }

    @Override
    public void render(Graphics g) {
//        g.drawImage(Assets.explosion0, (int) x, (int) y, 36, 36, null);
        g.drawImage(getCurrentAnimationMid(), (int) x, (int) y, 36, 36, null);
//        System.out.print("mid " + flameGifMid.getIndex());
        if (left != 0) {
//            System.out.println(", left" + flameGifLeft.getIndex());
            g.drawImage(getCurrentAnimationLeft(), (int) x-left*36, (int) y, left*36, 36, null);
        }
        if (right != 0)
            g.drawImage(getCurrentAnimationRight(), (int) x+36, (int) y, right*36, 36, null);
        if (up != 0)
            g.drawImage(getCurrentAnimationUp(), (int) x, (int) y-up*36, 36, up*36, null);
        if (down != 0)
            g.drawImage(getCurrentAnimationDown(), (int) x, (int) y+36, 36, down*36, null);

        if (!createBrickDie) {
            createBrickDie();
            createBrickDie = true;
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
        if (this.left != 0) flameGifLeft = new Animation(100, Assets.flameLeft(this.left));
        if (this.right != 0) flameGifRight = new Animation(100, Assets.flameRight(this.right));
        if (this.up != 0) flameGifUp = new Animation(100, Assets.flameUp(this.up));
        if (this.down != 0) flameGifDown = new Animation(100, Assets.flameDown(this.down));
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
    }

    private void createBrickDie() {
        for (int i = 0; i < changePositions.size(); i++) {
            World.Position position = changePositions.get(i);
            int xx = position.x;
            int yy = position.y;
            if (gameState.getWorld().getCharTile(xx, yy) == '*') {
                gameState.getCreatureDieAnimations().add(new CreatureDieAnimation(200, Assets.brickDie, 5, xx * 36, yy * 36, 36, 36));
            }
        }
    }

}
