
import afk.bot.london.TankRobot;
import static afk.bot.london.TankRobot.MOVE_BACK;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomBot extends TankRobot
{

    private int AVOIDANCE = 50;

    public RandomBot()
    {
        super();
    }

    @Override
    public void init()
    {
        super.init();
        setName("Pampoen");
    }

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        if (getActionValue(MOVE_BACK) > 0)
        {
            clearActions();
            moveForward(AVOIDANCE);
        } else
        {
            clearActions();
            moveBackward(AVOIDANCE);
        }
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        clearActions();
        target(visibleBots.get(0), 0.6f);
    }

    @Override
    public void idle()
    {
        startTimer((int) (Math.random() * 100), new Runnable() {
            @Override
            public void run()
            {
                turnAntiClockwise((int) (Math.random() * 360));
            }
        });

        moveForward(10);
    }
}
