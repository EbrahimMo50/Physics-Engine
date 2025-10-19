package Core.Collison.ResolveInstructions;

import Movables.Circle;
import Movables.Collidables.Boundary;

public class ResolveInstructions {
    public static void resolveCircleBoundary(Circle c, Boundary b) {

        double x = c.position.x;
        double y = c.position.y;
        double r = c.radius;

        double vx = c.getVelocityVector().x;
        double vy = c.getVelocityVector().y;

        // overlaps (positive means penetration past boundary)
        double leftOverlap = -(x - r);
        double rightOverlap = (x + r) - b.width;
        double topOverlap = -(y - r);
        double bottomOverlap = (y + r) - b.height;

        // find the largest penetration
        double maxOverlap = -1;
        String side = null;

        if (leftOverlap > maxOverlap) {
            maxOverlap = leftOverlap;
            side = "LEFT";
        }
        if (rightOverlap > maxOverlap) {
            maxOverlap = rightOverlap;
            side = "RIGHT";
        }
        if (topOverlap > maxOverlap) {
            maxOverlap = topOverlap;
            side = "TOP";
        }
        if (bottomOverlap > maxOverlap) {
            maxOverlap = bottomOverlap;
            side = "BOTTOM";
        }

        // resolve by pushing out and flipping velocity
        switch (side) {
            case "LEFT":
                c.position.x = r + 1;// +1 to prevent clipping it to the left after colliding
                vx = -vx * c.getElasticity();
                break;
            case "RIGHT":
                c.position.x = b.width - r;
                vx = -vx * c.getElasticity();
                break;
            case "TOP":
                c.position.y = r + 1; // +1 to prevent clipping it to the top after colliding
                vy = -vy * c.getElasticity();
                break;
            case "BOTTOM":
                c.position.y = b.height - r;
                vy = -vy * c.getElasticity();
                break;
        }

        c.setVelocityVector(vx, vy);
    }

    public static void resolveCircleCircle(Circle c1, Circle c2) {
        Double dx = c1.position.x - c2.position.x, dy = c1.position.y - c2.position.y;

        Double distance = Math.sqrt(dx * dx + dy * dy);
        Double normalX = dx / distance;
        Double normalY = dy / distance;

        // #region circle overlap handling
        double overlap = (c1.radius + c2.radius) - distance;

        if (overlap > 0) {
            double percent = 1;
            double correction = (overlap / (c1.getInvMass() + c2.getInvMass())) * percent;

            // Apply correction in opposite directions
            c1.position.x += correction * normalX * c1.getInvMass(); // push inverse to the normal from c2 to c1
            c1.position.y += correction * normalY * c1.getInvMass();

            c2.position.x -= correction * normalX * c2.getInvMass(); // push in the direction of the normal
            c2.position.y -= correction * normalY * c2.getInvMass();
        }
        // #endregion overlap handling

        // recalulating after overlap fix to get the new normal after seperation
        dx = c1.position.x - c2.position.x;
        dy = c1.position.y - c2.position.y;
        distance = Math.sqrt(dx * dx + dy * dy);
        normalX = dx / distance;
        normalY = dy / distance;

        Double releativeVelocityX = c1.getVelocityVector().x - c2.getVelocityVector().x;
        Double releativeVelocityY = c1.getVelocityVector().y - c2.getVelocityVector().y;

        Double minimumElasticity = Math.min(c1.getElasticity(), c2.getElasticity());

        Double releativeVelocity = releativeVelocityX * normalX + releativeVelocityY * normalY;

        // If circles are moving apart, skip
        if (releativeVelocity > 0)
            return;
        Double impulse = -(((1 + minimumElasticity) * releativeVelocity) / (c1.getInvMass() + c2.getInvMass()));

        c1.setVelocityVector(
                c1.getVelocityVector().x + (impulse * normalX) * c1.getInvMass(),
                c1.getVelocityVector().y + (impulse * normalY) * c1.getInvMass());

        c2.setVelocityVector(
                c2.getVelocityVector().x - (impulse * normalX) * c2.getInvMass(),
                c2.getVelocityVector().y - (impulse * normalY) * c2.getInvMass());
    }

}
