package justoneboss;

import JOB.framework.JOBAnimation;
import java.util.ArrayList;
import java.awt.Image;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;

public class JOBBoss {

    static int phase = 0, i = 0, control = 0;
    static int bossX = 800 - 180, bossY = 400;
    public Image bossImage, bossIdleC, bossIdleR, bossIdleL, bossIdleCL, bossIdleRL, bossIdleLL, bossIdleCR, bossIdleRR, bossIdleLR, controlIdle1, controlIdle2, controlIdle3,
            bossShrouded, bossLaser1, bossLaser2, summon1, summon2, summonFlash, flashSeek, flashSizz, flashGly, flashVac;
    public JOBAnimation bossAnim, bossIdle, bossIdleEyesR, bossIdleEyesL, bossSummon;
    private boolean set = false, set2 = false, summoned = false;
    public Timer timer;

    public enum bossState {
        INTRO, PHASE1, LASERS, SUMMONING;

        public static bossState getSUMMONING() {
            return SUMMONING;
        }

        public static bossState getLASERS() {
            return LASERS;
        }

        public static bossState getINTRO() {
            return INTRO;
        }

        public static bossState getPHASE1() {
            return PHASE1;
        }
    }
    bossState state, placeHold;
    public JOBAttacksSeeker seek1, seek2;
    public JOBAttacksLaser laser, laser2;
    public JOBAttacksVaccuum vaccuum;
    public JOBAttacksSizzle sizzle, sizzle2, sizzle3, sizzle4;
    public JOBAttacksSwarm swarm, swarm2;
    public ArrayList<JOBAttacksSizzle> sizzles = new ArrayList<JOBAttacksSizzle>();
    private JOBPlayer mario = JOBMain.getPlayer();
    private JOBBoss boss = JOBMain.getBoss();

    public JOBBoss() {
        loadImage();
    }

    private void loadImage() {
        state = bossState.INTRO;

        ImageIcon bossIC = new ImageIcon("src/assets/Slywinder.png");
        ImageIcon bossICL = new ImageIcon("src/assets/SlywinderEyesL.png");
        ImageIcon bossICR = new ImageIcon("src/assets/SlywinderEyesR.png");
        ImageIcon bossIR = new ImageIcon("src/assets/SlywinderIdleR_1.png");
        ImageIcon bossIRL = new ImageIcon("src/assets/SlywinderIdleRL.png");
        ImageIcon bossIRR = new ImageIcon("src/assets/SlywinderEyesRR.png");
        ImageIcon bossIL = new ImageIcon("src/assets/SlywinderIdleL.png");
        ImageIcon bossILL = new ImageIcon("src/assets/SlywinderIdleLL.png");
        ImageIcon bossILR = new ImageIcon("src/assets/SlywinderEyesLR.png");

        ImageIcon bossDark = new ImageIcon("src/assets/Slywinder shrouded.png");
        ImageIcon bossLase1 = new ImageIcon("src/assets/SlywinderLaseFace2.png");
        ImageIcon bossLase2 = new ImageIcon("src/assets/SlywinderLaseFace3.png");
        ImageIcon sum1 = new ImageIcon("src/assets/SlywinderSum1.png");
        ImageIcon sum2 = new ImageIcon("src/assets/SlywinderSum1.png");
        ImageIcon sumFlash = new ImageIcon("src/assets/SummonFlash.png");
        ImageIcon FlSeek = new ImageIcon("src/assets/FlashEyesSeek2.png");
        ImageIcon FlSizz = new ImageIcon("src/assets/FlashEyesSeek2.png");
        ImageIcon FlGly = new ImageIcon("src/assets/FlashEyesSeek2.png");
        ImageIcon FlVac = new ImageIcon("src/assets/FlashEyesSeek2.png");

        //Idle Image setups
        bossIdleC = bossIC.getImage();
        bossIdleCL = bossICL.getImage();
        bossIdleCR = bossICR.getImage();
        bossIdleR = bossIR.getImage();
        bossIdleRL = bossIRL.getImage();
        bossIdleRR = bossIRR.getImage();
        bossIdleL = bossIL.getImage();
        bossIdleLL = bossILL.getImage();
        bossIdleLR = bossILR.getImage();
        controlIdle1 = bossIdleC;
        controlIdle2 = bossIdleR;
        controlIdle3 = bossIdleL;

        //Special Image setups
        bossShrouded = bossDark.getImage();
        bossLaser1 = bossLase1.getImage();
        bossLaser2 = bossLase2.getImage();
        summon1 = sum1.getImage();
        summon2 = sum2.getImage();
        summonFlash = sumFlash.getImage();
        flashSeek = FlSeek.getImage();
        flashSizz = FlSizz.getImage();
        flashGly = FlGly.getImage();
        flashVac = FlVac.getImage();
        bossImage = bossIdleC;

        //Idle animations
        bossIdle = new JOBAnimation();
        bossIdle.addFrame(controlIdle1, 100);
        bossIdle.addFrame(controlIdle2, 100);
        bossIdle.addFrame(controlIdle1, 100);
        bossIdle.addFrame(controlIdle3, 100);

        bossIdleEyesL = new JOBAnimation();
        bossIdleEyesL.addFrame(bossIdleCL, 100);
        bossIdleEyesL.addFrame(bossIdleRL, 100);
        bossIdleEyesL.addFrame(bossIdleCL, 100);
        bossIdleEyesL.addFrame(bossIdleLL, 100);

        bossIdleEyesR = new JOBAnimation();
        bossIdleEyesR.addFrame(bossIdleCR, 100);
        bossIdleEyesR.addFrame(bossIdleRR, 100);
        bossIdleEyesR.addFrame(bossIdleCR, 100);
        bossIdleEyesR.addFrame(bossIdleLR, 100);

        //special Animations
        bossSummon = new JOBAnimation();
        bossSummon.addFrame(summon1, 1000);
        bossSummon.addFrame(summonFlash, 100);

        bossAnim = bossIdle;
    }

    public void attacksSetUp() {
        seek1 = new JOBAttacksSeeker(1400, 10, 2);
        seek2 = new JOBAttacksSeeker(400, 50, 1);
        laser = new JOBAttacksLaser(10);
        laser2 = new JOBAttacksLaser(10);
        vaccuum = new JOBAttacksVaccuum("left");
        sizzle = new JOBAttacksSizzle(15, 7);
        sizzle2 = new JOBAttacksSizzle(3, 3);
        sizzle3 = new JOBAttacksSizzle(9, 4);
        sizzle4 = new JOBAttacksSizzle(19, 5);

        sizzles.add(sizzle);
        sizzles.add(sizzle2);
        sizzles.add(sizzle3);
        sizzles.add(sizzle4);

        swarm = new JOBAttacksSwarm("Hdown", 3);
        swarm2 = new JOBAttacksSwarm("Hdown", 2);
    }

    //my hi score: 32
    public void attack() throws InterruptedException {

        updatePhase();
        //System.out.println(summoned);

        switch (phase) {
            case 0:
                sizzle.nope();
                sizzle2.nope();
                sizzle3.nope();
                sizzle4.nope();
                break;
            case 1:
                laser.update();
                state = bossState.LASERS;
                break;
            case 2:
                if (summoned == false) {
                    laser.nope();
                    state = bossState.SUMMONING;
                } else if (summoned == true) {
                    seek1.update();
                }
                /*
                    if(state == bossState.LASERS){
                        laser.update();
                    }else{
                        waitState(bossState.LASERS, 1);
                    }
                }
                 */
                break;
            case 3:
                if (summoned == false) {
                    laser.nope();
                    seek1.nope();
                    state = bossState.SUMMONING;
                } else if (summoned == true) {
                    sizzle.update();
                    sizzle2.update();
                    sizzle3.update();
                    sizzle4.update();
                }
                break;
            case 4:
                if (summoned == false) {
                    sizzle.nope();
                    sizzle2.nope();
                    sizzle3.nope();
                    sizzle4.nope();
                    state = bossState.SUMMONING;
                } else if (summoned == true) {
                    swarm.update();
                }
                break;
            case 5:
                if (summoned == false) {
                    swarm.nope();
                    state = bossState.SUMMONING;
                } else if (summoned == true) {
                    vaccuum.update();
                }
                
                break;
            case 6:
                
                break;
            case 7:
                
                //without a break, this goes on forever
            case 8:
                
            default:
                break;
        }
    }

    /* summon stucture (couldn't make it into a method)
        if (summoned == false) {
            laser.nope();
            state = bossState.SUMMONING;
        } else if (summoned == true) {
            *.update()
        }
     */
    public static void checkPhase() {
        switch (JOBRetaliate.getPoints()) {
            case 2:
                phase = 1;
                break;
            case 3:
                phase = 2;
                break;
            case 4:
                phase = 3;
                break;
            case 5:
                phase = 4;
                break;
            case 6:
                phase = 5;
                break;
            case 7:
                phase = 6;
                break;
            case 8:
                phase = 7;
                break;
            default:
                break;
        }
    }

    public void updatePhase() {
        //System.out.println(state);

        if (state == bossState.INTRO) {
            if (bossY == 0) {
                waitState(bossState.PHASE1, 1);
                //TimeUnit.SECONDS.sleep(1);
                //state = bossState.PHASE1;
            } else {
                bossY = bossY - 2;
                bossImage = bossShrouded;
            }
        }

        if (state == bossState.PHASE1) {
            bossImage = bossIdleCR;
            if (null != mario.getPos()) {
                switch (mario.getPos()) {
                    case LEFT:
                        /*
                        controlIdle1 = bossIdleCL;
                        controlIdle2 = bossIdleRL;
                        controlIdle3 = bossIdleLL;
                         */
                        break;
                    case MID:

                        bossAnim = bossIdle;
                        break;
                    case RIGHT:

                        bossAnim = bossIdleEyesR;
                        break;
                    default:
                        break;
                }
            }

            //disables slywinder eye following
            bossAnim = bossIdle;

            bossAnim.update(2);
            bossImage = bossAnim.getImage();
        }

        if (state == bossState.LASERS) {
            bossImage = bossLaser2;
        }

        if (state == bossState.SUMMONING) {
            if (bossImage != summonFlash) {
                if (set == false) {
                    if (bossX > 800 - 180) {
                        bossX = bossX - 10;
                    } else {
                        bossX = bossX + 10;
                    }
                }
                bossAnim = bossSummon;
                bossAnim.update(4);
                bossImage = bossAnim.getImage();
                wait1sec(50);
            } else {
                bossX = 800 - 180 + 47;
                summoned = true;
                waitState(bossState.PHASE1, 1);
                bossAnim.update(4);
            }
        }

    }

    public void waitState(bossState nextState, int seconds) {
        timer = new Timer();
        timer.schedule(new stateOver(), seconds * 1000);
        placeHold = nextState;

    }

    public void wait1sec(int time) {
        if (set == false) {
            timer = new Timer();
            timer.schedule(new secOver(), time);
            set = true;
        }
    }

    public void wait1sec2(int time) {
        if (set2 == false) {
            timer = new Timer();
            timer.schedule(new secOver2(), time);
            set2 = true;
        }
    }

    class stateOver extends TimerTask {

        @Override
        public void run() {
            state = placeHold;
            timer.cancel();
            set = false;
            if (placeHold == bossState.PHASE1) {
                bossX = 800 - 180;
            }
        }
    }

    class secOver extends TimerTask {

        @Override
        public void run() {
            timer.cancel();
            set = false;
        }
    }

    class secOver2 extends TimerTask {

        @Override
        public void run() {
            timer.cancel();
            set2 = false;
        }
    }

    public static int getPhase() {
        return phase;
    }

    public static void setPhase(int phase) {
        JOBBoss.phase = phase;
    }

    public ArrayList<JOBAttacksSizzle> getSizzles() {
        return sizzles;
    }

    public Image getBossImage() {
        return bossImage;
    }

    public bossState getState() {
        return state;
    }

    public static int getBossX() {
        return bossX;
    }

    public static int getBossY() {
        return bossY;
    }

    public Image getBossIdleL() {
        return bossIdleL;
    }

    public Image getBossIdleLL() {
        return bossIdleLL;
    }

    public Image getBossIdleLR() {
        return bossIdleLR;
    }

    public Image getBossLaser1() {
        return bossLaser1;
    }

    public void setSummoned(boolean summoned) {
        this.summoned = summoned;
    }

    public boolean isSummoned() {
        return summoned;
    }
    
    public Image getFlashSeek() {
        return flashSeek;
    }
    
    public Image getSummonFlash() {
        return summonFlash;
    }
}
