package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Core.Engine;
import Core.Generator;

// for more percise controls in the future a builder could be used next to the scroll to 
// control the size and keyboard can be listned for to toggle effects

public class MouseControls implements MouseListener {

    private Engine _engine;
    private Generator _generator;

    public MouseControls(Engine engine){
        _engine = engine;
    }

    public void setGenerator(Generator generator){
        this._generator = generator;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        _engine.addMovables(_generator.randomCircle(e.getX(),e.getY()));
    }

    @Override
    public void mousePressed(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }
    
}
