package afk.bot.london;

/**
 * Represents a bot that is visible to another bot. Used in RobotEvent.
 * @author Daniel
 */
public class VisibleBot
{

    public float bearing;
    public float elevation;

    public VisibleBot(float bearing, float elevation)
    {
        this.bearing = bearing;
        this.elevation = elevation;
    }
}
