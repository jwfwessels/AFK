package afk.bot.london;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.bot.RobotException;
import afk.bot.RobotEngine;
import afk.bot.RobotLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public class London implements RobotEngine, RobotConfigManager
{

    private Map<UUID, AbstractRobot> robots = new HashMap<UUID, AbstractRobot>();
    private Map<UUID, Map<String, String>> config = new HashMap<UUID, Map<String, String>>();
    private LondonRobotLoader robotLoader;
    // flag that states if bots are still in initialisation phase
    private boolean init = true;

    public London(RobotLoader robotLoader)
    {
        this.robotLoader = (LondonRobotLoader) robotLoader;
    }

    @Override
    public Robot addRobot(String path) throws RobotException
    {
        AbstractRobot r = robotLoader.getRobotInstance(path);
        r.setConfigManager(this);
        UUID id = r.getId();
        robots.put(id, r);
        config.put(id, new HashMap<String, String>());
        r.init();
        return r;
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
        config.remove(id);
    }

    @Override
    public void removeAllRobots()
    {
        robots.clear();
        config.clear();
    }

    @Override
    public void initComplete()
    {
        init = false;
    }

    /// this is where bot execution actually happens
    // TODO: multithread this bad-boy
    @Override
    public void execute()
    {
        if (init)
        {
            throw new RuntimeException("Executing before finishing init phase!");
        }
        for (AbstractRobot robot : robots.values())
        {
//            robot.clearActions(); // braks things now that we use event bases + commands
            robot.run();
        }
    }

    @Override
    public Robot[] getParticipants()
    {
        return robots.values().toArray(new Robot[0]);
    }

    @Override
    public boolean[] getFlags(UUID id)
    {
        return robots.get(id).getActions();
    }

    @Override
    public void setEvents(UUID id, RobotEvent events)
    {
        robots.get(id).events = events;
    }

    @Override
    public String getProperty(UUID id, String name)
    {
        return config.get(id).get(name);
    }

    @Override
    public void setProperty(UUID id, String name, String value)
    {
        if (!init)
        {
            throw new RuntimeException("Property set outside of init phase");
        }
        config.get(id).put(name, value);
    }

    @Override
    public RobotConfigManager getConfigManager()
    {
        return this;
    }
    
}
