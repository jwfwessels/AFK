package afk.game;

import afk.ge.tokyo.GameResult;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Common implementation of GameMaster
 * @author Daniel
 */
public abstract class AbstractGameMaster implements GameMaster
{
    
    protected Collection<GameListener> listeners = new ArrayList<GameListener>();

    @Override
    public void gameOver(GameResult result)
    {
        for (GameListener l : listeners)
        {
            l.gameOver(result);
        }
    }
    
    @Override
    public void addGameListener(GameListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeGameListener(GameListener listener)
    {
        listeners.remove(listener);
    }
}
