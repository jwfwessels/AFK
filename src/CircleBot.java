import afk.bot.london.TankRobot;
import afk.bot.london.VisibleBot;
import com.hackoeur.jglm.support.FastMath;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class CircleBot extends TankRobot
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
