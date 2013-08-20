/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

import afk.bot.RobotException;
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
    
    private Map<UUID, Robot> robots = new HashMap<UUID, Robot>();
    private RobotLoader robotLoader = new RobotLoader();

    @Override
    public UUID addRobot(String path) throws RobotException
    {
        Robot r = robotLoader.getRobotInstance(path);
        UUID id = r.getId();
        robots.put(id, r);
        return id;
    }

    @Override
    public void loadRobot(String path) throws RobotException
    {
        try
        {
            robotLoader.addRobot(path);
        } catch (RobotException e)
        {
            //TODO: Error reporting
            throw e;
        }
    }

    /// this is where bot execution actually happens
    // TODO: multithread this bad-boy
    @Override
    public void execute()
    {
        for (Robot robot : robots.values())
        {
            robot.clearFlags();
            robot.run();

            // FIXME: uncomment once the data system is set up
            //db.getController(robot.id).flags = robot.getActionFlags();
        }
    }

    // FIXME: db thing...
    @Override
    public boolean[] getFlags(UUID id)
    {
        return robots.get(id).getActionFlags();
    }

    @Override
    public void setEvents(UUID id, RobotEvent events)
    {
        robots.get(id).events = events;
    }
}
