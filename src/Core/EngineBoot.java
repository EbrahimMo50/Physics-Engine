package Core;

import GUI.Frame;
import GUI.MouseControls;
import GUI.Panel;

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
        _frame = new Frame(_panel);
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
}
