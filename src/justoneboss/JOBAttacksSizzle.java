package justoneboss;

import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;

public class JOBAttacksSizzle extends JOBAttacks {

    int boomX, boomY, missY, shotXR, shotXL, shotYU, shotYD;
    Rectangle shot1 = new Rectangle(0, 0, 0, 0), shot2 = new Rectangle(0, 0, 0, 0),
            shot3 = new Rectangle(0, 0, 0, 0), shot4 = new Rectangle(0, 0, 0, 0);

    public ArrayList<Rectangle> shots = new ArrayList<Rectangle>();
    public ArrayList<Image> shotImages = new ArrayList<Image>();

    public JOBAttacksSizzle(int startX, int startY) {
        setStartX(startX);
        setStartY(startY);
    }

    public void print() {
        System.out.println(shotXR);
    }

    public void update() {
        attackImage = sizzleM;
        boomX = 95 * 2 + startX * 64 - 64;
        boomY = 160 * 2 + startY * 64 - 64;
        missY = aHitBox.y;

        if (missY == boomY) {
            moveY = 0;
            quadShot();
        } else {
            moveY = 8;
            shotXR = aHitBox.x;
            shotXL = aHitBox.x;
            shotYD = aHitBox.y;
            shotYU = aHitBox.y;
        }
        aHitBox.setRect(boomX, missY + moveY, 32 * 2, 32 * 2);
    }

    public void quadShot() {
        if (shot1.x > 800 * 2 && shot2.x < -45 * 2 && shot3.y > 480 * 2 && shot4.y < 0 * 2) {

        } else {
            shots.add(shot1);
            shots.add(shot2);
            shots.add(shot3);
            shots.add(shot4);
            shotImages.add(sizzShotR);
            shotImages.add(sizzShotL);
            shotImages.add(sizzShotD);
            shotImages.add(sizzShotU);
            int dShot = 13;
            shotXR = shotXR + dShot;
            shot1.setRect(shotXR, aHitBox.y + 8 * 2, 45 * 2, 15 * 2);
            shotXL = shotXL - dShot;
            shot2.setRect(shotXL, aHitBox.y + 8 * 2, 45 * 2, 15 * 2);
            shotYD = shotYD + dShot;
            shot3.setRect(aHitBox.x + 8 * 2, shotYD, 15 * 2, 45 * 2);
            shotYU = shotYU - dShot;
            shot4.setRect(aHitBox.x + 8 * 2, shotYU, 15 * 2, 45 * 2);
        }
    }

    public ArrayList<Rectangle> getShots() {
        return shots;
    }

    public ArrayList<Image> getShotImages() {
        return shotImages;
    }

    public Rectangle getShot1() {
        return shot1;
    }

    public Rectangle getShot2() {
        return shot2;
    }

    public Rectangle getShot3() {
        return shot3;
    }

    public Rectangle getShot4() {
        return shot4;
    }

    public Image getSizzTarg() {
        return sizzTarg;
    }

    public int getBoomX() {
        return boomX;
    }

    public int getBoomY() {
        return boomY;
    }
    
    public void nope(){
        shot1.setRect(-100,0,0,0);
        shot2.setRect(-100,0,0,0);
        shot3.setRect(-100,0,0,0);
        shot4.setRect(-100,0,0,0);
        aHitBox.setRect(-100,0,0,0);
        boomX = -100;
        
    }

}
