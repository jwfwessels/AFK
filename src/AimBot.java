
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
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
    public void init()
    {
        super.init();
        setName("Sitting Duck");
    }

    @Override
    public void gotHit()
    {
        System.out.println("I got hit!");
    }

    @Override
    public void didHit()
    {
        System.out.println("I hit someone!");
    }

    @Override
    public void start()
    {
    }

    @Override
    public void hitObject()
    {
    }

    @Override
    public void sonarWarning(float[] distance)
    {
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        VisibleRobot visible = visibleBots.get(0);
        System.out.println("elevation: " + visible.elevation);
        System.out.println("barrel: " + events.barrel);
        target(visible, 0.6f);
    }

    @Override
    public void idle()
    {
    }
}
