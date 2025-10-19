package Movables;

import Utiliz.Pair;

import java.awt.Color;
import java.awt.Graphics2D;

import Movables.Collidables.Collidable;

// abstract class that is base to all movables
public abstract class Movable implements Collidable {
    protected Pair<Double, Double> velocityVector;
    public Pair<Double, Double> position;

    public double mass;
    protected double _terminalVelocity;
    protected double _invMass;
    protected double _elasticity;

    public Color color;

    // no edging on mass's values negatives and zeros will work... this shall be fun
    public Movable(Pair<Double, Double> pos, double mass, double terminalVelocity, double elasticity, Color color) {
        this.velocityVector = new Pair<Double, Double>(0.0, 0.0);
        this.position = pos;
        this.mass = mass;
        this._invMass = 1 / mass; // on mass equal zero _invMass will be infinity
        this._terminalVelocity = terminalVelocity;
        setElasticity(elasticity);
        this.color = color;
    }

    public void setElasticity(double value) {
        if (value > 1)
            value = 1;
        if (value < 0)
            value = 0;
        this._elasticity = value;
    }

    public double getElasticity() {
        return this._elasticity;
    }

    public void setVelocityVector(double vx, double vy) {
        double speed = Math.sqrt(vx * vx + vy * vy);

        if (speed > _terminalVelocity) {
            // normalize & clamp
            double scale = _terminalVelocity / speed;
            vx *= scale;
            vy *= scale;
        }

        velocityVector = new Pair<>(vx, vy);
    }

    public double getSpeed() {
        return Math.sqrt(velocityVector.x * velocityVector.x + velocityVector.y *velocityVector.y);
    }

    public Pair<Double, Double> getVelocityVector(){
        return new Pair<>(this.velocityVector.x, this.velocityVector.y);    // shallow copy to prevent tampring of proprties
    }

    public double getInvMass() {
        return _invMass;
    }

    public abstract double getArea();

    public abstract void draw(Graphics2D g);
}