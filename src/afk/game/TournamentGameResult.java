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
    private List<GameResult[]> results;
    private List<Robot[][]> groups;
    private Robot[] robotsThrough;
    private int currentGame;

    public TournamentGameResult(UUID winner, Map<UUID, Integer> scores,
            List<Robot[][]> groups, List<GameResult[]> results,
            Robot[] robotsThrough, int currentGame)
    {
        super(winner, scores);
        this.groups = groups;
        this.currentGame = currentGame;
        this.results = results;
        this.robotsThrough = robotsThrough;
    }

    public TournamentGameResult(GameResult other, List<Robot[][]> groups,
            List<GameResult[]> results, Robot[] robotsThrough,
            int currentGame)
    {
        super(other);
        this.groups = groups;
        this.currentGame = currentGame;
        this.results = results;
        this.robotsThrough = robotsThrough;
    }

    public Robot[][][] getGroups()
    {
        return groups.toArray(new Robot[0][][]);
    }
    
    public GameResult[][] getResults()
    {
        return results.toArray(new GameResult[0][]);
    }

    public int getCurrentGame()
    {
        return currentGame;
    }

    public Robot[] getRobotsThrough()
    {
        return robotsThrough;
    }
    
}
