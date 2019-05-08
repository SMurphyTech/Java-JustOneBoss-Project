package justoneboss;

public class JOBAttacksVaccuum extends JOBAttacks{
    String direction;
    
    public JOBAttacksVaccuum(String direction){
        setDirection(direction);
    }
    
    public void print(){
        System.out.println(direction);
    }
    
    public void update(){
        if ("right".equals(direction)) {
            mario.setX(mario.getX() + VSpeed);
        } else if ("left".equals(direction)) {
            mario.setX(mario.getX() - VSpeed);
        }
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
    }
}