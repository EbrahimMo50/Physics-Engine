package GUI.Controls;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;

import Core.Engine;
import Core.Generator;
import GUI.Visuals.Panel;
import Movables.Circle;
import Utiliz.MovableTypes;
import Utiliz.Pair;
import Utiliz.Renderable;

/// mediator used to define system events for the controls to call and then the
/// resolve logic to be executed on the generator and any releated controls
/// needed for certain event to be dispachted while decoupled
public class ControlsResolver implements Renderable {
    private MovableTypes _movableType;
    private double _sizeScale = 1.0;
    private double _mass = 1.0;
    private double _elasticity = 1.0;

    // #region drag & launch
    
    private Pair<Integer, Integer> startingPosition = null; // used to render the direction of the arrow
    private final double LAUNCH_POWER = 10.0;
    private int renderCoolDown = 5;                         // 5 render ticks which is equal to 1/12 sec at 60 fps

    // #endregion drag n launch

    private Panel _panel;
    private Engine _engine;

    public ControlsResolver(Engine engine, Panel panel) {
        _movableType = MovableTypes.Circle; // default to circle
        _panel = panel;
        _engine = engine;
    }

    public void changeMovableType() {

    }

    public void setStartingPosition(Pair<Integer, Integer> startPos) {
        renderCoolDown = 5;
        startingPosition = startPos;
    }

    public void dragAndThrowMovable(Pair<Integer, Integer> endPos) {
        int x1 = startingPosition.x,
                x2 = endPos.x,
                y1 = startingPosition.y,
                y2 = endPos.y;

        double len = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));

        double vx = (x1 - x2) / len * LAUNCH_POWER;
        double vy = (y1 - y2) / len * LAUNCH_POWER;
        if (len == 0) {
            vx = 0;
            vy = 0;
        }

        switch (_movableType) {
            case MovableTypes.Circle:
                Circle movable = Generator.Circles.random();
                movable.setVelocityVector(vx, vy);
                movable.position = new Pair<>(Double.valueOf(endPos.x), Double.valueOf(endPos.y));
                _engine.addMovables(movable);
                break;
        
            default:
                break;
        }

        startingPosition = null;
    }

    public void render(Graphics2D g) {
        if (renderCoolDown > 0) {
            renderCoolDown--;
            return;
        }
        if (startingPosition != null) {
            // Get current mouse position relative to the panel
            Point screenPos = MouseInfo.getPointerInfo().getLocation();
            int x = screenPos.x - _panel.getLocationOnScreen().x;
            int y = screenPos.y - _panel.getLocationOnScreen().y;

            // Draw main line
            g.setColor(Color.GRAY);
            g.setStroke(new BasicStroke(2));
            g.drawLine(startingPosition.x, startingPosition.y, x, y); // the line

            // ---- Draw arrowhead pointing BACK toward start ----
            double phi = Math.toRadians(30); // arrowhead angle
            int barb = 12; // arrowhead length

            double dx = startingPosition.x - x, dy = startingPosition.y - y;
            double theta = Math.atan2(dy, dx); // note reversed order

            // Arrowhead base coordinates (at startingPosition direction)
            double x1 = startingPosition.x - barb * Math.cos(theta + phi);
            double y1 = startingPosition.y - barb * Math.sin(theta + phi);
            double x2 = startingPosition.x - barb * Math.cos(theta - phi);
            double y2 = startingPosition.y - barb * Math.sin(theta - phi);

            // Draw the arrowhead
            g.drawLine(startingPosition.x, startingPosition.y, (int) x1, (int) y1);
            g.drawLine(startingPosition.x, startingPosition.y, (int) x2, (int) y2);
        }
    }

}
