package afk.bot.london;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class RobotEvent
{

    /**
     * List of visible bots as angles from where tank is facing. Empty if there
     * are none.
     */
    public List<VisibleBot> visibleBots = new ArrayList<VisibleBot>();
    /**
     * The robot's current pitch.
     */
    public float pitch = 0;
    /**
     * The robot's current heading.
     */
    public float heading = 0;
    /**
     * The current angle of the turret relative to the robot.
     */
    public float turret = 0;
    /**
     * The current angle of the barrel relative to the robot.
     */
    public float barrel = 0;
    /**
     * True if bot was hit by another.
     */
    public boolean gotHit = false;
    /**
     * True if bot successfully hit another.
     */
    public boolean didHit = false;
    /**
     * true if bot drove into a wall.
     */
    public boolean hitWall = false;
}
