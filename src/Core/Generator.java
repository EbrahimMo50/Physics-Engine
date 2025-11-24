package Core;

import java.awt.Color;
import java.util.Random;

import Movables.Circle;
import Utiliz.Pair;

public class Generator {
    private static final double LIMIT_MASS = 100.0;
    private static final double LIMIT_VELOCITY = 500.0;

    public class Circles {
        private static final double LIMIT_RADIUS = 40.0;

        public static Circle random() {
            return _generateRandomCircle();
        }

        public static Circle random(int x, int y) {
            Circle circle = _generateRandomCircle();
            circle.position = new Pair<>((double) x, (double) y);
            return circle;
        }

        private static Circle _generateRandomCircle() {
            Random random = new Random();

            double radius = (random.nextDouble() * LIMIT_RADIUS) + 1;
            Pair<Double, Double> position = new Pair<>(0.0, 0.0);
            double mass = (random.nextDouble() * LIMIT_MASS) + 0.001;
            double terminalVelocity = (random.nextDouble() * LIMIT_VELOCITY) + 0.001;
            double elasticity = 1;
            Color color = new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));

            return new Circle(radius, position, new Pair<>(0.0, 0.0), mass, terminalVelocity, elasticity, color);
        }
    }

}
