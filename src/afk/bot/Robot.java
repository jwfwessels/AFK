package afk.bot;

import afk.bot.london.RobotEvent;
import java.util.UUID;

/**
 * This is the interface to all user-written Robot classes.
 * 
 * @author Daniel
 */
public interface Robot
{
    /**
     * Sets the specified flag to the specified value.
     * @param index the index of the flag to set.
     * @param value the value to set the flag to.
     */
    public void setFlag(int index, boolean value);

    /**
     * Sets all flags to there default "false" position.
     */
    public void clearFlags();

    /**
     * Sets the feedback object.
     * @param event 
     */
    public void feedback(RobotEvent event);

    /**
     * Gets a copy of the action flag array.
     * @return 
     */
    public boolean[] getActionFlags();

    /**
     * Gets the robot's unique ID.
     * @return 
     */
    public UUID getId();

    /**
     * Main execution method of the robot implemented by the user. This is
     * called once every game tick to calculate the actions to take for that
     * game tick.
     */
    public void run();
    
    /**
     * Initialisation code is implemented by the user here. This is where any
     * robot configuration properties may be set.
     */
    public void init();
    
}
