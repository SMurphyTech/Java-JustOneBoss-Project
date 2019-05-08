package justoneboss;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import JOB.framework.JOBAnimation;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class JOBMain extends JPanel implements ActionListener, Runnable {

    private Timer timer;
    private static JOBPlayer mario;
    private static JOBBoss boss;
    private static JOBAttacks attacks;
    private static JOBRetaliate retal;
    private final int DELAY = 10;
    private Image AS;
    private Thread animator;
    private int x, y;
    public int i = 1;  
    private static boolean paused, lasering;
    private JOBAnimation attackAnim;

    private ArrayList<JOBTile> tilearray = new ArrayList<JOBTile>(); 

    public JOBMain() {

        initBoard();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.black);
        setDoubleBuffered(true);

        mario = new JOBPlayer();
        boss = new JOBBoss();
        attacks = new JOBAttacks();
        retal = new JOBRetaliate();

        boss.attacksSetUp();

        try {
            loadMap("src/assets/TestMap.txt");
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        //draws boss in correct state
        if (boss.getState() == boss.getState().getINTRO()) {
            lasering = false;
            g2d.drawImage(boss.getBossImage(), boss.getBossX(), boss.getBossY(), this);
        } else if (boss.getState() == boss.getState().getPHASE1()) {
            if (boss.getBossImage() == boss.getBossIdleL() || boss.getBossImage() == boss.getBossIdleLL() || boss.getBossImage() == boss.getBossIdleLR()) {
                lasering = false;
                g2d.drawImage(boss.getBossImage(), boss.getBossX() - 235 + 180, boss.getBossY(), this);
            } else {
                lasering = false;
                g2d.drawImage(boss.getBossImage(), boss.getBossX(), boss.getBossY(), this);
            }
        } else if (boss.getState() == boss.getState().getLASERS()) {
            lasering = true;
        } else if (boss.getState() == boss.getState().getSUMMONING()) {
            lasering = false;
            g2d.drawImage(boss.getBossImage(), boss.getBossX() - 47, boss.getBossY(), this);
            if (boss.getBossImage() == boss.getSummonFlash()) {
                g2d.drawImage(boss.getFlashSeek(), boss.getBossX() - 296 - 47, boss.getBossY() - 128, this);
            }
        }

        paintTiles(g2d);

        /*
        for (int h = 0; h < mario.getHearts().size(); h++) {
            
            switch (mario.getHealth()) {
                case 4:
                    break;
                case 3:
                    mario.getHearts().remove(4);
                    mario.getHearts().add(4, mario.getEHeartImage());
                    break;
                case 2:
                    mario.getHearts().remove(3);
                    mario.getHearts().add(3, mario.getEHeartImage());
                    break;
                case 1:
                    mario.getHearts().remove(2);
                    mario.getHearts().add(2, mario.getEHeartImage());
                    break;
                case 0:
                    mario.getHearts().remove(1);
                    mario.getHearts().add(1, mario.getEHeartImage());
                    g2d.drawImage(mario.getGameOver(), 360 * 2, 50 * 2, this);
                    break;
            }
            g2d.drawImage(mario.getHearts().get(h), h * 80 + 10, 10, this);           
        }
         */
        switch (mario.getHealth()) {
            case 4:
                g2d.drawImage(mario.getFHeartImage(), 7 * 2, 5 * 2, this);
                g2d.drawImage(mario.getFHeartImage(), 47 * 2, 5 * 2, this);
                g2d.drawImage(mario.getFHeartImage(), 87 * 2, 5 * 2, this);
                g2d.drawImage(mario.getFHeartImage(), 127 * 2, 5 * 2, this);
                break;
            case 3:
                g2d.drawImage(mario.getFHeartImage(), 7 * 2, 5 * 2, this);
                g2d.drawImage(mario.getFHeartImage(), 47 * 2, 5 * 2, this);
                g2d.drawImage(mario.getFHeartImage(), 87 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 127 * 2, 5 * 2, this);
                break;
            case 2:
                g2d.drawImage(mario.getFHeartImage(), 7 * 2, 5 * 2, this);
                g2d.drawImage(mario.getFHeartImage(), 47 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 87 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 127 * 2, 5 * 2, this);
                break;
            case 1:
                g2d.drawImage(mario.getFHeartImage(), 7 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 47 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 87 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 127 * 2, 5 * 2, this);
                break;
            case 0:
                g2d.drawImage(mario.getEHeartImage(), 7 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 47 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 87 * 2, 5 * 2, this);
                g2d.drawImage(mario.getEHeartImage(), 127 * 2, 5 * 2, this);
                g2d.drawImage(mario.getGameOver(), 360 * 2, 50 * 2, this);
                break;
        }

        //displays scorebox
        g2d.setColor(Color.CYAN);
        g2d.fillRect(retal.getScoreBox().x, retal.getScoreBox().y, retal.getScoreBox().width, retal.getScoreBox().height);
        //g2d.drawString(retal.getPoints().toString(), 1500, 100);

        //displays HealthBox
        //g2d.drawRect(retal.getHealthBox().x, retal.getHealthBox().y, retal.getHealthBox().width, retal.getHealthBox().height);
        g2d.drawImage(mario.getFHeartImage(), retal.getHealthBox().x, retal.getHealthBox().y, this);

        //displays sizzle attacks
        g2d.setColor(Color.WHITE);
        for (int i = 0; i < boss.getSizzles().size(); i++) {
            //.drawRect(boss.getSizzles().get(i).getaHitBox().x, boss.getSizzles().get(i).getaHitBox().y, boss.getSizzles().get(i).getaHitBox().width, boss.getSizzles().get(i).getaHitBox().height);
            g2d.drawImage(boss.getSizzles().get(i).getAttackImage(), boss.getSizzles().get(i).getaHitBox().x + 5 * 2, boss.getSizzles().get(i).getaHitBox().y - 10 * 2, this);
            g2d.drawImage(boss.getSizzles().get(i).getSizzTarg(), boss.getSizzles().get(i).getBoomX() + 1 * 2, boss.getSizzles().get(i).getBoomY() + 1 * 2, this);
            for (int m = 0; m < boss.getSizzles().get(i).getShots().size(); m++) {
                //g2d.drawRect(boss.getSizzles().get(i).getShots().get(m).x, boss.getSizzles().get(i).getShots().get(m).y,
                //boss.getSizzles().get(i).getShots().get(m).width, boss.getSizzles().get(i).getShots().get(m).height);
                g2d.drawImage(boss.getSizzles().get(i).getShotImages().get(m), boss.getSizzles().get(i).getShots().get(m).x,
                        boss.getSizzles().get(i).getShots().get(m).y, this);
            }
        }

        //displays seeker attacks
        g2d.setColor(Color.YELLOW);
        g2d.drawImage(boss.seek1.getAttackImage(), boss.seek1.getaHitBox().x - 3 * 2, boss.seek1.getaHitBox().y - 3 * 2, this);
        //g2d.drawRect(boss.seek1.getaHitBox().x, boss.seek1.getaHitBox().y, boss.seek1.getaHitBox().width, boss.seek1.getaHitBox().height);
        g2d.drawImage(boss.seek2.getAttackImage(), boss.seek2.getaHitBox().x - 3 * 2, boss.seek2.getaHitBox().y - 3 * 2, this);
        //g2d.drawRect(boss.seek2.getaHitBox().x, boss.seek2.getaHitBox().y, boss.seek2.getaHitBox().width, boss.seek2.getaHitBox().height);

        //displays swarm attacks
        g2d.setColor(Color.CYAN);
        for (int r = 0; r < boss.swarm.getGlyBoxes().size(); r++) {
            if (boss.swarm.getGlyBoxes().get(r).x > 85) {
                //g2d.drawRect(boss.swarm.getGlyBoxes().get(r).x, boss.swarm.getGlyBoxes().get(r).y, 32 * 2, 32 * 2);
                g2d.drawImage(boss.swarm.getAttackImage(), boss.swarm.getGlyBoxes().get(r).x, boss.swarm.getGlyBoxes().get(r).y, this);
            }
        }
        for (int r = 0; r < boss.swarm2.getGlyBoxes().size(); r++) {
            if (boss.swarm2.getGlyBoxes().get(r).x > 85) {
                //g2d.drawRect(boss.swarm.getGlyBoxes().get(r).x, boss.swarm.getGlyBoxes().get(r).y, 32 * 2, 32 * 2);
                g2d.drawImage(boss.swarm2.getAttackImage(), boss.swarm2.getGlyBoxes().get(r).x, boss.swarm2.getGlyBoxes().get(r).y, this);
            }
        }

        if (lasering == true) {
            if (boss.laser.getAttackImage() == boss.laser.getLaser()) {
                g2d.drawImage(boss.getBossImage(), boss.laser.getStartX() - 115, boss.laser.getStartY() - 20, this);
            } else {
                g2d.drawImage(boss.getBossLaser1(), boss.laser.getStartX() - 115, boss.laser.getStartY() - 20, this);
            }
        }

        //displays laser attack
        g2d.setColor(Color.GREEN);
        //jg2d.drawRect(boss.laser.getaHitBox().x, boss.laser.getaHitBox().y, boss.laser.getaHitBox().width, boss.laser.getaHitBox().height);
        g2d.drawImage(boss.laser.getAttackImage(), boss.laser.getStartX() - 37 * 2, boss.laser.getStartY() + 220, this);

        if (mario.isHit() == true) {
            g2d.drawImage(mario.getPlayerHurtSparks(), mario.getX() - 105, mario.getY() - 90, this);
        }

        //displays player 
        g2d.drawImage(mario.getPImage(), mario.getX() - 16 * 2,
                mario.getY() - 23 * 2, this);
        
        g2d.setColor(Color.white);
        /*
        g2d.drawRect(mario.getRect().x, mario.getRect().y, mario.getRect().width, mario.getRect().height);
        g2d.drawRect(mario.getRect2().x, mario.getRect2().y, mario.getRect2().width, mario.getRect2().height);
        g2d.drawRect(mario.getFootright().x, mario.getFootright().y, mario.getFootright().width, mario.getFootright().height);
        g2d.drawRect(mario.getFootleft().x, mario.getFootleft().y, mario.getFootleft().width, mario.getFootleft().height);
        g2d.drawRect(mario.getYellowRed().x, mario.getYellowRed().y, mario.getYellowRed().width, mario.getYellowRed().height);
        */
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        step();
    }

    private void step() {

        if (paused == false) {
            mario.update();
            updateTiles();
            attacks.bossAttacks();
            try {
                boss.attack();
            } catch (InterruptedException ex) {
                Logger.getLogger(JOBMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            JOBRetaliate.update();

            repaint(mario.getX() - 2, mario.getY() - 2,
                    mario.getWidth() + 2, mario.getHeight() + 2);
        }
    }

    //methods that load, update, and display the tiles
    private void loadMap(String filename) throws IOException {
        ArrayList lines = new ArrayList();
        int width = 0;
        int height = 0;

        BufferedReader reader = new BufferedReader(new FileReader(filename));
        while (true) {
            String line = reader.readLine();
            // no more lines to read
            if (line == null) {
                reader.close();
                break;
            }

            if (!line.startsWith("!")) {
                lines.add(line);
                width = Math.max(width, line.length());

            }
        }
        height = lines.size();

        for (int j = 0; j < 15; j++) {
            String line = (String) lines.get(j);
            for (int i = 0; i < width; i++) {

                if (i < line.length()) {
                    char ch = line.charAt(i);
                    JOBTile t = new JOBTile(i, j, Character.getNumericValue(ch));
                    tilearray.add(t);
                }

            }
        }

    }

    private void updateTiles() {
        for (int i = 0; i < tilearray.size(); i++) {
            JOBTile t = (JOBTile) tilearray.get(i);
            t.update();
        }
    }

    private void paintTiles(Graphics g) {
        for (int i = 0; i < tilearray.size(); i++) {
            JOBTile t = (JOBTile) tilearray.get(i);
            g.drawImage(t.getTileImage(), t.getTileX(), t.getTileY(), this);
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            mario.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            mario.keyPressed(e);
        }
    }

    @Override
    public void run() {

        long beforeTime, timeDiff, sleep;

        beforeTime = System.currentTimeMillis();

        while (true) {

            repaint();

            timeDiff = System.currentTimeMillis() - beforeTime;
            sleep = DELAY - timeDiff;

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {

                String msg = String.format("Thread interrupted: %s", e.getMessage());

                JOptionPane.showMessageDialog(this, msg, "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

            beforeTime = System.currentTimeMillis();
        }
    }

    public static JOBPlayer getPlayer() {
        return mario;
    }

    public static JOBAttacks getAttacks() {
        return attacks;
    }

    public static JOBBoss getBoss() {
        return boss;
    }

    public static void setPaused(boolean paused) {
        JOBMain.paused = paused;
    }

    public static boolean isPaused() {
        return paused;
    }
}
