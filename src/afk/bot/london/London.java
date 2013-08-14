/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

import afk.bot.RobotEngine;
import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
public class London extends RobotEngine
{
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
            robotLoader.AddRobot(path);
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
}
