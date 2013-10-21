package afk.game;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class TournamentGame extends AbstractGameMaster
{
    private SingleGame currentGame = null;
    private RobotConfigManager config;
    private Collection<Robot> robots = new ArrayList<Robot>();

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
        robots.add(robot);
    }

    @Override
    public void removeAllRobotInstances()
    {
        robots.clear();
    }

    @Override
    public void removeRobotInstance(Robot robot)
    {
        robots.remove(robot);
    }

    @Override
    public void removeRobotInstance(UUID robot)
    {
        Iterator<Robot> it = robots.iterator();
        while(it.hasNext())
        {
            if (it.next().getId().equals(robot))
            {
                it.remove();
            }
        }
    }

    @Override
    public String getRobotName(UUID id)
    {
        return config.getProperty(id, "name");
    }

    @Override
    public void start()
    {
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
