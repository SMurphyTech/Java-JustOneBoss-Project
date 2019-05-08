package justoneboss;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;

public class JOBTile {

    private int tileX, tileY, speedX, speedY, type;
    public Image tileImage, crate, PHT, lava;
    public static Rectangle r;
    private static boolean mobile = true, contactUp = false, contactDown = false, contactRight = false, contactLeft = false;
    private JOBAttacks attacks = JOBMain.getAttacks();
    private JOBBoss boss = JOBMain.getBoss();
    private JOBPlayer mario = JOBMain.getPlayer();
    private Rectangle attackHitbox = attacks.getaHitBox();

    public JOBTile(int x, int y, int typeInt) {
        loadImage();
        tileX = x * 32 * 2;
        tileY = y * 32 * 2;

        type = typeInt;

        r = new Rectangle();

        setImage();

    }

    public void setImage() {
        switch (type) {
            case 1:
                tileImage = crate;
                break;
            case 2:
                tileImage = PHT;
                break;
            case 3:
                tileImage = lava;
                break;
            default:
                type = 0;
                break;
        }
    }

    public void loadImage() {
        ImageIcon ic = new ImageIcon("src/assets/Nothing.png");
        crate = ic.getImage();
        ImageIcon ig = new ImageIcon("src/assets/Place Holder Tile64.png");
        PHT = ig.getImage();
        ImageIcon id = new ImageIcon("src/assets/Danger Tile .png");
        lava = id.getImage();
    }

    public void update() {
        speedY = 0;

        tileY += speedY;

        speedX = 0;

        tileX += speedX;

        r.setBounds(tileX, tileY, 32 * 2, 32 * 2);

        if (r.intersects(JOBPlayer.yellowRed)) {
            checkSideCollision(JOBPlayer.footleft, JOBPlayer.footright);
            checkVerticalCollision(JOBPlayer.rect, JOBPlayer.rect2);
        }

        /*
        if (r.intersects(attackHitbox)){
            checkTileCollision(attackHitbox);
        }
         */
        setImage();

    }

    //Getters and setters
    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public int getTileX() {
        return tileX;
    }

    public void setTileX(int tileX) {
        this.tileX = tileX;
    }

    public int getTileY() {
        return tileY;
    }

    public void setTileY(int tileY) {
        this.tileY = tileY;
    }

    public Image getTileImage() {
        return tileImage;
    }

    public void setTileImage(Image tileImage) {
        this.tileImage = tileImage;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public static boolean isContactUp() {
        return contactUp;
    }

    public static void setContactUp(boolean contactUp) {
        JOBTile.contactUp = contactUp;
    }

    public static boolean isContactDown() {
        return contactDown;
    }

    public static void setContactDown(boolean contactDown) {
        JOBTile.contactDown = contactDown;
    }

    public static boolean isContactRight() {
        return contactRight;
    }

    public static void setContactRight(boolean contactRight) {
        JOBTile.contactRight = contactRight;
    }

    public static boolean isContactLeft() {
        return contactLeft;
    }

    public static void setContactLeft(boolean contactLeft) {
        JOBTile.contactLeft = contactLeft;
    }

    public void checkVerticalCollision(Rectangle rtop, Rectangle rbot) {

        if (rtop.intersects(r) && type == 1) {
            mario.setDy(0);
            mario.setCanMoveUp(false);
        }

        if (rbot.intersects(r) && type == 1) {
            mario.setDy(0);
            mario.setCanMoveDown(false);
        }
        
        for (int r = 0; r < boss.swarm.getGlyBoxes().size(); r++) {
            if (rtop.intersects(boss.swarm.getGlyBoxes().get(r))) {
                mario.takeDamage();
            }
        }

    }

    public void checkSideCollision(Rectangle leftfoot, Rectangle rightfoot) {

        if (rightfoot.intersects(r) && type == 1) {
            mario.setDx(0);
            mario.setCanMoveLeft(false);
            JOBAttacks.setVSpeed(0);
        }

        if (leftfoot.intersects(r) && type == 1) {
            mario.setDx(0);
            mario.setCanMoveRight(false);
            JOBAttacks.setVSpeed(0);
        }

        if (rightfoot.intersects(r) && type == 3) {
            mario.takeDamage();
        }

        if (rightfoot.intersects(boss.seek1.getaHitBox()) || leftfoot.intersects(boss.seek1.getaHitBox()) || leftfoot.intersects(boss.laser.getaHitBox())) {
            mario.takeDamage();
        }
        if (rightfoot.intersects(boss.seek2.getaHitBox()) || leftfoot.intersects(boss.seek2.getaHitBox()) || leftfoot.intersects(boss.laser.getaHitBox())) {
            mario.takeDamage();
        }

        //checks for contact with sizzle shots
        for (int i = 0; i < boss.getSizzles().size(); i++) {
            for (int m = 0; m < boss.getSizzles().get(i).getShots().size(); m++) {
                if (rightfoot.intersects(boss.getSizzles().get(i).getShots().get(m))) {
                    mario.takeDamage();
                }
            }
        }
    }



public void checkTileCollision(Rectangle AHB) {

        if (AHB.intersects(r) && type == 2) {
            type = 3;
        }
    }
}
