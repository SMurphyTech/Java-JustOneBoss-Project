package justoneboss;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class JOBStartingClass extends JFrame {  
    
    public JOBStartingClass() {

        initUI();
    }

    private void initUI() {

        add(new JOBMain());       

        setTitle("JustOneBoss Alpha");
        setSize(800 * 2, 480 * 2);
        
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }    
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            JOBStartingClass ex = new JOBStartingClass();
            ex.setVisible(true);
        });
    }
}
