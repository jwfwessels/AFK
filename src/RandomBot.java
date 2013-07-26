
import afk.bot.london.SmallTank;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomBot extends SmallTank
{

    int movement = 0;
    int rotation = 0;
    boolean turning = true;
    private float thetaAngle;

    public RandomBot()
    {
        super();
    }

    @Override
    public void run()
    {
        if (events.hitWall())
        {
            movement = 200;
            rotation = 180;
            turning = true;
        }

        float[] visibles = events.getVisibleBots();
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

                    turnClockwise();
                    thetaAngle++;
                }
                if (Float.compare(thetaAngle, 0) > 0)
                {
                    turnAntiClockwise();
                    thetaAngle--;
                }
            }
        } else if (turning)
        {
            turn();
        } else
        {
            move();
        }
    }

    public void turn()
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

    public void move()
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
}
