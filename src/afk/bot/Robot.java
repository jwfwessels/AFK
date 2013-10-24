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
     * Sets all flags to there default "false" position.
     */
    public void clearActions();

    /**
     * Gets a copy of the action array.
     * @return 
     */
    public boolean[] getActions();
    
    /**
     * Sets the events for this tick.
     * @param events the events.
     */
    public void setEvents(RobotEvent events);

    /**
     * Gets the robot's unique ID.
     * @return 
     */
    public UUID getId();
    
    /**
     * Get the robot's number.
     * @return the robot's number.
     */
    public int getBotNum();

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
    
    /**
     * Gets the RobotConfigManager associated with this robot.
     * @return the RobotConfigManager associated with this robot.
     */
    public RobotConfigManager getConfigManager();
    
    /**
     * Sets the RobotConfigManager to associate with this robot.
     * @return the RobotConfigManager to associate with this robot.
     */
    public void setConfigManager(RobotConfigManager config);
    
}
