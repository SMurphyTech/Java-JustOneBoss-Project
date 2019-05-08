package justoneboss;

import java.awt.Image;

public class JOBAttacksLaser extends JOBAttacks {

    public JOBAttacksLaser(int startY) {
        setStartY(startY);
    }

    public void print() {
        System.out.println(startY);
    }

    public void update() {
        currentAttack = "Laser";
        attackAnim = laserShot;
        attackAnim.update(8);
        attackImage = attackAnim.getImage();

        if (attackImage == nothing2) {
            switch (randomXpos()) {
                case 1:
                    startX = 207 * 2;
                    break;
                case 2:
                    startX = 303 * 2;
                    break;
                case 3:
                    startX = 400 * 2;
                    break;
                case 4:
                    startX = 495 * 2;
                    break;
                case 5:
                    startX = 591 * 2;
                    break;
                case 6:
                    startX = 687 * 2;
                    break;
            }
        }
        if (attackImage == laser) {
            aHitBox.setRect(startX - 37 * 2, startY, 74 * 2, 400 * 2);
        } else {
            aHitBox.setRect(0, 0, 0, 0);
        }
    }

    public Image getLaser() {
        return laser;
    }
    
    public void nope(){
        startX = -100;
        aHitBox.setRect(0, 0, 0, 0);
    }
}