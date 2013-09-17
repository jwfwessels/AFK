
import afk.bot.london.TankRobot;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class SampleBot2 extends TankRobot {

    boolean running = true;
    int move = 0;
    float turn = 45;
    int shoot = 0;
    int turns = 0;

    public SampleBot2() {
        super();
    }

    @Override
    public void run() {
        if (running) {
            {
//                if (move < 600) {
                    moveForward();
            move++;
            if (move == 120) {
                System.out.println("M 120");
                move = 0;
            }
            }

        }
    }
}