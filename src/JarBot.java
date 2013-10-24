
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;


/**
 * Sample robot class, same as randomBot but makes use of RandomClass class
 * Use for testing Jar loading
 * @author Jessica
 *
 */
public class JarBot extends TankRobot
{
    RandomClass manager = new RandomClass();

    public JarBot()
    {
        
    }

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        manager.setMovement(200);
        manager.setRotation(180);
        manager.setTurning(true);
    }

    @Override
    public void gotHit()
    {
        idle();
    }

    @Override
    public void didHit()
    {
        idle();
    }

    @Override
    public void sonarWarning(float[] distance)
    {
        idle();
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        target(visibleBots.get(0), 0.6f);
    }

    @Override
    public void idle()
    {
        if (manager.getTurning())
        {
            if(manager.turn())
            {
                turnAntiClockwise(1);
            }
        }
        else
        {
            if(manager.move())
            {
                moveForward(1);
            }
        }
    }
}
