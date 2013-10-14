
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleBot;
import java.util.List;

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
    public void init()
    {
        super.init();
        setName("Die Snaakste Pampoen");
    }

    @Override
    public void run()
    {
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
        List<VisibleBot> visibles = events.getVisibleBots();
        if (!visibles.isEmpty())
        {
            VisibleBot visible = visibles.get(0);
            float bearing = visible.bearing;
            float elevation = visible.elevation-events.barrel;
            float diff = bearing*bearing+elevation*elevation;
            final float give = 0.6f;

            if (Float.compare(diff, give*give) < 0)
            {
                attack();
                return;
            }
            
            if (Float.compare(bearing, 0) < 0)
            {
                aimAntiClockwise();
            }
            if (Float.compare(bearing, 0) > 0)
            {
                aimClockwise();
            }

            if (Float.compare(elevation, 0) < 0)
            {
                aimDown();
            }
            if (Float.compare(elevation, 0) > 0)
            {
                aimUp();
            }
        } else if (retaliating != 0)
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
