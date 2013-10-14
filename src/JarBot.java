
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleBot;
import java.util.List;


/**
 * Sample robot class, same as randomBot but makes use of SomeClass class
 * Use for testing Jar loading
 * @author Jessica
 *
 */
public class JarBot extends TankRobot
{
    //int movement = 0;
    //int rotation = 0;
    //boolean turning = true;
    RandomClass manager = new RandomClass();

    public JarBot()
    {
        
    }

    @Override
    public void run()
    {
        if (events.hitWall())
        {
            manager.setMovement(200);
            manager.setRotation(180);
            manager.setTurning(true);
        }
        
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
        } else if (manager.getTurning())
        {
            if(manager.turn())
            {
                turnAntiClockwise();
            }
        }
        else
        {
            if(manager.move())
            {
                moveForward();
            }
        }
    }
}
