package afk.ge.tokyo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public class GameResult
{
    private UUID winner;
    private Map<UUID, Integer> scores;

    public GameResult(UUID winner, Map<UUID, Integer> scores)
    {
        this.winner = winner;
        this.scores = scores;
    }

    public UUID getWinner()
    {
        return winner;
    }
    
    public int getScore(UUID id)
    {
        return scores.get(id);
    }
}
