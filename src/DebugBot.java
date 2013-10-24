
import afk.bot.london.Sonar;
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class DebugBot extends TankRobot
{
    private float theta = Float.POSITIVE_INFINITY;

    public DebugBot()
    {
        super();
    }
    
    private boolean different(float a, float b)
    {
        return Math.abs(a-b) > 0.0001f;
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
    public void gotHit()
    {
    }

    @Override
    public void didHit()
    {
    }

    @Override
    public void sonarWarning(float[] distance)
    {
        System.out.println("sonar_front: " + distance[Sonar.FRONT]);
        System.out.println("sonar_back: " + distance[Sonar.BACK]);
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        if (different(visibleBots.get(0).bearing, theta))
        {
            System.out.println(getId() + " -> " + (theta = visibleBots.get(0).bearing));
        }
    }

    @Override
    public void idle()
    {
    }
}
