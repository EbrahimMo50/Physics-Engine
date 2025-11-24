# Physics Engine

This is a small Java physics engine project (single-process desktop app) used as a learning playground for collision detection, resolution and a simple game loop. The codebase is intentionally small and modular so new collidables, rules and renderables can be added easily.

## High-level Architecture

Overview (top → bottom):

- **Launcher**: `Main` — starts the application by launching `EngineBoot`.
- **EngineBoot**: central bootstrapping and wiring class. It creates the `Engine`, the `Panel` (render surface), control handlers and registers collision rules and resolve instructions with the `CollisionHandler`.
- **Engine**: the simulation core. Runs the fixed-timestep update loop (UPS) to update physics and applies rendering requests to the `Panel` at a separate FPS rate.
- **Collision Subsystem**: decoupled registry that maps pairs of `Collidable` types to a `CollisionRule` (test) and a `ResolveInstruction` (response). This allows adding new collidable types without changing the engine core.
- **Movables**: domain objects (e.g. `Circle`) that hold position, velocity and draw logic. They implement `Renderable` / `Movable` interfaces and are owned/updated by the `Engine`.
- **GUI**: `Frame` (window) and `Panel` (drawing surface). The `Panel` holds `Renderable` instances and paints them when `repaint()` is requested by the `EngineBoot` loop.

Movables (Circle, Boundary, ...) ⇄ Engine (update & collision checks) ⇄ CollisionHandler

## Important Packages & Files

- `src/Main.java` — program entry point.
- `src/Core/EngineBoot.java` — creates and wires major components, registers collision rules and resolve instructions, and starts the engine thread.
- `src/Core/Engine.java` — simulation loop and main update logic (gravity, drag, collision checks, move application).
- `src/Core/Collison/CollisionHandler.java` — central registry for collision rules and resolvers. Keys are a lightweight `ClassPair` record.
- `src/Core/Collison/CollisionRules/*` — pure predicate implementations (e.g. `checkCircleCircle`, `checkCircleBoundary`).
- `src/Core/Collison/ResolveInstructions/*` — code that performs position corrections and velocity updates after a detected collision.
- `src/GUI/Visuals/Frame.java` & `Panel.java` — window and drawing surface. `EngineBoot` wires them together.
- `src/Movables/` — domain objects (`Movable`, `Circle`, `Collidables/Boundary`, ...).
- `src/Utiliz/` — small helper types: `Pair`, `Renderable`, `MovableTypes`, etc.

## How components communicate

- `EngineBoot` is the composition root: it instantiates the `Engine`, `Panel`, control objects and registers colliders and resolvers with `CollisionHandler`.
- The `Engine` holds a list of `Movable` objects and performs the following each update step:
	- apply gravity and drag
	- call `CollisionHandler.collides(...)` to test collisions with the current boundary and other movables
	- call `CollisionHandler.resolve(...)` to execute the matching resolve instruction if a collision is detected
	- update each movable's `position` by its current velocity
- The `Panel` is repainted at the FPS rate; it simply calls the `render` method of registered `Renderable` objects (the `Engine` is registered as a `Renderable`).
- When the application window moves, `Frame` notifies `EngineBoot`, which forwards the delta to the `Engine` via `notifyFramePositionChange(dx,dy)`. The `Engine` adjusts all movables coordinates so the world stays visually stable.

## Collision registration (Algorithm registery pattern)

`EngineBoot.populateHandler()` contains the registration pattern used by the engine:

- Register a rule: `CollisionHandler.register(Circle.class, Boundary.class, (c,b) -> CollisionRules.checkCircleBoundary(c,b));`
- Register a resolver: `CollisionHandler.register(Circle.class, Boundary.class, (c,b) -> ResolveInstructions.resolveCircleBoundary(c,b));`

This decoupled approach makes it easy to add new collidable types: implement the test and resolver and register them in `EngineBoot`.
## Development notes & common gotchas

- Fixed timestep: the engine uses a fixed UPS (updates per second) and a separate FPS. This is good for deterministic physics and prevents variable frame-rate influence on the simulation.
- Jitter on collisions commonly comes from tiny residual velocities or incomplete position correction after a collision. The project addresses this by:
	- killing very small velocity components in `Engine._applyKillThreshHold(...)` after a collision
	- moving objects out of penetration in resolver methods (see `ResolveInstructions.resolveCircleBoundary`)
- If you still see jitter:
	- check that the resolver always sets position to a non-penetrating value (exact boundary), not just flips velocity
	- make sure kill thresholds are tuned to your UPS and gravity values (smaller thresholds if UPS is high)
	- avoid applying position corrections that depend on frame size without using the same values in collision checks
- Frame movement: the `Frame` notifies the engine with deltas — avoid re-centering the frame inside that callback or you may create a feedback loop.

## How to add a new collidable type

1. Add the collidable in `src/Movables/Collidables` and implement `Collidable`.
2. Add a `CollisionRule` that returns true when two objects collide.
3. Add a `ResolveInstruction` that fixes penetration and updates velocities.
4. Register both in `EngineBoot.populateHandler()`.

## Next steps & ideas

- Add unit tests for collision rule edge cases (grazing/tangent collisions).
- Add a simple visualization overlay showing penetration vectors and normals to debug resolution issues.
- Consider using vector types for positions/velocities to make math clearer and less error-prone.
---
<img width="752" height="529" alt="image" src="https://github.com/user-attachments/assets/d5ef81bd-df71-41a9-b245-21653cd62be4" />
