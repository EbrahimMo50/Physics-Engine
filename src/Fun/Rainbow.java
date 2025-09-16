package Fun;

import java.util.ArrayList;
import java.util.List;
import Movables.Movable;
import java.awt.Color;

// takes movables and rainbow effects them :)
public class Rainbow extends Thread {

    private List<Movable> _movables;
    private static Rainbow _instance;
    
    /**
     *  controls transition from color to another the lower the smoother
     *  ranged between 0-1 */
    private final double STEP_SMOOTHNESS = 0.1;
    /**
     * controls delay between each update
     * measured in millie second */
    private final long DELAY_CONSTANT = 100;
    private final Color[] GRADIANT = {
            new Color(255, 0, 0),
            new Color(255, 127, 0),
            new Color(255, 255, 0),
            new Color(0, 255, 0),
            new Color(0, 0, 255),
            new Color(75, 0, 130),
            new Color(143, 0, 255)
    };

    private Rainbow() {
        this._movables = new ArrayList<>();
    }

    public static synchronized Rainbow getInstance() {
        if (_instance == null) {
            _instance = new Rainbow();
        }
        return _instance;
    }

    // taken by reference affecting all to be added movables
    public void setMovables(List<Movable> movables){
        this._movables = movables;
    }

    // taking only the movables on the starting call of the method leaving any to be added for manual additional calls
    public void addMovables(List<Movable> newMovables) {
        this._movables.addAll(newMovables);
    }

    public void addMovables(Movable newMovable){
        this._movables.add(newMovable);
    }

    @Override
    public void run() {
        double percentage = 0.0;
        int colorIndex = 0;
        while (!Thread.currentThread().isInterrupted()) {

            if (!_movables.isEmpty()) {
                Color startColor = GRADIANT[colorIndex];
                Color endColor = GRADIANT[(colorIndex + 1)% this.GRADIANT.length];

                int r = (int) (startColor.getRed() + (endColor.getRed() - startColor.getRed()) * percentage);
                int g = (int) (startColor.getGreen() + (endColor.getGreen() - startColor.getGreen()) * percentage);
                int b = (int) (startColor.getBlue() + (endColor.getBlue() - startColor.getBlue()) * percentage);

                Color newColor = new Color(r, g, b);

                for (Movable movable : _movables) {
                    movable.color = newColor;
                }

                percentage += STEP_SMOOTHNESS;

                if (percentage >= 1.0) {
                    percentage = 0.0;
                    colorIndex = (colorIndex + 1) % this.GRADIANT.length;
                }
            }
            try {
                Thread.sleep(DELAY_CONSTANT);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}