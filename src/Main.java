import Core.EngineBoot;

public class Main {
    public static void main(String[] args) {
        new EngineBoot().start();
    }
}

// COLLISION RULES
// elasticity Coff = min(objectA.e, objectB.e)
// relativeVelocity = Va - Vb
// collision impulse = −(1+e) * ​relativeVelocity​*n​ / invMassA​+invMassB

// impluse application
// Va` = Va​−j*invMassA​*n where n is the collision normal
// Vb` = Vb−j*invMassA​*n
// n = (position(a)-position(b))/(||position(a) - position(b)||)

// collision with static vertical/horizontal gives position vector of (+-1, 0)
// or (0, +-1)
// Skewed walls having inclination of theta give position of (cos(theta),
// sin(theta))
