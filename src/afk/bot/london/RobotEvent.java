package afk.bot.london;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class RobotEvent
{
    
    /** List of visible bots as angles from where tank is facing. Empty if there are none. */
    public List<VisibleBot> visibleBots;
    
    public float turret;
    public float barrel;
    
    /** True if bot was hit by another. */
    public boolean gotHit;
    
    /** True if bot successfully hit another. */
    public boolean didHit;
    
    /** true if bot drove into a wall. */
    public boolean hitWall;

    public RobotEvent()
    {
        visibleBots = new ArrayList<VisibleBot>();
        gotHit = false;
        didHit = false;
        hitWall = false;
    }
    
}
