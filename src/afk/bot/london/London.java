/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

import afk.bot.RobotEngine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public class London extends RobotEngine
{
    /// refactor
    Map<UUID, Robot> robots = new HashMap<UUID, Robot>();
    
    ArrayList<String> botNames = new ArrayList<String>();
    public London()
    {
        robotLoader = new RobotLoader();
    }
    
    @Override
    public Robot[] getRobotInstances() throws RobotException
    {
        Robot[] bots = new Robot[botNames.size()];
        for(int x = 0; x < botNames.size(); x++)
        {
            try
            {
                bots[x] = robotLoader.getRobotInstance(botNames.get(x));
                System.out.println("created bot: " + botNames.get(x));
                
                /// adding bot to 'robots' hashmap
                robots.put(bots[x].getId(), bots[x]);
            }
            catch(RobotException e)
            {
                System.out.println("In London: " + e.getMessage());
                  throw e;
            }
        }
        return bots;
    }
    
    @Override
    public void addRobot(String path) throws RobotException
    {
        try
        {
            robotLoader.addRobot(path);
        }
        catch(RobotException e)
        {
            //TODO: Error reporting
            System.out.println("In London: " + e.getMessage());
            throw e;
        }
    }
    
    public void setParticipatingBots(ArrayList<String> _botNames)
    {
        botNames = _botNames;
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
