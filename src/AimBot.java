
import afk.bot.london.RobotEvent;
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleBot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class AimBot extends TankRobot
{

    public AimBot()
    {
        super();
    }

    @Override
    public void run()
    {
        List<VisibleBot> visibles = events.getVisibleBots();
        if (!visibles.isEmpty())
        {
            VisibleBot visible = visibles.get(0);
            System.out.println("elevation: " + visible.elevation);
            System.out.println("barrel: " + events.barrel);
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
        }
    }
}
