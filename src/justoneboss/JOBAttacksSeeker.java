package justoneboss;

import java.awt.Rectangle;

public class JOBAttacksSeeker extends JOBAttacks {
    int movemode = 2;
    
    public JOBAttacksSeeker(int startX, int startY, int movemode) {
        setStartX(startX);
        setStartY(startY);
        setMovemode(movemode);
    }

    public void print() {
        System.out.println(startX);
        System.out.println(startY);
        System.out.println(playX);
        System.out.println(playY);
    }

    public void update() {
        currentAttack = "Seeker";
        attackAnim = seekerChase;
        attackAnim.update(1);
        attackImage = attackAnim.getImage();

        if (movemode == 2) {
            if (attackImage == seekerAnim) {
                startY = startY + 6;
            } else if (attackImage == seekerAnim2) {
                startY = startY - 6;
            }
        }

        if (startY > playY) {
            moveY = -1 * 2 - 1;
        } else if (startY < playY) {
            moveY = 1 * 2 + 1;
        } else {
            moveY = 0;
        }

        if (startX > playX) {
            moveX = -1 * 2 - 1;
        } else if (startX < playX) {
            moveX = 1 * 2 + 1;
        } else {
            moveX = 0;
        }

        startX = startX + moveX;
        startY = startY + moveY;

        aHitBox.setRect(startX - 20 * 2, startY - 20 * 2, 38 * 2, 38 * 2);
    }
    
    public void setMovemode(int movemode) {
        this.movemode = movemode;
    }

    public void nope(){
        aHitBox.setRect(-100,0,0,0);
    }
}
