
import afk.bot.london.TankRobot;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class SampleBot extends TankRobot {

    boolean running = true;
    int move = 0;
    float turn = 0;
    int shoot = 0;
    int turns = 0;

    public SampleBot() {
        super();
    }

    @Override
    public void run() {
        if (running) {
            turnAntiClockwise();
            turn++;
            if (turn == 60) {
                System.out.println("T 60");
                turn = 0;
            }
        }
    }
}