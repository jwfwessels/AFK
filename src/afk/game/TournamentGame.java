package afk.game;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.ge.tokyo.GameResult;
import java.awt.Component;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class TournamentGame extends AbstractGameMaster implements GameListener
{

    private SingleGame currentGame = null;
    private RobotConfigManager config;
    private Map<UUID, Robot> robots = new HashMap<UUID, Robot>();
    private Map<UUID, Robot> nextRoundRobots = new HashMap<UUID, Robot>();
    private Map<UUID, Integer> scores = new HashMap<UUID, Integer>();
    private Robot[][] groups = null;
    private int currentGroup;
    // TODO: these should be user specified some time
    public static final int MIN_GROUP_SIZE = 3;
    public static final int MAX_GROUP_SIZE = 4;

    public TournamentGame(RobotConfigManager config)
    {
        this.config = config;
    }

    @Override
    public Component getAWTComponent()
    {
        if (currentGame != null)
        {
            return currentGame.getAWTComponent();
        }
        return null;
    }

    @Override
    public void addRobotInstance(Robot robot)
    {
        robots.put(robot.getId(), robot);
    }

    @Override
    public void removeAllRobotInstances()
    {
        robots.clear();
    }

    @Override
    public void removeRobotInstance(Robot robot)
    {
        removeRobotInstance(robot.getId());
    }

    @Override
    public void removeRobotInstance(UUID robot)
    {
        robots.remove(robot);
    }

    @Override
    public String getRobotName(UUID id)
    {
        return config.getProperty(id, "name");
    }

    @Override
    public void gameOver(GameResult result)
    {
        UUID[] bots = result.getTop();
        for (int i = 0; i < bots.length; i++)
        {
            if (i < MAX_GROUP_SIZE/2)
                nextRoundRobots.put(bots[i], robots.get(bots[i]));
            int score = scores.get(bots[i]);
            score += result.getScore(bots[i]);
            scores.put(bots[i], score);
        }
        nextGame();
    }

    private Robot[][] makeGroups()
    {
        int numBots = robots.size();

        int leftOvers = numBots % MAX_GROUP_SIZE;
        int numGroups = numBots / MAX_GROUP_SIZE;

        // if there are leftovers we need an extra group for them
        if (leftOvers > 0)
        {
            numGroups++;
        }

        // initial setup of groups. all have maxGroupSize number of bots in them...
        int[] groupSizes = new int[numGroups];
        for (int i = 0; i < numGroups; i++)
        {
            groupSizes[i] = MAX_GROUP_SIZE;
        }
        // ... except the last one if there are leftovers
        if (leftOvers > 0)
        {
            groupSizes[numGroups - 1] = leftOvers;

            // steal some if there are not enough bots in the last group
            for (int i = 0; groupSizes[numGroups - 1] < MIN_GROUP_SIZE; i = (i + 1) % (numGroups - 1))
            {
                // if stealing causes one of the other groups to fall below minimum group size,
                // then the situation is a fail
                if (groupSizes[i] == MIN_GROUP_SIZE + 1)
                {
                    // we've tried all we can :(
                    System.err.println("Warning: Group sizes cannot conform to specifications.");
                    break;
                }
                groupSizes[i]--;
                groupSizes[numGroups - 1]++;
            }
        }

        // put the robots in the groups
        Robot[][] robotGroups = new Robot[numGroups][];
        Iterator<Robot> it = robots.values().iterator();
        for (int i = 0; i < numGroups; i++)
        {
            robotGroups[i] = new Robot[groupSizes[i]];
            for (int j = 0; j < groupSizes[i]; j++)
            {
                robotGroups[i][j] = it.next();
            }
        }

        return robotGroups;
    }

    @Override
    public void start()
    {
        nextGame();
    }

    private void nextRound()
    {
        groups = makeGroups();
        robots = nextRoundRobots;
        nextRoundRobots = new HashMap<UUID, Robot>();
        currentGroup = 0;
    }

    private void nextGame()
    {
        if (currentGame != null)
        {
            currentGame.stop();
            currentGame.removeGameListener(this);
        }
        currentGroup++;
        if (groups == null || currentGroup > groups.length)
        {
            nextRound();
        }
        currentGame = new SingleGame(config);
        currentGame.addGameListener(this);
        for (int i = 0; i < groups[currentGroup].length; i++)
        {
            currentGame.addRobotInstance(groups[currentGroup][i]);
        }
        currentGame.start();
    }

    @Override
    public void stop()
    {
        if (currentGame != null)
        {
            currentGame.stop();
        }
    }

    @Override
    public void playPause()
    {
        if (currentGame != null)
        {
            currentGame.playPause();
        }
    }

    @Override
    public float getGameSpeed()
    {
        if (currentGame != null)
        {
            return currentGame.getGameSpeed();
        }
        return 1;
    }

    @Override
    public void increaseSpeed()
    {
        if (currentGame != null)
        {
            currentGame.increaseSpeed();
        }
    }

    @Override
    public void decreaseSpeed()
    {
        if (currentGame != null)
        {
            currentGame.decreaseSpeed();
        }
    }
}
