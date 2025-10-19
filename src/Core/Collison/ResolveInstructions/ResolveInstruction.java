package Core.Collison.ResolveInstructions;

import Movables.Collidables.Collidable;

// Abstraction to the Resolving instructions
@FunctionalInterface
public interface ResolveInstruction<A extends Collidable, B extends Collidable> {
    public void resolve(A a, B b);
}