package afk;

import afk.bot.RobotException;

/**
 *
 * @author Daniel
 */
public interface Coordinator
{
    /**
     * Loads a Robot Class into the JVM classpath from the given file path.
     * @param path the path to the .class or .jar file
     * @throws RobotException if any error occured during the load.
     */
    public void loadRobot(String path) throws RobotException;
    
    /**
     * Adds a Robot to the current game setup
     * @param path the path to the .class or .jar file
     */
    public void addRobot(String path);
    
    /**
     * Removes a Robot from the current game setup
     * @param path the path to the .class or .jar file
     */
    public void removeRobot(String path);
    
    /**
     * Creates a new game with the current configuration and resets the
     * configuration.
     */
    public GameCoordinator newGame();
}
