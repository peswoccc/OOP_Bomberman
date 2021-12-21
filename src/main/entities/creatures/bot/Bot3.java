package main.entities.creatures.bot;

import main.Handler;
import main.gfx.Animation;
import main.gfx.Assets;
import main.tiles.Tile;
import main.worlds.World;

import java.awt.*;

public class Bot3 extends Balloon{
    private World world;
    private boolean canUp = true, canDown = true, canLeft = true, canRight = true;
    private int tx, ty;

    public Bot3(Handler handler, float x, float y) {
        super(handler, x, y);

        aniDown = new Animation(500, Assets.bot3_down);
        aniUp = new Animation(500, Assets.bot3_up);
        aniLeft = new Animation(500, Assets.bot3_left);
        aniRight = new Animation(500, Assets.bot3_right);

        speed = 1.0f;
    }

    @Override
    public void tick() {
        super.tick();

        if (world == null) world = handler.getGame().getGameState().getWorld();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
    }


    @Override
    protected void setAutoMove() {
        jump();

        if (x % 36 == 0 && y % 36 == 0) {
            int tx = (int) x/36;
            int ty = (int) y/36;

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
    }

    private void jump() {
        if (x % 36 == 0 && y % 36 == 0) {
            int tx = (int) x/36;
            int ty = (int) y/36;

            boolean canUp = true, canDown = true, canLeft = true, canRight = true;

            if (collisionTitle(tx, (int) (y+36) / 36)) canDown = false;
            if (collisionTitle(tx, (int) (y-1)/36)) canUp = false;
            if (collisionTitle((int) (x+36)/36, ty)) canRight = false;
            if (collisionTitle((int) (x-1)/36, ty)) canLeft = false;

            int choose = random.nextInt(4);
            if (choose == 0 && !canDown && canJump(tx, ty+2)
                    && ty < world.getHeight()-2 && world.getCharTile(tx, ty+2) == ' ') {
                y += Tile.TILE_HEIGHT * 2;
                 return;
            }
            if (choose == 1 && !canUp && canJump(tx, ty-2)
                    && ty > 1 && world.getCharTile(tx, ty - 2) == ' ') {
                y -= Tile.TILE_HEIGHT * 2;
                return;
            }
            if (choose == 2 && !canRight && canJump(tx+2, ty)
                    && tx < world.getWidth()-3 && world.getCharTile(tx + 2, ty) == ' ') {
                x += Tile.TILE_WIDTH * 2;
                return;
            }
            if (choose == 3 && !canLeft && canJump(tx-2, ty)
                    && tx > 1 && world.getCharTile(tx - 2, ty) == ' ') {
                x -= Tile.TILE_WIDTH * 2;
            }
        }
    }

    private boolean canJump(int tx, int ty) {
        boolean canLeft = !collisionTitle(tx-1,ty);
        boolean canRight = !collisionTitle(tx+1, ty);
        boolean canUp = !collisionTitle(tx, ty-1);
        boolean canDown = !collisionTitle(tx, ty+1);
        return canLeft || canRight || canUp || canDown;
    }

    private void setCanGo() {
        tx = (int) x/36;
        ty = (int) y/36;
        if (collisionTitle(tx, (int) (y+36) / 36)) canDown = false;
        if (collisionTitle(tx, (int) (y-1)/36)) canUp = false;
        if (collisionTitle((int) (x+36)/36, ty)) canRight = false;
        if (collisionTitle((int) (x-1)/36, ty)) canLeft = false;
    }

    private void randomPath() {
        boolean jumped = false;
        while(true) {
            int choose = random.nextInt(4);
            if (choose == 0) {
                if (canDown) {
                    xMove = 0;
                    yMove = speed;
                    return;
                } else if (!jumped && ty < world.getHeight()-3 && world.getCharTile(tx, ty+2) == ' ') {
                    y += Tile.TILE_HEIGHT * 2;
                    return;
                }
            }
            if (choose == 1) {
                if (canUp) {
                    xMove = 0;
                    yMove = -speed;
                    return;
                } else if (!jumped && ty > 1 && world.getCharTile(tx, ty - 2) == ' ') {
                    y -= Tile.TILE_HEIGHT * 2;
                    return;
                }
            }
            if (choose == 2) {
                if (canRight) {
                    xMove = speed;
                    yMove = 0;
                    return;
                } else if (!jumped && tx < world.getWidth()-3 && world.getCharTile(tx + 2, ty) == ' ') {
                    x += Tile.TILE_WIDTH * 2;
                    return;
                }

            }
            if (choose == 3) {
                if (canLeft) {
                    xMove = -speed;
                    yMove = 0;
                    return;
                } else if (!jumped && tx > 1 && world.getCharTile(tx - 2, ty) == ' ') {
                    x -= Tile.TILE_WIDTH * 2;
                    return;
                }

            }
        }
    }

}
