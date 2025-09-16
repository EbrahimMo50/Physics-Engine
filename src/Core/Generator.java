package Core;

import java.awt.Color;
import java.util.Random;

import GUI.Panel;
import Movables.Circle;
import Utiliz.Pair;

public class Generator {

    private final double LIMIT_RADIUS = 75.0;
    private final double LIMIT_MASS = 1000.0;
    private final double LIMIT_VELOCITY = 500.0;

    private Panel _panel;

    public Generator(Panel panel){
        this._panel = panel;
    }

    public Circle randomCircle(){
        return _generateRandomCircle();
    }

    public Circle randomCircle(int x, int y){
        Circle circle = _generateRandomCircle();
        circle.position = new Pair<>((double)x, (double)y);
        return circle;
    }

    private Circle _generateRandomCircle(){
        Random random = new Random();

        double radius = (random.nextDouble() * LIMIT_RADIUS) + 0.001;
        Pair<Double, Double> position = new Pair<>(random.nextDouble() * (double) this._panel.getX(), random.nextDouble() * (double) this._panel.getY());
        double mass = (random.nextDouble() * LIMIT_MASS) + 0.001;
        double terminalVelocity = (random.nextDouble() * LIMIT_VELOCITY) + 0.001;
        double elasticity = random.nextDouble();
        Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

        return new Circle(radius, position, mass, terminalVelocity, elasticity, color);
    }
}
