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
     * Adds an instance of a robot to this robot engine.
     * @param path
     * @return
     * @throws RobotException 
     */
    public UUID addRobot(String path) throws RobotException;

    /**
     * Initialises all the currently added robots.
     */
    public void init();
    
    /**
     * Executes a single logic tick of each robot.
     */
    public void execute();

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
