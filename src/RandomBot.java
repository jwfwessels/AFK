
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

    int movement = 0;
    int rotation = 0;
    boolean turning = true;
    private float thetaAngle;
    boolean retaliating = false;

    public RandomBot()
    {
        super();
    }

    @Override
    public void run()
    {
        float[] visibles = events.getVisibleBots();
        if (events.hitWall())
        {
            movement = 10;
            rotation = 45;
            if (retaliating) turning = !turning;
            retaliating = !retaliating;
        }
	if (visibles.length > 0)
        {
            thetaAngle = visibles[0];
            float diff = FastMath.abs(thetaAngle);

            if (Float.compare(diff, 1) < 0)
            {
                attack();
            } else
            {
                if (Float.compare(thetaAngle, 0) < 0)
                {
                    turnAntiClockwise();
                    thetaAngle++;
                }
                if (Float.compare(thetaAngle, 0) > 0)
                {
                    turnClockwise();
                    thetaAngle--;
                }
            }
        } else if (retaliating)
        {
            retaliate();
        }
        else if (turning)
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
                turnClockwise();
                rotation--;
            } else
            {
                retaliating = false;
                turning = !turning;
            }
        }
        else
        {
            if (movement > 0)
            {
                moveBackwards();
                movement--;
            } else
            {
                retaliating = false;
                turning = !turning;
            }
        }
    }
}
