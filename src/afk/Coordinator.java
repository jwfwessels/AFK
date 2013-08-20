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
     * Adds an instance of a Robot to the current game setup
     * @param path the path to the .class or .jar file
     * @throws RobotException if any error occured during the instantiation.
     */
    public void addRobot(String path) throws RobotException;
    
    /**
     * Starts a new AFK game with the current configuration.
     */
    public GameCoordinator startGame();
}
