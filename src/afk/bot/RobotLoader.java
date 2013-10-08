package afk.bot;

/**
 *
 * @author Daniel
 */
public interface RobotLoader<R extends Robot>
{

    /**
     * Loads all necessary classes needed for the robot specified by path
     * @param path
     * @throws RobotException
     */
    public void loadRobot(String path) throws RobotException;

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
    public R getRobotInstance(String name) throws RobotException;
    
}
