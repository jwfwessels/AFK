package afk.bot.london;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.bot.RobotEngine;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public class London implements RobotEngine
{
    private RobotConfigManager config;
    private Map<UUID, Robot> robots = new HashMap<UUID, Robot>();

    public London(RobotConfigManager config)
    {
        this.config = config;
    }

    @Override
    public void addRobot(Robot robot) 
    {
        UUID id = robot.getId();
        robots.put(id, (AbstractRobot)robot);
    }

    @Override
    public void removeRobot(Robot robot)
    {
        removeRobot(robot.getId());
    }

    @Override
    public void removeRobot(UUID id)
    {
        robots.remove(id);
    }

    @Override
    public void removeAllRobots()
    {
        robots.clear();
    }

    /// this is where bot execution actually happens
    // TODO: multithread this bad-boy
    @Override
    public void execute(UUID id)
    {
        if (config.isInitComplete())
        {
            throw new RuntimeException("Executing before finishing init phase!");
        }
        Robot robot = robots.get(id);
        if (robot == null)
        {
            throw new RuntimeException("Robot " + id + " does not exist in this engine.");
        }
        robot.clearFlags();
        robot.run();
    }

    @Override
    public Robot[] getAllRobots()
    {
        return robots.values().toArray(new Robot[0]);
    }

    @Override
    public boolean[] getFlags(UUID id)
    {
        return robots.get(id).getActionFlags();
    }

    @Override
    public void setEvents(UUID id, RobotEvent events)
    {
        robots.get(id).setEvents(events);
    }

    @Override
    public RobotConfigManager getConfigManager()
    {
        return config;
    }
    
}
