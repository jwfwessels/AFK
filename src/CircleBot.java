
import afk.london.Robot;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class CircleBot extends Robot
{

    int movement = 0;
    int rotation = 0;
    boolean turning = true;
    private float thetaAngle;

    public CircleBot()
    {
        super();
    }

    @Override
    public void run()
    {

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
        if (rotation < 90)
        {
            turnAntiClockwise();
            rotation++;
        } else
        {
            rotation = 0;
            turning = false;
        }
    }

    public void move()
    {
        if (movement < 1600)
        {
            moveForward();
            movement++;
        } else
        {
            movement = 0;
            turning = true;
        }
    }
}
