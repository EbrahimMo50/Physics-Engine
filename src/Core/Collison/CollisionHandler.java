package Core.Collison;

import java.util.HashMap;
import java.util.Map;

import Core.Collison.CollisionRules.CollisionRule;
import Core.Collison.ResolveInstructions.ResolveInstruction;
import Movables.Collidables.Collidable;

public class CollisionHandler {
    private static final Map<ClassPair, CollisionRule<?, ?>> collisionRules = new HashMap<>();
    private static final Map<ClassPair, ResolveInstruction<?, ?>> resolveInstructions = new HashMap<>();

    public static <A extends Collidable, B extends Collidable> void register(Class<A> a, Class<B> b, CollisionRule<A, B> rule) {
        collisionRules.put(new ClassPair(a, b), rule);
    }

    public static <A extends Collidable, B extends Collidable> void register(Class<A> a, Class<B> b, ResolveInstruction<A, B> rule) {
        resolveInstructions.put(new ClassPair(a, b), rule);
    }

    public static boolean collides(Collidable a, Collidable b) {
        CollisionRule<Collidable, Collidable> rule = (CollisionRule<Collidable, Collidable>) collisionRules.get(new ClassPair(a.getClass(), b.getClass()));
        return rule != null && rule.test(a, b);
    }

    public static void resolve(Collidable a, Collidable b){
        ResolveInstruction<Collidable, Collidable> instruction = (ResolveInstruction<Collidable, Collidable>) resolveInstructions.get(new ClassPair(a.getClass(), b.getClass()));
        if(instruction != null)
            instruction.resolve(a, b);
    }

    // Immutation type to pair up each collidable to make a unique entry for the map's keys nothing more
    private record ClassPair(Class<?> a, Class<?> b) { }
}
