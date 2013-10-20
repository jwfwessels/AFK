package afk.game;

import afk.ge.tokyo.GameResult;

/**
 * 
 * @author Daniel
 */
public interface GameListener
{
    /**
     * Called when a game is complete.
     * @param result the result of the game.
     */
    public void gameOver(GameResult result);
}
