package afk.ge.tokyo;

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
    
    public UUID[] getParticipants()
    {
        return scores.keySet().toArray(new UUID[0]);
    }
}
