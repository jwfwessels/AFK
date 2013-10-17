
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
public class SmallRandomBot extends TankRobot
{

    private int AVOIDANCE = 30;

    public SmallRandomBot()
    {
        super();
    }

    @Override
    public void init()
    {
        setType("smallTank");
        setName("Brat");
    }
    int timer;

    @Override
    public void start()
    {
        resetTimer();
        idle();
    }

    @Override
    public void hitObject()
    {
        if (getActionValue(MOVE_BACK) > 0)
        {
            moveForward(AVOIDANCE);
        } else
        {
            moveBackward(AVOIDANCE);
        }
        timer = 0;
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
        timer--;
        if (timer <= 0)
        {
            turnAntiClockwise((int) (Math.random() * 180));
            resetTimer();
        }

        moveForward(10);
    }

    private void resetTimer()
    {
        timer = (int) (Math.random() * 400);
    }
}