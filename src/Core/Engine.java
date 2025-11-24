package Core;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Core.Collison.CollisionHandler;
import Fun.Rainbow;
import Movables.Movable;
import Movables.Collidables.Boundary;
import Utiliz.Pair;
import Utiliz.Renderable;

public class Engine implements Renderable {
    // in the case of UPS set to 120 update per second which means if accelration
    // affecting
    // is set to (0, -10) each second the ball would move 120*10 downward on each
    // update

    private final double DRAG = 1 / EngineBoot.UPS;
    /** vector defination of gravity */
    private final Pair<Double, Double> GRAVITY = new Pair<>(0.0, -9.8 / EngineBoot.UPS);

    /**
     * factor to kill velocity if it reached velocity low enough to stop the
     * infinite bounce
     */
    private static final double VELOCITY_KILL_THRESHHOLD = 0.8; // lowest possible threshhold when gravity is -9.8/120
                                                                // and drag is equal to 1 is 0.006 in elastic
                                                                // collision
                                                                // case

    private List<Movable> _movables; // boundaries of wall and floor to be taken dynamically every UPS from the panel
    // or set constant and passed to panel?
    // second option is more fun tbf

    public Engine() {
        _movables = new ArrayList<Movable>();
        Rainbow effect = Rainbow.getInstance();
        effect.setMovables(_movables);
        effect.start();
    }

    public void addMovables(List<Movable> movables) {
        for (Movable movable : movables)
            addMovables(movable);
    }

    public void addMovables(Movable movable) {
        // check collision using collison handler
        this._movables.add(movable);
    }

    public void update(int limitX, int limitY) {
        for (int i = 0; i < _movables.size(); i++) {
            Movable m = _movables.get(i);
            Pair<Double, Double> velocityVector = m.getVelocityVector();

            // gravity force
            velocityVector.x -= GRAVITY.x;
            velocityVector.y -= GRAVITY.y;

            // quadratic drag force (stronger at high velocities)
            velocityVector.x -= velocityVector.x * Math.abs(velocityVector.x) * DRAG;
            velocityVector.y -= velocityVector.y * Math.abs(velocityVector.y) * DRAG;

            m.setVelocityVector(velocityVector.x, velocityVector.y);

            // collision checks and resolve

            Boundary currentBoundaryLimit = new Boundary(limitX, limitY);

            if (CollisionHandler.collides(m, currentBoundaryLimit)) {
                CollisionHandler.resolve(m, currentBoundaryLimit);
                _applyKillThreshHold(m);
            }

            for (int j = i + 1; j < _movables.size(); ++j) {
                Movable nextMovable = _movables.get(j);
                if (CollisionHandler.collides(m, nextMovable)) {
                    CollisionHandler.resolve(m, nextMovable);
                    _applyKillThreshHold(m, nextMovable);
                }
            }
            m.position.x += m.getVelocityVector().x;
            m.position.y += m.getVelocityVector().y;
        }
    }

    public void render(Graphics2D g) {
        for (Movable movable : _movables)
            movable.draw(g);
    }

    public void notifyFramePositionChange(int dx, int dy) {
        for (Movable movable : _movables) {
            movable.position.x -= dx;
            movable.position.y -= dy;
        }
    }

    /// kills any minimal velocity upon collision to prevent jittering
    private void _applyKillThreshHold(Movable... movables) {
        for (Movable m : movables) {
            if (Math.abs(m.getVelocityVector().y) < VELOCITY_KILL_THRESHHOLD)
                m.setVelocityVector(m.getVelocityVector().x, 0.0);

            if (Math.abs(m.getVelocityVector().x) < VELOCITY_KILL_THRESHHOLD)
                m.setVelocityVector(0.0, m.getVelocityVector().y);
        }
    }
}