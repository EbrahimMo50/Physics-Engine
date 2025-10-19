package Core.Collison.CollisionRules;

import Movables.Collidables.Collidable;

// Abstraction for the rules of collision in the system
@FunctionalInterface
public interface CollisionRule<A extends Collidable, B extends Collidable> {
    Boolean test(A a, B b);
}
