package GUI.Visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import Core.Engine;
import GUI.Controls.MouseControls;

// the panel will be listner for its actions to avoid over complicating things for now
public class Panel extends JPanel {

    private final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private final Dimension INTIAL_SIZE = new Dimension(500, 500);

    private Engine _engine;
    private MouseControls _mouseControls;

    public Panel(Engine engine, MouseControls mouseControls) {

        setPreferredSize(INTIAL_SIZE);
        addMouseListener(mouseControls);
        this._engine = engine;

        this.setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this._engine.render((Graphics2D) g);
    }
}
