package justoneboss;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.Timer;
import java.util.TimerTask;
import JOB.framework.JOBAnimation;
import java.util.ArrayList;

public class JOBPlayer {

    //screen dimensions: x = 1600, y = 960
    int MOVESPEED = 6;
    //grid-based movement = 1, free movement = 2
    int movemode = 2;
    private int dx = 0;
    private int dy = 0;
    private int x = 800;
    private int y = 480;
    private int w;
    private int h;
    public static int health = 4;
    public ArrayList<Image> hearts = new ArrayList<Image>();
    private boolean movingLeft = false, canMoveLeft = true;
    private boolean movingRight = false, canMoveRight = true;
    private boolean movingUp = false, canMoveUp = true;
    private boolean movingDown = false, canMoveDown = true;
    public enum triPos {
        LEFT, MID, RIGHT;
    }
    triPos pos = triPos.MID;
    public enum State {
        STILL, MOVING, HIT, INVINCIBLE, DEAD;
    }
    State state;
    public enum moveState {
        NOT, MOVINGUP, MOVINGDOWN, MOVINGRIGHT, MOVINGLEFT;
    }
    moveState movestate = moveState.NOT;
    public boolean touchCrate = false, invincible = false, hit = false, test = false, mobile = true;
    public static Rectangle rect = new Rectangle(0, 0, 0, 0);
    public static Rectangle rect2 = new Rectangle(0, 0, 0, 0);
    public static Rectangle yellowRed = new Rectangle(0, 0, 0, 0);
    public static Rectangle footleft = new Rectangle(0, 0, 0, 0);
    public static Rectangle footright = new Rectangle(0, 0, 0, 0);
    private Image nothing, gameOver, playerHurtSparks, fullHeart, emptyHeart, heartImage;
    private Image playerImage, playerImage2, playerRight, playerLeft, playerUp, playerDown, playerHurt, playerHurt2, playerBlinkU, PlayerBlinkD, PlayerBlinkR, PlayerBlinkL,
            playerRunR, playerRunR2, playerRunL, playerRunL2, playerRunU, playerRunU2, playerRunU3, playerRunD, playerRunD2;
    private JOBAnimation currentAnim, playerMoveR, playerMoveL, playerMoveU, playerMoveD, playerIdleR, playerIdleL, playerIdleU, playerIdleD, 
            invinciFrames, invinciFramesR, invinciFramesL, invinciFramesU, invinciFramesD, hitFrames;
    private Timer invincibility, damage;

    public JOBPlayer() {
        loadImage();
    }

    private void loadImage() {       
        
        ImageIcon non = new ImageIcon("src/assets/Nothing.png");
        ImageIcon GO = new ImageIcon("src/assets/Game Over.png");
        nothing = non.getImage();
        gameOver = GO.getImage();

        //Player image setups
        //idle sprites
        ImageIcon pr = new ImageIcon("src/assets/Green Boi Right64.png");
        ImageIcon pl = new ImageIcon("src/assets/Green Boi Left64.png");
        ImageIcon pu = new ImageIcon("src/assets/Green Boi Up64.png");
        ImageIcon pd = new ImageIcon("src/assets/Green Boi Down64.png");
        playerRight = pr.getImage();
        playerLeft = pl.getImage();
        playerUp = pu.getImage();
        playerDown = pd.getImage();
        
        playerIdleR = new JOBAnimation();
        playerIdleR.addFrame(playerRight, 100);
        
        playerIdleL = new JOBAnimation();
        playerIdleL.addFrame(playerLeft, 100);
        
        playerIdleU = new JOBAnimation();
        playerIdleU.addFrame(playerUp, 100);
        
        playerIdleD = new JOBAnimation();
        playerIdleD.addFrame(playerDown, 100);
        
        //running sprites
        ImageIcon pmr1 = new ImageIcon("src/assets/Green Boi MoveR 1.png");
        ImageIcon pmr2 = new ImageIcon("src/assets/Green Boi MoveR 2.png");
        ImageIcon pml1 = new ImageIcon("src/assets/Green Boi MoveL 1.png");
        ImageIcon pml2 = new ImageIcon("src/assets/Green Boi MoveL 2.png");
        ImageIcon pmu1 = new ImageIcon("src/assets/Green Boi MoveU 1.png");
        ImageIcon pmu2 = new ImageIcon("src/assets/Green Boi MoveU 2.png");
        ImageIcon pmu3 = new ImageIcon("src/assets/Green Boi MoveU 3.png");
        ImageIcon pmd1 = new ImageIcon("src/assets/Green Boi MoveD 1.png");
        ImageIcon pmd2 = new ImageIcon("src/assets/Green Boi MoveD 2.png");
        playerRunR = pmr1.getImage();
        playerRunR2 = pmr2.getImage();
        playerRunL = pml1.getImage();
        playerRunL2 = pml2.getImage();
        playerRunU = pmu1.getImage();
        playerRunU2 = pmu2.getImage();
        playerRunU3 = pmu3.getImage();
        playerRunD = pmd1.getImage();
        playerRunD2 = pmd2.getImage();
        
        playerMoveR = new JOBAnimation();
        playerMoveR.addFrame(playerRunR, 100);
        playerMoveR.addFrame(playerRight, 100);
        playerMoveR.addFrame(playerRunR2, 100);
        playerMoveR.addFrame(playerRight, 100);
        
        playerMoveL = new JOBAnimation();
        playerMoveL.addFrame(playerRunL, 100);
        playerMoveL.addFrame(playerLeft, 100);
        playerMoveL.addFrame(playerRunL2, 100);
        playerMoveL.addFrame(playerLeft, 100);
        
        playerMoveU = new JOBAnimation();
        playerMoveU.addFrame(playerRunU, 100);
        playerMoveU.addFrame(playerRunU2, 100);
        playerMoveU.addFrame(playerRunU3, 100);
        playerMoveU.addFrame(playerRunU2, 100);
        
        playerMoveD = new JOBAnimation();
        playerMoveD.addFrame(playerRunD, 100);
        playerMoveD.addFrame(playerRunD2, 100);
        
        //hurt sprites
        ImageIcon ph = new ImageIcon("src/assets/Green Boi Hurt.png");
        ImageIcon ph2 = new ImageIcon("src/assets/Green Boi Hurt2.png");
        ImageIcon phs = new ImageIcon("src/assets/Green Boi Hurt sparks.png");
        playerHurt = ph.getImage();
        playerHurt2 = ph2.getImage();
        playerHurtSparks = phs.getImage();
        
        hitFrames = new JOBAnimation();
        hitFrames.addFrame(playerHurt, 100);
        hitFrames.addFrame(playerHurt2, 100);
        
        invinciFrames = new JOBAnimation();

        invinciFramesR = new JOBAnimation();
        invinciFramesR.addFrame(playerRight, 100);
        invinciFramesR.addFrame(nothing, 100);

        invinciFramesL = new JOBAnimation();
        invinciFramesL.addFrame(playerLeft, 100);
        invinciFramesL.addFrame(nothing, 100);

        invinciFramesU = new JOBAnimation();
        invinciFramesU.addFrame(playerUp, 100);
        invinciFramesU.addFrame(nothing, 100);

        invinciFramesD = new JOBAnimation();
        invinciFramesD.addFrame(playerDown, 100);
        invinciFramesD.addFrame(nothing, 100);
        
        playerImage = playerDown;
        playerImage2 = playerDown;
        
        currentAnim = playerIdleD;        

        //Health image setups
        ImageIcon fhi = new ImageIcon("src/assets/Full Heart 66.png");
        fullHeart = fhi.getImage();
        ImageIcon ehi = new ImageIcon("src/assets/Empty Heart 66.png");
        emptyHeart = ehi.getImage();
        
        hearts.add(fullHeart);
        hearts.add(fullHeart);
        hearts.add(fullHeart);
        hearts.add(fullHeart);    

        w = playerImage.getWidth(null);
        h = playerImage.getHeight(null);
        
    }

    public void update() {
        x = dx + x;
        y = dy + y;
        
        //currentAnim.update(10);
        //playerImage = currentAnim.getImage();
        
        if (isMovingRight() == false && isMovingLeft() == true && isMovingUp() == false && isMovingDown() == false) {
            dx = -MOVESPEED;
            currentAnim = playerMoveL;
        }
        if (isMovingRight() == true && isMovingLeft() == false && isMovingUp() == false && isMovingDown() == false) {
            dx = MOVESPEED;
            currentAnim = playerMoveR;
        }

        if (isMovingRight() == false && isMovingLeft() == false && isMovingUp() == true && isMovingDown() == false) {
            dy = -MOVESPEED;
            currentAnim = playerMoveU;
        }
        if (isMovingRight() == false && isMovingLeft() == false && isMovingUp() == false && isMovingDown() == true) {
            dy = MOVESPEED;
            currentAnim = playerMoveD;
        }

        if (hit == true) {
            hitFrames.update(10);
            playerImage = hitFrames.getImage();
        }else{
            currentAnim.update(7);
            playerImage = currentAnim.getImage();
        }
        
        if(mobile == false && dx != 0 || mobile == false && dy != 0){
            dx = 0;
            dy = 0;
        }
        
        if (invincible == true) {
            invinciFrames.update(10);
            playerImage = invinciFrames.getImage();
        }

        if (invincible == false && playerImage == nothing) {
            playerImage = playerImage2;
        }
        
        if(x < 620){
            pos = triPos.LEFT;
        }else if(x > 620 && x < 980){
            pos = triPos.MID;
        }else if(x > 980){
            pos = triPos.RIGHT;
        }

        rect.setRect(x - 10 * 2, y - 16 * 2, 19 * 2, 16 * 2);
        rect2.setRect(rect.getX(), rect.getY() + 16 * 2, 19 * 2, 16 * 2);
        yellowRed.setRect(x - 43 * 2, y - 47 * 2, 87 * 2, 97 * 2);
        footright.setRect(x - 14 * 2, y - 10 * 2, 20 * 2, 20 * 2);
        footleft.setRect(x, y - 10 * 2, 13 * 2, 20 * 2);
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        //Moves Left
        if (key == KeyEvent.VK_A) {
            if (canMoveLeft == true && mobile == true) {
                dx = -MOVESPEED;
                setMovingLeft(true);
                
                setCanMoveRight(true);
                JOBAttacks.setVSpeed(3);
                if (invincible == true) {
                    invinciFrames = invinciFramesL;
                    playerImage2 = playerLeft;
                } else {
                    //playerImage = playerLeft;

                }
            }
        }

        //Moves Right
        if (key == KeyEvent.VK_D) {
            if (canMoveRight == true && mobile == true) {
                dx = MOVESPEED;
                setMovingRight(true);
                
                setCanMoveLeft(true);
                JOBAttacks.setVSpeed(3);
                if (invincible == true) {
                    invinciFrames = invinciFramesR;
                    playerImage2 = playerRight;
                } else {
                    //playerImage = playerRight;

                }
            }
        }

        //Moves Up
        if (key == KeyEvent.VK_W) {
            if (canMoveUp == true && mobile == true) {
                dy = -MOVESPEED;
                setMovingUp(true);
                
                setCanMoveDown(true);
                if (invincible == true) {
                    invinciFrames = invinciFramesU;
                    playerImage2 = playerUp;
                } else {
                    //playerImage = playerUp;

                }
            }
        }

        //Moves Down
        if (key == KeyEvent.VK_S) {
            if (canMoveDown == true && mobile == true) {
                dy = MOVESPEED;
                setMovingDown(true);
                
                setCanMoveUp(true);
                if (invincible == true) {
                    invinciFrames = invinciFramesD;
                    playerImage2 = playerDown;
                } else {
                    //playerImage = playerDown;

                }
            }
        }

        if (key == KeyEvent.VK_J) {
            if (JOBMain.isPaused()) {
                JOBMain.setPaused(false);
            } else {
                JOBMain.setPaused(true);
            }
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        //Stops character when the key is released
        if (key == KeyEvent.VK_A) {
            dx = 0;
            setMovingLeft(false);
            currentAnim = playerIdleL;
        }

        if (key == KeyEvent.VK_D) {
            dx = 0;
            setMovingRight(false);
            currentAnim = playerIdleR;
        }

        if (key == KeyEvent.VK_W) {
            dy = 0;
            setMovingUp(false);
            currentAnim = playerIdleU;
        }

        if (key == KeyEvent.VK_S) {
            dy = 0;
            setMovingDown(false);
             currentAnim = playerIdleD;
        }

        if (key == KeyEvent.VK_Z) {
            test = false;
        }
    }

    public void stopRight() {
        setMovingRight(false);
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

    public void stopUp() {
        setMovingUp(false);
        stop();
    }

    public void stopDown() {
        setMovingDown(false);
        stop();
    }

    private void stop() {
        if (isMovingRight() == false && isMovingLeft() == false) {
            dx = 0;
        }
        if (isMovingUp() == false && isMovingDown() == false) {
            dy = 0;
        }

        if (isMovingRight() == false && isMovingLeft() == true && isMovingUp() == false && isMovingDown() == false) {
            dx = -MOVESPEED;
        }
        if (isMovingRight() == true && isMovingLeft() == false && isMovingUp() == false && isMovingDown() == false) {
            dx = MOVESPEED;
        }
        if (isMovingRight() == false && isMovingLeft() == false && isMovingUp() == true && isMovingDown() == false) {
            dy = -MOVESPEED;
        }
        if (isMovingRight() == false && isMovingLeft() == false && isMovingUp() == false && isMovingDown() == true) {
            dy = MOVESPEED;
        }

    }

    //lowers health when hit by an attack
    public void takeDamage() {
        if (invincible == false && hit == false) {
            health = health - 1;
            hit = true;
            mobile = false;
            damage = new Timer();
            damage.schedule(new damageOver(), 600);
            
        }
    }
    
    class damageOver extends TimerTask {
        @Override
        public void run() {
            hit = false;
            System.out.println("damage anim over");
            damage.cancel();
            mobile = true;
            invincible = true;
            invincibility = new Timer();
            invincibility.schedule(new InvinceOver(), 2000);
        }
    }

    //the class called by the Timer after three seconds of invincibilty
    class InvinceOver extends TimerTask {

        @Override
        public void run() {
            invincible = false;
            System.out.println("invincibility over");
            invincibility.cancel(); //Terminate the timer thread
        }
    }

    //Getters and setters
    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public int getX() {

        return x;
    }

    public int getY() {

        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {

        return w;
    }

    public int getHeight() {

        return h;
    }

    public Image getPImage() {

        return playerImage;
    }

    public Image getFHeartImage() {

        return fullHeart;
    }

    public Image getEHeartImage() {

        return emptyHeart;
    }

    public Image getGameOver() {
        return gameOver;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public void setMovingUp(boolean movingUp) {
        this.movingUp = movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public void setMovingDown(boolean movingDown) {
        this.movingDown = movingDown;
    }

    public static int getHealth() {
        return health;
    }

    public static void setHealth(int health) {
        JOBPlayer.health = health;
    }

    public boolean isTouchCrate() {
        return touchCrate;
    }

    public void setTouchCrate(boolean touchCrate) {
        this.touchCrate = touchCrate;
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getMovemode() {
        return movemode;
    }

    public boolean isMobile() {
        return mobile;
    }

    public void setMobile(boolean mobile) {
        this.mobile = mobile;
    }

    public boolean isCanMoveLeft() {
        return canMoveLeft;
    }

    public void setCanMoveLeft(boolean canMoveLeft) {
        this.canMoveLeft = canMoveLeft;
    }

    public boolean isCanMoveRight() {
        return canMoveRight;
    }

    public void setCanMoveRight(boolean canMoveRight) {
        this.canMoveRight = canMoveRight;
    }

    public boolean isCanMoveUp() {
        return canMoveUp;
    }

    public void setCanMoveUp(boolean canMoveUp) {
        this.canMoveUp = canMoveUp;
    }

    public boolean isCanMoveDown() {
        return canMoveDown;
    }

    public void setCanMoveDown(boolean canMoveDown) {
        this.canMoveDown = canMoveDown;
    }
    
    public ArrayList<Image> getHearts() {
        return hearts;
    }
    
    public boolean isHit() {
        return hit;
    }

    public Image getPlayerHurtSparks() {
        return playerHurtSparks;
    }
    
    public triPos getPos() {
        return pos;
    }
    
    public static Rectangle getRect() {
        return rect;
    }

    public static Rectangle getRect2() {
        return rect2;
    }

    public static Rectangle getYellowRed() {
        return yellowRed;
    }

    public static Rectangle getFootleft() {
        return footleft;
    }

    public static Rectangle getFootright() {
        return footright;
    }
}
