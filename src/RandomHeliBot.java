
import afk.bot.london.HeliRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomHeliBot extends HeliRobot
{

    private float GIVE = 1.4f;
    private int AVOIDANCE = 10;

    public RandomHeliBot()
    {
        super();
    }

    @Override
    public void init()
    {
        super.init();
        setName("Whirlybird");
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
        if (Math.random() > 0.5)
        {
            if (getActionValue(MOVE_BACK) > 0)
            {
                moveForward(AVOIDANCE);
            } else
            {
                moveBackward(AVOIDANCE);
            }
        }
        else
        {
            clearActions();
            if (Math.random()>0.5)
            {
                strafeLeft(AVOIDANCE);
            } else
            {
                strafeRight(AVOIDANCE);
            }
        }
        timer = 0;
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        clearActions();
        target(visibleBots.get(0), GIVE);
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
        timer = (int) (Math.random() * 300);
    }
}
