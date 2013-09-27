
import java.util.List;

import afk.bot.london.TankRobot;
import afk.ge.tokyo.ems.components.TargetingInfo;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class ScaredBot extends TankRobot
{

    int movement = 0;
    int rotation = 0;
    boolean turning = true;
    private float thetaAngle;
    boolean antiBot;

    public ScaredBot()
    {
        super();

        antiBot = Math.random() > 0.5;
    }

    @Override
    public void run()
    {
        final List<TargetingInfo> visibles = events.getVisibleBots();
        if (visibles.size() > 0)
        {
            thetaAngle = visibles.get(0).bearing;
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
        } else
        {
            if (events.hitWall())
            {
                if (antiBot)
                {
                    turnAntiClockwise();
                } else
                {
                    turnClockwise();
                }
            } else
            {
                moveForward();
            }
        }
    }
}
