package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;

import Core.Engine;

// the panel will be listner for its actions to avoid over complicating things for now
public class Panel extends JPanel implements MouseListener {

    private final Color BACKGROUND_COLOR = new Color(255, 255, 255);
    private final Dimension INTIAL_SIZE = new Dimension(500, 500);

    private Engine _engine;

    public Panel(Engine engine) {

        setPreferredSize(INTIAL_SIZE);

        addMouseListener(this);
        this._engine = engine;

        this.setBackground(BACKGROUND_COLOR);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this._engine.render((Graphics2D) g);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println(this.getWidth() + this.getHeight());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
