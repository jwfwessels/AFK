
import afk.bot.london.TankRobot;

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
        
        float[][] visibles = events.getVisibleBots();
        if (visibles.length > 0)
        {
            System.out.println("elevation: " + visibles[0][1]);
            System.out.println("barrel: " + events.barrel);
            float bearing = visibles[0][0]-events.turret;
            float elevation = visibles[0][1]-events.barrel;
            float diff = bearing*bearing+elevation*elevation;

            if (Float.compare(diff, 0.6f) < 0)
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
