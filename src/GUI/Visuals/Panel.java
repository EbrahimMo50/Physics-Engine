package GUI.Visuals;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import Utiliz.Renderable;

// the panel will be listner for its actions to avoid over complicating things for now
public class Panel extends JPanel {

    private final Color BACKGROUND_COLOR = Color.WHITE;
    private final Dimension INTIAL_SIZE = new Dimension(500, 500);

    private final List<Renderable> RENDERABLES = new ArrayList<>();

    public Panel() {
        setPreferredSize(INTIAL_SIZE);
        this.setBackground(BACKGROUND_COLOR);
    }

    public void addRenderable(Renderable... renderables) {
        for (Renderable renderable : renderables) {
            this.RENDERABLES.add(renderable);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Renderable renderable : RENDERABLES) {
            renderable.render((Graphics2D) g);
        }
    }
}
