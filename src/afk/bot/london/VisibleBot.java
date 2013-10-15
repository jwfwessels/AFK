package afk.bot.london;

import java.util.UUID;

/**
 * Represents a bot that is visible to another bot. Used in RobotEvent.
 *
 * @author Daniel
 */
public class VisibleBot
{

    public UUID targetUUID;
    public float bearing;
    public float elevation;

    public VisibleBot(float bearing, float elevation, UUID targetUUID)
    {
        this.bearing = bearing;
        this.elevation = elevation;
        this.targetUUID = targetUUID;
    }
}
