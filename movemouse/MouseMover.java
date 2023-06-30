import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MouseMover {
    public static final int FIVE_SECONDS = 5000;
    private static final Logger logger = Logger.getLogger(MouseMover.class.getName());

    public static void main(String... args) throws Exception {
        Robot robot = new Robot();
        while (true) {
            Point pObj = MouseInfo.getPointerInfo().getLocation();
            robot.mouseMove(pObj.x + 1, pObj.y + 1);
            robot.mouseMove(pObj.x - 1, pObj.y - 1);
            pObj = MouseInfo.getPointerInfo().getLocation();
            logger.log(Level.INFO, pObj.toString() + "x>>" + pObj.x + "  y>>" + pObj.y);
            //robot.mouseMove(random.nextInt(MAX_X), random.nextInt(MAX_Y));
            Thread.sleep(FIVE_SECONDS);
        }
    }
}