package afk.bot;

import afk.bot.london.Robot;

/**
 *
 * @author Daniel
 */
public interface RobotLoader
{

    /**
     * Loads all necessary classes needed for the robot specified by path
     * @param path
     * @throws RobotException
     */
    public void addRobot(String path) throws RobotException;

    /**
     *
     */
    public void clearMaps();

    /**
     * Returns instances of robots that are in robotClasses - to be used when game is started
     * @param name
     * @return
     * @throws RobotException
     */
    public Robot getRobotInstance(String name) throws RobotException;
    
}
