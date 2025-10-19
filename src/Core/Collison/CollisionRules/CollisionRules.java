package Core.Collison.CollisionRules;

import Movables.Circle;
import Movables.Collidables.Boundary;

public class CollisionRules {
    public static boolean checkCircleCircle(Circle c1, Circle c2){
        double c1x = c1.position.x, c2x = c2.position.x,
               c1y = c1.position.y, c2y = c2.position.y;
        
        double distance = Math.sqrt(Math.pow(c1x-c2x, 2) + Math.pow(c1y - c2y, 2));

        return c1.radius + c2.radius >= distance;
    }

    public static boolean checkCircleBoundary(Circle c, Boundary b){
        if(c.position.x + c.radius > b.width)
            return true;
        if(c.position.x - c.radius <= 0)
            return true;
        if(c.position.y + c.radius > b.height)
            return true;
        if(c.position.y - c.radius <= 0)
            return true;
        return false;
    }
}
