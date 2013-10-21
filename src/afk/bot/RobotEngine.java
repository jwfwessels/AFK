package afk.bot;

import afk.bot.london.RobotEvent;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public interface RobotEngine
{
    /**
     * Adds an instance of a robot to this robot engine and initialises it.
     * @param robot the robot to add
     */
    public void addRobot(Robot robot);
    
    /**
     * Removes an instance of a robot from this robot engine.
     * @param id the id of the robot to remove.
     */
    public void removeRobot(UUID id);
    
    /**
     * Removes an instance of a robot from this robot engine.
     * @param robot the robot to remove.
     */
    public void removeRobot(Robot robot);
    
    /**
     * Removes all robots from this robot engine.
     */
    public void removeAllRobots();

    /**
     * Retrieve a list of all the currently added robot instances.
     * @return a list of the UUIDs of the current robot instances. 
     */
    public Robot[] getAllRobots();
    
    /**
     * Executes a single logic tick of a robot.
     * @param id the id of the robot.
     */
    public void execute(UUID id);

    /**
     * Get the action flags from the specified robot since the last execution.
     * @param id
     * @return 
     */
    public boolean[] getFlags(UUID id);

    /**
     * Sets the events of the specified robot for the next execution.
     * @param id
     * @param events 
     */
    public void setEvents(UUID id, RobotEvent events);
    
    /**
     * Get the configuration manager for this robot engine.
     * @return 
     */
    public RobotConfigManager getConfigManager();
}
