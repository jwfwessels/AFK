package afk.game;

import afk.bot.RobotException;
import java.awt.Component;

/**
 *
 * @author Daniel
 */
public interface GameCoordinator
{
    /**
     * Gets the AWT Component for this game.
     * @return the AWT component for viewing the game.
     */
    public Component getAWTComponent();
    
    /**
     * Start the game.
     */
    public void start() throws RobotException;
    
    public void playPause();
    
    /**
     * Registers a game listener to receive game events such as game over and
     * player death.
     * @param listener 
     */
    public void addGameListener(GameListener listener);
    
    /**
     * De-register a game listener.
     * @param listener 
     */
    public void removeGameListener(GameListener listener);
}
