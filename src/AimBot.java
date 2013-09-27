import java.util.List;

import afk.bot.london.TankRobot;
import afk.ge.tokyo.ems.components.TargetingInfo;

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
        
        final List<TargetingInfo> visibles = events.getVisibleBots();
        if (visibles.size() > 0)
        {
            System.out.println("elevation: " + visibles.get(0).elevation);
            System.out.println("barrel: " + events.barrel);
            float bearing = visibles.get(0).bearing;
            float elevation = visibles.get(0).elevation-events.barrel;
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
