package justoneboss;

import java.awt.Rectangle;
import java.util.ArrayList;

public class JOBAttacksSwarm extends JOBAttacks {

    String orient;
    int holeNo, glyX;
    public ArrayList holes = new ArrayList();
    public ArrayList<Rectangle> glyBoxes = new ArrayList<Rectangle>();
    public ArrayList xPos = new ArrayList();
    Rectangle glyBox;
    Rectangle box1 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box2 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box3 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box4 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box5 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box6 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box7 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box8 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box9 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box10 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box11 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box12 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box13 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box14 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box15 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box16 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box17 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box18 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box19 = new Rectangle(0, startY, 32 * 2, 32 * 2);
    Rectangle box20 = new Rectangle(0, startY, 32 * 2, 32 * 2);

    public JOBAttacksSwarm(String orient, int holeNo) {
        setOrient(orient);
        setHoleNo(holeNo);
        glyBoxes.add(box1);
        glyBoxes.add(box2);
        glyBoxes.add(box3);
        glyBoxes.add(box4);
        glyBoxes.add(box5);
        glyBoxes.add(box6);
        glyBoxes.add(box7);
        glyBoxes.add(box8);
        glyBoxes.add(box9);
        glyBoxes.add(box10);
        glyBoxes.add(box11);
        glyBoxes.add(box12);
        glyBoxes.add(box13);
        glyBoxes.add(box14);
        glyBoxes.add(box15);
        glyBoxes.add(box16);
        glyBoxes.add(box17);       
        glyBoxes.add(box18);       
        glyBoxes.add(box19);       
        glyBoxes.add(box20);    
    }

    public void print() {
        System.out.println(holes);
    }

    //tiles: 19 horiz, 7 vertic
    public void update() {
        currentAttack = "Swarm";
        attackImage = gly;
        
        if (startY > 500 * 2) {
            moveY = 0;
        } else {
            moveY = 6;
        }

        startY = moveY + startY;

        for (int i = 0; i <= holeNo; i++) {

            if (holes.size() > holeNo) {

            } else {
                if (orient.equals("Hdown")) {
                    holes.add(rand.nextInt(19) + 1);
                }
            }
        }
        for (int p = 0; p < 20; p++) {
            if (holes.contains(p)) {
                
            } else {
                glyX = 95 * 2 + p * 64 - 32 * 2;               
                glyBoxes.get(p).setRect(glyX, startY, 32 * 2, 32 * 2);
            }
        }
    }
    
    public void nope(){
        startY = -50;
    }

    public Rectangle getGlyBox() {
        return glyBox;
    }

    public void setOrient(String orient) {
        this.orient = orient;
    }

    public void setHoleNo(int holeNo) {
        this.holeNo = holeNo;
    }
    
    public ArrayList<Rectangle> getGlyBoxes() {
        return glyBoxes;
    }

}
