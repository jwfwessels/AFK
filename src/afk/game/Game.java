package afk.game;

import afk.bot.Robot;
import afk.bot.RobotException;
import java.awt.Component;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public interface Game extends GameListener{

    /**
     * Gets the AWT Component for this game.
     *
     * @return the AWT component for viewing the game.
     */
    public Component getAWTComponent();
    
    public Robot addRobotInstance(String robot) throws RobotException;
    
    public void removeAllRobotInstances();
    
    public void removeRobotInstance(Robot robot);
    
    public void removeRobotInstance(UUID robot);

    /**
     * Start the game.
     */
    public void start() throws RobotException;

//    public void playPause();
    
    public float getGameSpeed();

    public void increaseSpeed();

    public void decreaseSpeed();

    /**
     * Registers a game listener to receive game events such as game over and
     * player death.
     *
     * @param listener
     */
    public void addGameListener(GameListener listener);

    /**
     * De-register a game listener.
     *
     * @param listener
     */
    public void removeGameListener(GameListener listener);
}
