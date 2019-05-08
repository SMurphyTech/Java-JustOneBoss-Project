package justoneboss;

import java.awt.Rectangle;
import java.util.Random;

public class JOBRetaliate {

    public static int points = 1, coordsX, coordsY, coordsX2, coordsY2, sbx, sby, hbx, hby, distanceKeeper;
    static Rectangle scoreBox = new Rectangle();
    static Rectangle healthBox = new Rectangle();
    static Random chance = new Random();
    private static JOBPlayer mario = JOBMain.getPlayer();
    private static JOBBoss boss = JOBMain.getBoss();

    public JOBRetaliate() {
        coordsX = chance.nextInt(19) + 1;
        coordsY = chance.nextInt(6) + 3;
        coordsX2 = chance.nextInt(19) + 1;
        coordsY2 = chance.nextInt(6) + 3;
    }

    public static void update() {
        sbx = 95 * 2 + coordsX * 64 - 64;
        sby = 160 * 2 + coordsY * 64 - 64;
        //if(boss.getState() == boss.getState().getPHASE1()){
            scoreBox.setBounds(sbx, sby, 64, 64);
        //}
        if (JOBPlayer.footright.intersects(scoreBox)) {
            boss.setSummoned(false);
            //System.out.println(boss.isSummoned());
            points = points + 1;
            while(true){
                distanceKeeper = chance.nextInt(19) + 1;
                if(distanceKeeper > coordsX + 7 || distanceKeeper < coordsX - 7){
                    coordsX = distanceKeeper;
                    break;
                }else{
                   
                }
            }
            coordsY = chance.nextInt(6) + 3;
            boss.checkPhase();
        }
        hbx = 95 * 2 + coordsX2 * 64 - 64;
        hby = 160 * 2 + coordsY2 * 64 - 64;
        healthBox.setBounds(hbx, hby, 64, 64);
        if (JOBPlayer.footright.intersects(healthBox)) {
            mario.setHealth(mario.getHealth() + 1);
            //coordsX2 = chance.nextInt(19) + 1;
            //coordsY2 = chance.nextInt(7) + 1;
            coordsX2 = -200;
            coordsY2 = -200;
        }

    }

    public static Rectangle getHealthBox() {
        return healthBox;
    }

    public static Rectangle getScoreBox() {
        return scoreBox;
    }

    public static int getPoints() {
        return points;
    }
}
