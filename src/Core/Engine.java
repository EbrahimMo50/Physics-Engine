package Core;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import Fun.Rainbow;
import Movables.Circle;
import Movables.Movable;
import Utiliz.Pair;

public class Engine {
    // in the case of UPS set to 120 update per second which means if accelration
    // affecting
    // is set to (0, -10) each second the ball would move 120*10 downward on each
    // update

    private final double DRAG = 1 / EngineBoot.UPS;
    /** vector defination of gravity */
    private final Pair<Double, Double> GRAVITY = new Pair<>(0.0, -9.8 / EngineBoot.UPS);
    /** factor to kill velocity if it reached velocity low enough to stop the infinite bounce */
    private final double VELOCITY_KILL_THRESHHOLD = 0.1;

    private List<Movable> _movables; // boundaries of wall and floor to be taken dynamically every UPS from the panel
    // or set constant and passed to panel?
    // second option is more fun tbf

    public Engine() {
        Circle dummy = new Circle(5, new Pair<>(50.0, 50.0), 10, 100.0, 0.5, new Color(255, 0, 0));

        _movables = new ArrayList<Movable>();
        _movables.add(dummy);
        Rainbow effect = Rainbow.getInstance();
        effect.addMovables(_movables);
        effect.start();
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

            m.position.x += velocityVector.x;
            m.position.y += velocityVector.y;

            if (m.position.y >= limitY - 5.0) {
                velocityVector.y = -velocityVector.y * m.getElasticity();
                m.position.y = (double)limitY - 5.0;

                // snap to rest if very small velocity
                if (Math.abs(velocityVector.y) < VELOCITY_KILL_THRESHHOLD) {
                    velocityVector.y = 0.0;
                }
            }
            m.setVelocityVector(velocityVector.x, velocityVector.y);
        }
    }

    public void render(Graphics2D g) {
        for (Movable movable : _movables)
            movable.draw(g);
    }
}