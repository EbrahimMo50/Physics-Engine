package Core;

import Core.Collison.CollisionHandler;
import Core.Collison.CollisionRules.CollisionRules;
import Core.Collison.ResolveInstructions.ResolveInstructions;
import GUI.Controls.MouseControls;
import GUI.Visuals.Frame;
import GUI.Visuals.Panel;
import Movables.Circle;
import Movables.Collidables.Boundary;

/**the central class with all the intiallizations, dependicies and game loop*/
public class EngineBoot extends Thread{
    private Engine _engine;

    private Frame _frame;
    private Panel _panel;
    private MouseControls _mouseControls;
    private Generator _generator;

    public static final int UPS = 120;  // exposed as public for engine to use as relative calculation for affectors to be done by second
    public static final int FPS = 60;

    public EngineBoot(){
        _engine = new Engine();
        _mouseControls = new MouseControls(_engine);
        _panel = new Panel(_engine, _mouseControls);
        _generator = new Generator(_panel);
        _mouseControls.setGenerator(_generator);
        _frame = new Frame(_panel, this);

        populateHandler();          // call to register the rules and instructions in the handler
    }

    // Could be done through reflaction on Rules methods since each new added collidable will add +CountMovables to the rules and the registeration calls
    private static void populateHandler(){
        
        //#region rules
        CollisionHandler.register(Circle.class, Boundary.class, (Circle c, Boundary b) -> CollisionRules.checkCircleBoundary(c, b));
        CollisionHandler.register(Circle.class, Circle.class, (Circle c1, Circle c2) -> CollisionRules.checkCircleCircle(c1, c2));

        //#endregion rules

        //#region resolvers
        CollisionHandler.register(Circle.class, Boundary.class, (Circle c, Boundary b) -> ResolveInstructions.resolveCircleBoundary(c, b));
        CollisionHandler.register(Circle.class, Circle.class, (Circle c1, Circle c2) -> ResolveInstructions.resolveCircleCircle(c1, c2));

        //#endregion resolvers

    }

    @Override
    public void run() {
        double timePerFrame = 1000000000.0 / FPS;
        double timePerUpdate = 1000000000.0 / UPS;

        // unit of time measuring is nanosecond hence we use 1000000000.0

        int frames = 0, updates = 0;
        long lastCheck = System.currentTimeMillis();
        double deltaU = 0, deltaF = 0;
        long previousTime = System.nanoTime();

        while (true) {
            long currentTime = System.nanoTime();

            deltaU += (currentTime - previousTime) / timePerUpdate;
            deltaF += (currentTime - previousTime) / timePerFrame;
            previousTime = currentTime;

            if (deltaU >= 1) {
                _engine.update(_panel.getWidth(), _panel.getHeight());
                deltaU--;
                updates++;
            }

            if (deltaF >= 1) {
                _panel.repaint();
                deltaF--;
                frames++;
            }

            if (System.currentTimeMillis() - lastCheck >= 1000) {
                lastCheck = System.currentTimeMillis();
                System.out.println("FPS: " + frames + "| UPS: " + updates);
                frames = 0;
                updates = 0;
            }
            // fps displayer
        }
    }

    public void notifyFramePositionChange(int dx, int dy) {
        _engine.notifyFramePositionChange(dx, dy);
    }
}
