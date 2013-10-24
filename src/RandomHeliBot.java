
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
    private int AVOIDANCE = 50;

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

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        clearActions();
        cancelAllTimers();
        if (getActionValue(MOVE_BACK) > 0)
        {
            moveForward(AVOIDANCE);
        } else
        {
            moveBackward(AVOIDANCE);
        }
        startTimer(AVOIDANCE / 3, new Runnable()
        {
            @Override
            public void run()
            {
                if (Math.random() > 0.5)
                {
                    turnClockwise(180);
                } else
                {
                    turnAntiClockwise(180);
                }
            }
        });
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        clearActions();
        cancelAllTimers();
        target(visibleBots.get(0), GIVE);
    }

    @Override
    public void idle()
    {
        startTimer((int) (Math.random() * 300), new Runnable() {

            @Override
            public void run()
            {
                int amount = (int) (Math.random() * 180);
                moveForward(amount);
                if (Math.random() > 0.5)
                {
                    turnClockwise(amount);
                } else
                {
                    turnAntiClockwise(amount);
                }
            }
        });

        moveForward((int) (Math.random() * 300));
    }
}
