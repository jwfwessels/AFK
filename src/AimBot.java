
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
        
        System.out.println("turret: " + events.turret);
        
        float[][] visibles = events.getVisibleBots();
        if (visibles.length > 0)
        {
            float bearing = visibles[0][0]-events.turret;
            float elevation = visibles[0][1]-events.barrel;
            float diff = bearing*bearing+elevation*elevation;
            System.out.printf("bearing: %g, elev: %g\n", bearing, elevation);
            System.out.printf("diff: %g\n", diff);

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
