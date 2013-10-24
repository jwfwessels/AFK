package afk.game;

import afk.bot.Robot;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.UUID;
import java.util.Vector;

/**
 *
 * @author daniel
 */
public class TournamentGameResult extends GameResult
{
    
    private List<Robot[][]> groups;
    private int currentGame;

    public TournamentGameResult(UUID winner, Map<UUID, Integer> scores,
            List<Robot[][]> groups, int currentGame)
    {
        super(winner, scores);
        this.groups = groups;
        this.currentGame = currentGame;
    }

    public TournamentGameResult(GameResult other, List<Robot[][]> groups, int currentGame)
    {
        super(other);
        this.groups = groups;
        this.currentGame = currentGame;
    }

    public Robot[][][] getGroups()
    {
        return groups.toArray(new Robot[0][][]);
    }

    public int getCurrentGame()
    {
        return currentGame;
    }
    
}
