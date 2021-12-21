package main.gfx;

import main.entities.bomb.Flame;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Assets {

    public static BufferedImage player, grass, wall, brick, fake, brickUncut;
    public static BufferedImage[] player_down, player_up, player_left, player_right;
    public static BufferedImage[] balloon_down, balloon_up, balloon_left, balloon_right;
    public static BufferedImage bomb, explosionUncut, explosion0;
    public static BufferedImage[] bombGif;
    public static BufferedImage flameItem, bombItem, speedItem;
    public static BufferedImage[] playerDie, brickDie, bot1Die, bot2Die, bot3Die, bot4Die;
    public static BufferedImage empty, botUncut;
    public static BufferedImage[] bot2_left, bot2_right, bot2_up, bot2_down;
    public static BufferedImage[] bot3_left, bot3_right, bot3_up, bot3_down;
    public static BufferedImage[] bot4_left, bot4_right, bot4_up, bot4_down;
    public static BufferedImage[] portalOpen, portalClose;
    public static BufferedImage playerDie1, playerDie2, playerDie3, playerDie4;

    private static SpriteSheet botUncutSheet;

    public static void init(){
        setPlayerImage();

        grass = ImageLoader.loadImage("/image/grass.jpg");
        wall = ImageLoader.loadImage("/image/hardWall.png");
        setBrickImage();
        fake = ImageLoader.loadImage("/image/bomb1.png");
        bomb = ImageLoader.loadImage("/image/bomb1.png");

        bombGif = new BufferedImage[2];
        bombGif[0] = bomb;
        bombGif[1] = bomb;

        // explosion cutting
        explosionUncut = ImageLoader.loadImage("/image/uncut/explosion.png");
        SpriteSheet spriteSheetExplosion = new SpriteSheet(explosionUncut);
        explosion0 = spriteSheetExplosion.crop(5*32, 0, 32, 32);

        flameItem = ImageLoader.loadImage("/image/power_pierce.png");
        speedItem = ImageLoader.loadImage("/image/power_speed.png");
        bombItem = ImageLoader.loadImage("/image/power_bomb.png");

        empty = ImageLoader.loadImage("/image/empty.png");

        setPlayerDieImage();

        botUncut = ImageLoader.loadImage("/image/enemy/bot_uncut.png");
        botUncutSheet = new SpriteSheet(botUncut);
        setBot2Image();
        setBalloonImage();
        setBot3Image();
        setBot4Image();

        setPortalImage();
    }

    private static void setBot4Image() {
        BufferedImage bot4Uncut = ImageLoader.loadImage("/image/enemy/bot4_uncut.png");
        SpriteSheet bot4Sheet = new SpriteSheet(bot4Uncut);
        bot4_left = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            bot4_left[i] = bot4Sheet.crop(i * 192, 192, 192, 192);
        }

        bot4_right = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            bot4_right[i] = bot4Sheet.crop(i * 192, 2 * 192, 192, 192);
        }

        bot4_up = new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            bot4_up[i] = bot4Sheet.crop(i * 192, 3 * 192, 192, 192);
        }

        bot4_down =  new BufferedImage[3];
        for (int i = 0; i < 3; i++) {
            bot4_down[i] = bot4Sheet.crop(i * 192, 0, 192, 192);
        }
    }

    private static void saveImage(BufferedImage image, String name) {
        File outputFile = new File("D:\\" + name + ".png");
        try {
            ImageIO.write(image, "png", outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage[] flameLeft(int length) {
        BufferedImage[] ans = new BufferedImage[8];
        String s = "left" + (Flame.flameSize);

        for (int i = 0; i <= 4; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (4-i) + "/bombbang_" + s + ".png");
            ans[i] = cropLeft(length, ans[i]);
        }
        for (int i = 5; i <= 7; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (i-4) + "/bombbang_" + s + ".png");
            ans[i] = cropLeft(length, ans[i]);
        }

        return ans;
    }

    public static BufferedImage[] flameRight(int length) {
        BufferedImage[] ans = new BufferedImage[8];
        String s = "right" + (Flame.flameSize);

        for (int i = 0; i <= 4; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (4-i) + "/bombbang_" + s + ".png");
            ans[i] = cropRight(length, ans[i]);
        }
        for (int i = 5; i <= 7; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (i-4) + "/bombbang_" + s + ".png");
            ans[i] = cropRight(length, ans[i]);
        }

        return ans;
    }

    public static BufferedImage[] flameUp(int length) {
        BufferedImage[] ans = new BufferedImage[8];
        String s = "up" + (Flame.flameSize);

        for (int i = 0; i <= 4; i++) {
//            System.out.println("/image/explosion/"+ i + "/bombbang_" + s + ".png");
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (4-i) + "/bombbang_" + s + ".png");
            ans[i] = cropUp(length, ans[i]);
        }
        for (int i = 5; i <= 7; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (i-4) + "/bombbang_" + s + ".png");
            ans[i] = cropUp(length, ans[i]);
        }

        return ans;
    }

    public static BufferedImage[] flameDown(int length) {
        BufferedImage[] ans = new BufferedImage[8];
        String s = "down" + (Flame.flameSize);

        for (int i = 0; i <= 4; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (4-i) + "/bombbang_" + s + ".png");
            ans[i] = cropDown(length, ans[i]);
        }
        for (int i = 5; i <= 7; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (i-4) + "/bombbang_" + s + ".png");
            ans[i] = cropDown(length, ans[i]);
        }

        return ans;
    }

    public static BufferedImage[] flameMid() {
        BufferedImage[] ans = new BufferedImage[8];
        for (int i = 0; i <= 4; i++) {
//            System.out.println("/image/explosion/"+ i + "/bombbang_mid.png");
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (4-i) + "/bombbang_mid.png");
        }
        for (int i = 5; i <= 7; i++) {
            ans[i] = ImageLoader.loadImage("/image/explosion/"+ (i-4) + "/bombbang_mid.png");
        }
        return ans;
    }

    private static BufferedImage cropLeft(int length, BufferedImage ans) {
        ans = new SpriteSheet(ans).crop(0, 0, ans.getWidth()-1, ans.getHeight()-1);
        double tileSize = (double) ans.getWidth() / Flame.flameSize;
        ans = new SpriteSheet(ans).crop((int) ((Flame.flameSize - length) * tileSize),
                0,
                (int) (length * tileSize),
                ans.getHeight());
        return ans;
    }

    private static BufferedImage cropRight(int length, BufferedImage ans) {
        ans = new SpriteSheet(ans).crop(0, 0, ans.getWidth()-1, ans.getHeight()-1);
        double tileSize = (double) ans.getWidth() / Flame.flameSize;
        ans = new SpriteSheet(ans).crop(0,
                0,
                (int) (length * tileSize),
                ans.getHeight());
        return ans;
    }

    private static BufferedImage cropUp(int length, BufferedImage ans) {
        ans = new SpriteSheet(ans).crop(0, 0, ans.getWidth()-1, ans.getHeight()-1);
        double tileSize = (double) ans.getHeight() / Flame.flameSize;
        ans = new SpriteSheet(ans).crop( 0,
                (int) ((Flame.flameSize - length) * tileSize),
                ans.getWidth(),
                (int) (length * tileSize));
        return ans;
    }

    private static BufferedImage cropDown(int length, BufferedImage ans) {
        ans = new SpriteSheet(ans).crop(0, 0, ans.getWidth()-1, ans.getHeight()-1);
        double tileSize = (double) ans.getHeight() / Flame.flameSize;
        ans = new SpriteSheet(ans).crop( 0,
                0,
                ans.getWidth(),
                (int) (length * tileSize));
        return ans;
    }

    private static void setPlayerImage() {
        SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/image/uncut/bomber3.png"));

        player = playerSheet.crop(0, 48, 32, 48);

        player_down = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            player_down[i] = playerSheet.crop(i * 32, 48, 32, 48);
        }

        player_up = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            player_up[i] = playerSheet.crop(i * 32, 0, 32, 48);
        }

        player_right = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            player_right[i] = playerSheet.crop(i * 32, 3 * 48, 32, 48);
        }

        player_left = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            player_left[i] = playerSheet.crop(i * 32, 2 * 48, 32, 48);
        }
    }

    private static void setPlayerDieImage() {
        SpriteSheet playerSheet = new SpriteSheet(ImageLoader.loadImage("/image/uncut/bomber3.png"));
        playerDie1 = playerSheet.crop(0, 4 * 48, 32, 48);
        playerDie2 = playerSheet.crop(32, 4 * 48, 32, 48);
        playerDie3 = playerSheet.crop(2 * 32, 4 * 48, 32, 48);
        playerDie4 = playerSheet.crop(3 * 32, 4 * 48, 32, 48);

        playerDie = new BufferedImage[20];
        playerDie[0] = empty;
        playerDie[1] = playerDie1;
        playerDie[2] = empty;
        playerDie[3] = playerDie1;
        playerDie[4] = empty;
        playerDie[5] = playerDie2;
        playerDie[6] = empty;
        playerDie[7] = playerDie2;
        playerDie[8] = empty;
        playerDie[9] = playerDie3;
        playerDie[10] = empty;
        playerDie[11] = playerDie3;
        playerDie[12] = empty;
        playerDie[13] = playerDie4;
        playerDie[14] = empty;
        playerDie[15] = playerDie4;
        playerDie[16] = empty;
        playerDie[17] = playerDie4;
        playerDie[18] = empty;
        playerDie[19] = playerDie4;
    }

    private static void setBot2Image() {
        bot2_left = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot2_left[i] = botUncutSheet.crop((6+i)*47, 47, 47, 47);
        }

        bot2_right = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot2_right[i] = botUncutSheet.crop((6+i)*47, 2*47, 47, 47);
        }

        bot2_up = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot2_up[i] = botUncutSheet.crop((6+i)*47, 3*47, 47, 47);
        }

        bot2_down = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot2_down[i] = botUncutSheet.crop((6+i)*47, 0, 47, 47);
        }
    }

    private static void setBalloonImage() {
        balloon_left = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            balloon_left[i] = botUncutSheet.crop((i)*47, 47, 47, 47);
        }

        balloon_right = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            balloon_right[i] = botUncutSheet.crop((i)*47, 2*47, 47, 47);
        }

        balloon_up = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            balloon_up[i] = botUncutSheet.crop((i)*47, 3*47, 47, 47);
        }

        balloon_down = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            balloon_down[i] = botUncutSheet.crop((i)*47, 0, 47, 47);
        }

        bot1Die = new BufferedImage[2];
        bot1Die[0] = balloon_down[0];
        bot1Die[1] = empty;
    }

    private static void setBot3Image() {
        bot3_left = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot3_left[i] = botUncutSheet.crop((6+i)*47, 5*47, 47, 47);
        }

        bot3_right = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot3_right[i] = botUncutSheet.crop((6+i)*47, 6*47, 47, 47);
        }

        bot3_up = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot3_up[i] = botUncutSheet.crop((6+i)*47, 7*47, 47, 47);
        }

        bot3_down = new BufferedImage[6];
        for (int i = 0; i < 6; i++) {
            bot3_down[i] = botUncutSheet.crop((6+i)*47, 4*47, 47, 47);
        }

    }

    private static void setBrickImage() {
                brick = ImageLoader.loadImage("/image/softWall.png");
//        brickUncut = ImageLoader.loadImage("/image/uncut/brick_uncut.png");
//        SpriteSheet brickSheet = new SpriteSheet(brickUncut);
//        brick = brickSheet.crop(59, 22, 98, 98);
//        brick = brickSheet.crop(59+98+95, 22,98,98);
        brickDie = new BufferedImage[8];
//        brickDie[0] = brickSheet.crop(59+98+95, 22,98,98);
//        brickDie[1] = brickSheet.crop(59+2*98+2*95, 22,98,98);
//        brickDie[2] = brickSheet.crop(98,98);
//        brickDie[3] = brickSheet.crop(98,98);
//        brickDie[4] = brickSheet.crop(98,98);
//        brickDie[5] = brickSheet.crop(98,98);
//        brickDie[6] = brickSheet.crop(98,98);
//        brickDie[7] = brickSheet.crop(98,98);
        brickDie[0] = empty;
        brickDie[1] = empty;
        brickDie[2] = empty;
        brickDie[3] = empty;
        brickDie[4] = empty;
        brickDie[5] = empty;
        brickDie[6] = empty;
        brickDie[7] = empty;
    }

    private static void setPortalImage() {
        portalClose = new BufferedImage[17];
        BufferedImage portalCloseUncut = ImageLoader.loadImage("/image/portal_close.png");
        SpriteSheet portalCloseSheet = new SpriteSheet(portalCloseUncut);
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 4; j++) {
                if (j + i*4 >= 17) break;
                portalClose[j + i*4] = portalCloseSheet.crop(j * 32, i * 32, 32, 32);
            }
        }
//        portalClose[17] =

        portalOpen = new BufferedImage[5];
        BufferedImage portalOpenUncut = ImageLoader.loadImage("/image/portal_open.png");
        SpriteSheet portalOpenSheet = new SpriteSheet(portalOpenUncut);
        for (int i = 0; i < 5; i++) {
            portalOpen[i] = portalOpenSheet.crop(i * 32, 0, 32, 32);
        }
    }
}