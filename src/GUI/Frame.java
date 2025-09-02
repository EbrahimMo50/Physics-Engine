package GUI;

import javax.swing.JFrame;

public class Frame {
    public Frame (Panel panel){
        JFrame jframe = new JFrame("Physics Engine");
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(panel);
        jframe.setResizable(true);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
        jframe.setFocusable(true);
    }
}
