
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
        float[][] visibles = events.getVisibleBots();
	if (visibles.length > 0 && different(visibles[0][0], theta))
        {
            System.out.println(getId() + " -> " + (theta = visibles[0][0]));
        }
    }
    
    private boolean different(float a, float b)
    {
        return Math.abs(a-b) > 0.0001f;
    }
}
