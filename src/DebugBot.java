
import java.util.List;

import afk.bot.london.TankRobot;
import afk.ge.tokyo.ems.components.TargetingInfo;

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
        final List<TargetingInfo> visibles = events.getVisibleBots();
	if (visibles.size() > 0 && different(visibles.get(0).bearing, theta))
        {
            System.out.println(getId() + " -> " + (theta = visibles.get(0).bearing));
        }
    }
    
    private boolean different(float a, float b)
    {
        return Math.abs(a-b) > 0.0001f;
    }
}
