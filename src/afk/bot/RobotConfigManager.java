package afk.bot;

import java.util.UUID;

/**
 *
 * @author Daniel
 */
public interface RobotConfigManager
{
    
    /**
     * Indicates that the initialisation phase is over and the game has started,
     */
    public void initComplete();
    
    /**
     * Check if initialisation is complete.
     * @return true if initialisation is complete, false otherwise.
     */
    public boolean isInitComplete();
    
    /**
     * Gets a property for a robot.
     * @param id the id of the robot.
     * @param name the name of the property to get.
     * @return the value of the property.
     */
    public String getProperty(UUID id, String name);
    
    /**
     * Sets a property for a robot.
     * @param id the id of the robot.
     * @param name the name of the property to set.
     * @param value the value to set the property to.
     */
    public void setProperty(UUID id, String name, String value);
    
    /**
     * Get the value of a constant.
     * @param the id of the robot.
     * @param name the name of the constant.
     * @return the value of the constant, null if the constant does not exist.
     */
    public Object getConstant(UUID id, String name);
}
