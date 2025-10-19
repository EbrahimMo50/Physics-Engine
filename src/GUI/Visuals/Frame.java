package GUI.Visuals;

import javax.swing.JFrame;
import java.awt.event.*;

import Core.EngineBoot;

public class Frame {
    
    private int _lastX, _lastY;

    public Frame (Panel panel, EngineBoot engineBoot){
        JFrame jframe = new JFrame("Physics Engine");

        jframe.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentMoved(ComponentEvent e) {
                int newX = jframe.getX();
                int newY = jframe.getY();

                int dx = newX - _lastX;
                int dy = newY - _lastY;

                if (dx != 0 || dy != 0) {
                    engineBoot.notifyFramePositionChange(dx, dy);
                    _lastX = newX;
                    _lastY = newY;
                }
            }
        });
        
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.add(panel);
        jframe.setResizable(true);
        jframe.pack();
        jframe.setVisible(true);
        jframe.setLocationRelativeTo(null);
        jframe.setFocusable(true);
    }
}
