package afk.bot.london;

import java.util.UUID;

/**
 * Represents a bot that is visible to another bot. Used in RobotEvent.
 *
 * @author Daniel
 */
public class VisibleRobot
{

    public UUID targetUUID;
    public float bearing;
    public float elevation;
    public float distance;

    public VisibleRobot(float bearing, float elevation, float distance, UUID targetUUID)
    {
        this.bearing = bearing;
        this.elevation = elevation;
        this.distance = distance;
        this.targetUUID = targetUUID;
    }
}
