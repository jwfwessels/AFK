
import afk.bot.london.TankRobot;

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

    @Override
    public void run()
    {
	if (!events.visibleBots.isEmpty() && different(events.visibleBots.get(0).bearing, theta))
        {
            System.out.println(getId() + " -> " + (theta = events.visibleBots.get(0).bearing));
        }
        
        System.out.println("sonar_front: " + events.sonar.front);
        System.out.println("sonar_back: " + events.sonar.back);
    }
    
    private boolean different(float a, float b)
    {
        return Math.abs(a-b) > 0.0001f;
    }
}
