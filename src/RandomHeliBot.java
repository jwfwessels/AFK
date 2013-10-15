
import afk.bot.london.HeliRobot;
import afk.bot.london.VisibleBot;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomHeliBot extends HeliRobot
{

    private final float GIVE = 1.4f;
    private int movement = 0;
    private int rotation = 0;
    private boolean turning = true;
    private int retaliating = 0;

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
    public void run()
    {
        if (events.hitWall)
        {
            turning = false;
            movement = 100;
            rotation = 45;
            if (retaliating != 0)
            {
                retaliating = -retaliating;
            } else
            {
                retaliating = -1;
            }
        }
        if (!events.visibleBots.isEmpty())
        {
            VisibleBot visible = events.visibleBots.get(0);
            target(visible, GIVE);
        } else if (retaliating != 0)
        {
            retaliate();
        } else if (turning)
        {
            turn();
        } else
        {
            move();
        }
    }

    private void turn()
    {
        if (rotation > 0)
        {
            turnAntiClockwise();
            rotation--;
        } else
        {
            rotation = (int) (Math.random() * 360);
            turning = false;
        }
    }

    private void move()
    {
        if (movement > 0)
        {
            moveForward();
            movement--;
        } else
        {
            movement = (int) (Math.random() * 800);
            turning = true;
        }
    }

    private void retaliate()
    {
        if (turning)
        {
            if (rotation > 0)
            {
                if (retaliating == 1)
                {
                    turnClockwise();
                } else
                {
                    turnAntiClockwise();
                }
                rotation--;
            } else
            {
                retaliating = 0;
                turning = !turning;
            }
        } else
        {
            if (movement > 0)
            {
                if (retaliating == 1)
                {
                    moveForward();
                } else
                {
                    moveBackwards();
                }
                movement--;
            } else
            {
                retaliating = 0;
                turning = !turning;
            }
        }
    }
}
