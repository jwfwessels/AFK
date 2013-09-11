
import afk.bot.london.TankRobot;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomBot extends TankRobot
{

    private int movement = 0;
    private int rotation = 0;
    private boolean turning = true;
    private int retaliating = 0;

    public RandomBot()
    {
        super();
    }

    @Override
    public void run()
    {
        float[][] visibles = events.getVisibleBots();
        if (events.hitWall())
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
        if (visibles.length > 0)
        {
            float bearing = visibles[0][0];
            float elevation = visibles[0][1];
            float diff = FastMath.abs(bearing);

            if (Float.compare(FastMath.abs(elevation), 1) < 0)
            {
                if (Float.compare(diff, 1) < 0)
                {
                    attack();
                    return;
                }
                {
                    if (Float.compare(bearing, 0) < 0)
                    {
                        turnAntiClockwise();
                        bearing++;
                    }
                    if (Float.compare(bearing, 0) > 0)
                    {
                        turnClockwise();
                        bearing--;
                    }
                    // TODO: move barrel when we can
                }
            } else if (retaliating == 0 && !turning)
            {
                move();
            }
        }
        if (retaliating != 0)
        {
            retaliate();
        } else if (turning)
        {
            turn();
        }
        else
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
