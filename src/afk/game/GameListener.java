package afk.game;

/**
 * 
 * @author Daniel
 */
public interface GameListener
{
    /**
     * Indicates a game event happened. This could include players deaths,
     * picking up of key items such as an opponent's flag, etc.
     */
    public void gameEvent();
    
    /**
     * Indicates that a game state change has occurred. This includes
     * end-of-game, or in some unique cases where there may include half-time,
     * etc.
     */
    public void gameStateChange(String[] state);
}
