package justoneboss;

import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.Rectangle;
import java.util.Random;
import JOB.framework.JOBAnimation;

public class JOBAttacks {

    public int startX, startY, moveY, moveX;
    public JOBAnimation attackAnim;
    public Image attackImage;
    public Rectangle aHitBox = new Rectangle(0, 0, 0, 0);
    static Random rand = new Random();
    String currentAttack = "none";
    public boolean running = false;
    public static int VSpeed = 3;
    public Image preLaser, laser, seekerAnim, seekerAnim2, sizzleM, sizzTarg, sizzShotR, sizzShotL, sizzShotU, sizzShotD, gly;
    public Image nothing, nothing2, currentAttackIm = nothing, currentAttackIm2 = nothing;
    public static JOBAnimation currentAAnim, laserShot, seekerChase;

    public static JOBPlayer mario = JOBMain.getPlayer();
    public static int playX = mario.getX();
    public static int playY = mario.getY();

    public JOBAttacks() {
        loadImage();
    }

    private void loadImage() {
        //laser assets and animation
        ImageIcon space = new ImageIcon("src/assets/Nothing.png");
        ImageIcon laserR = new ImageIcon("src/assets/Pre Laser2.png");
        ImageIcon Laser = new ImageIcon("src/assets/Nothing2.png");
        nothing = space.getImage();
        preLaser = laserR.getImage();
        laser = Laser.getImage();
        laserShot = new JOBAnimation();
        laserShot.addFrame(nothing2, 100);
        laserShot.addFrame(preLaser, 100);
        laserShot.addFrame(nothing, 100);
        laserShot.addFrame(preLaser, 100);
        laserShot.addFrame(nothing, 100);
        laserShot.addFrame(preLaser, 100);
        laserShot.addFrame(nothing, 100);
        laserShot.addFrame(laser, 1000);

        //seeker assets and animation
        ImageIcon skr = new ImageIcon("src/assets/Seeker90.png");
        ImageIcon skr2 = new ImageIcon("src/assets/Seeker902.png");
        seekerAnim = skr.getImage();
        seekerAnim2 = skr2.getImage();
        seekerChase = new JOBAnimation();
        seekerChase.addFrame(seekerAnim, 100);
        seekerChase.addFrame(seekerAnim2, 100);

        //sizzle assets and animation
        ImageIcon szm = new ImageIcon("src/assets/Sizzle misslex2.png");
        ImageIcon szt = new ImageIcon("src/assets/SizzleTargx2.png");
        ImageIcon szr = new ImageIcon("src/assets/Sizzle Shot Rx2.png");
        ImageIcon szl = new ImageIcon("src/assets/Sizzle Shot Lx2.png");
        ImageIcon szu = new ImageIcon("src/assets/Sizzle Shot Ux2.png");
        ImageIcon szd = new ImageIcon("src/assets/Sizzle Shot Dx2.png");
        sizzleM = szm.getImage();
        sizzTarg = szt.getImage();
        sizzShotR = szr.getImage();
        sizzShotL = szl.getImage();
        sizzShotU = szu.getImage();
        sizzShotD = szd.getImage();
        
        //swarm assets
        ImageIcon gy = new ImageIcon("src/assets/Gly2.png");
        gly = gy.getImage();
    }

    public static void bossAttacks() {
        playX = mario.getX();
        playY = mario.getY();
    }


    public int randomXpos() {
        return rand.nextInt(6) + 1;
    }

    //Getters and setters
    public String getCurrentAttack() {
        return currentAttack;
    }

    public Image getPreLaser() {

        return preLaser;
    }

    public Image getLaser() {
        return laser;
    }
    
    public void setStartX(int startX) {
        this.startX = startX;
    }

    public int getStartX() {
        return startX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }
    
     public static void setVSpeed(int VSpeed) {
        JOBAttacks.VSpeed = VSpeed;
    }

    public Rectangle getaHitBox() {
        return aHitBox;
    }

    public Image getAttackImage() {
        return attackImage;
    }
    
    public void setRunning(boolean running) {
        this.running = running;
    }
}