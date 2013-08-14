
import afk.bot.london.LargeTank;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class CircleBot extends LargeTank
{

    int movement = 0;
    int rotation = 0;
    boolean turning = true;
    private float thetaAngle;
    boolean antiBot;

    public CircleBot()
    {
        super();

        antiBot = Math.random() > 0.5;
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
                    turnAntiClockwise();
                    thetaAngle++;
                }
                if (Float.compare(thetaAngle, 0) > 0)
                {
                    turnClockwise();
                    thetaAngle--;
                }
            }
        } else
        {

            if (events.hitWall())
            {
                if (antiBot)
                {
//                    moveBackwards();
                    turnAntiClockwise();
                } else
                {
//                    moveBackwards();
                    turnClockwise();
                }
            } else
            {
                moveForward();
            }
        }
    }
}
