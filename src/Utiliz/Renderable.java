package Utiliz;

import java.awt.Graphics2D;

/// represnation for any component that has any thing that has to be drawn on
/// the screen, could use a rename
@FunctionalInterface
public interface Renderable {
    public void render(Graphics2D g);
}
