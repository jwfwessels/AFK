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
    public Robot[] getRobotInstances()
    {
        Robot[] bots = new Robot[botNames.size()];
        for(int x = 0; x < botNames.size(); x++)
        {
            
            bots[x] = robotLoader.getRobotInstance(botNames.get(x));
            System.out.println("created bot: " + botNames.get(x));
            
            /// refactor
            robots.put(bots[x].getId(), bots[x]);
        }
        return bots;
    }
    
    @Override
    public void addRobot(String path)
    {
        robotLoader.addRobot(path);
    }
    
    public void setParticipatingBots(ArrayList<String> _botNames)
    {
        botNames = _botNames;
    }
    
    /// refactor
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
    
    // FIXME:
    @Override
    public boolean[] getFlags(UUID id)
    {
        return robots.get(id).getActionFlags();
    }
    
    
}
