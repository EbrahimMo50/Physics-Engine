package GUI.Controls;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import Utiliz.Pair;

// for more percise controls in the future a builder could be used next to the scroll to 
// control the size and keyboard can be listned for to toggle effects

public class MouseControls implements MouseListener {

    private ControlsResolver _ControlsResolver;

    public MouseControls(ControlsResolver resolver) {
        _ControlsResolver = resolver;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == 1)
            _ControlsResolver.setStartingPosition(new Pair<>(e.getX(), e.getY()));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == 1)
            _ControlsResolver.dragAndThrowMovable(new Pair<>(e.getX(), e.getY()));
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

}
