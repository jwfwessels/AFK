
import afk.bot.london.TankRobot;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomBot1 extends TankRobot
{

    public RandomBot1()
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

            if (Float.compare(diff, 1) < 0)
            {
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
            return;
        }
    }
}
