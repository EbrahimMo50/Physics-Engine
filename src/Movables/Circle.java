package Movables;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import Utiliz.Pair;

public class Circle extends Movable {

    public double radius;

    public Circle(double radius, Pair<Double, Double> pos, Pair<Double, Double> velocityVec, double mass, double terminalVelocity, double elasticity, Color color) {
        super(pos, velocityVec, mass, terminalVelocity, elasticity, color);
        this.radius = radius;
    }

    @Override
    public double getArea() {
        return Math.PI * Math.pow(radius, 2);
    }

    @Override
    public void draw(Graphics2D g) {
        g.setColor(color);
        AffineTransform old = g.getTransform();
        g.fillOval(
                (int) Math.round(position.x - radius),
                (int) Math.round(position.y - radius),
                (int) Math.round(radius * 2),
                (int) Math.round(radius * 2));
        g.setTransform(old);
    }
}