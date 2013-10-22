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
    private Map<UUID, Robot> allRobots = new HashMap<UUID, Robot>();
    private Map<UUID, Robot> robots = new HashMap<UUID, Robot>();
    private Map<UUID, Integer> scores = new HashMap<UUID, Integer>();
    private Robot[][] groups = null;
    private int currentGroup = 0;
    private int currentRound = 0;
    private int botsThisRounds = 0;
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
        allRobots.put(robot.getId(), robot);
        scores.put(robot.getId(), 0);
    }

    @Override
    public void removeAllRobotInstances()
    {
        allRobots.clear();
        scores.clear();
    }

    @Override
    public void removeRobotInstance(Robot robot)
    {
        removeRobotInstance(robot.getId());
    }

    @Override
    public void removeRobotInstance(UUID robot)
    {
        allRobots.remove(robot);
        scores.remove(robot);
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
            if (i < MAX_GROUP_SIZE / 2)
            {
                robots.put(bots[i], allRobots.get(bots[i]));
            }
            int score = scores.get(bots[i]);
            score += result.getScore(bots[i]);
            scores.put(bots[i], score);
        }
        if (bots.length == 2)
        {
            tournamentOver();
        } else
        {
            displayScores(result);
//            nextGame();
        }
    }

    @Override
    public void displayScores(GameResult result)
    {
        for (GameListener listener : listeners)
        {
            listener.gameOver(result);
        }
    }

    private void tournamentOver()
    {
        for (GameListener listener : listeners)
        {
            UUID winner = null;
            int highScore = 0;
            // TODO: deal with ties? here or somehwere else?
            for (UUID id : scores.keySet())
            {
                int score = scores.get(id);
                if (score > highScore)
                {
                    winner = id;
                    highScore = score;
                }
            }
            listener.gameOver(new GameResult(winner, scores));
        }
    }

    @Override
    public void newGame(GameMaster gm)
    {
//        JOptionPane.showMessageDialog(null,
//                "Round " + currentRound + "\n"
//                + "Group " + (currentGroup + 1) + "/" + groups.length + "\n"
//                + "Bots: " + botsThisRounds);
        for (GameListener listener : listeners)
        {
            listener.newGame(gm);
        }
    }

    private Robot[][] makeGroups()
    {
        int numBots = robots.size();

        if (numBots < MIN_GROUP_SIZE)
        {
            Robot[][] robotGroups = new Robot[1][numBots];
            Iterator<Robot> it = robots.values().iterator();
            for (int i = 0; i < numBots; i++)
            {
                robotGroups[0][i] = it.next();
            }
            return robotGroups;
        }

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
        robots.putAll(allRobots);
        nextGame();
    }

    private void nextRound()
    {
        botsThisRounds = robots.size();
        groups = makeGroups();
        robots.clear();
        currentGroup = 0;
        currentRound++;
    }

    @Override
    public void nextGame()
    {
        if (currentGame != null)
        {
            currentGame.stop();
        }
        currentGroup++;
        if (groups == null || currentGroup >= groups.length)
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

    private void printGroups()
    {
        System.out.print("[ ");
        for (int i = 0; i < groups.length; i++)
        {
            for (int j = 0; j < groups[i].length; j++)
            {
                System.out.print((groups[i][j] == null ? 0 : 1) + " ");
            }
            if (i != groups.length - 1)
            {
                System.out.print("| ");
            }
        }
        System.out.println("]");
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