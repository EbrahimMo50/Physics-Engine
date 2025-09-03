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

    private final double DRAG = 0.1; // range 0-1, could use quadratic drag formula for more realistic drag
    /** factor of gravity */
    private final Pair<Double, Double> GRAVITY = new Pair<>(0.0 / EngineBoot.UPS, -9.8 / EngineBoot.UPS);

    private Circle dummy = new Circle(5, new Pair<>(50, 50), 10, 100.0, 0.8, new Color(255, 0, 0));
    // boundaries of wall and floor to be taken dynamically every UPS from the panel
    // or set constant and passed to panel?
    // second option is more fun tbf

    public Engine() {
        List<Movable> movables = new ArrayList<Movable>();
        movables.add(dummy);
        Rainbow effect = Rainbow.getInstance();
        effect.addMovables(movables);
        effect.start();
    }

    public void update() {
        Pair<Double, Double> velocityVector = dummy.getVelocityVector();

        // gravity force
        velocityVector.x -= GRAVITY.x;
        velocityVector.y -= GRAVITY.y;

        // drag force
        velocityVector.x -= velocityVector.x * DRAG;
        velocityVector.y -= velocityVector.y * DRAG;
        System.out.println(velocityVector.y);

        dummy.setVelocityVector(velocityVector.x, velocityVector.y);
        dummy.position.x += (int) Math.floor(velocityVector.x);
        dummy.position.y += (int) Math.floor(velocityVector.y);

        if (dummy.position.y > 500 + dummy.radius) {
            dummy.setVelocityVector(velocityVector.x, (-velocityVector.y) * dummy.getElasticity());
            dummy.position.y = 500 - (int) dummy.radius;
        }

    }

    public void render(Graphics2D g) {
        dummy.draw(g);
    }
}