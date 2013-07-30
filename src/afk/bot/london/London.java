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
    public Robot[] getRobotInstances()
    {
        Robot[] bots = new Robot[botNames.size()];
        for(int x = 0; x < botNames.size(); x++)
        {
            
            bots[x] = robotLoader.getRobotInstance(botNames.get(x));
            System.out.println("created bot: " + botNames.get(x));
        }
        return bots;
    }
    
    @Override
    public void addRobot(String path)
    {
        robotLoader.AddRobot(path);
    }
    
    public void setParticipatingBots(ArrayList<String> _botNames)
    {
        botNames = _botNames;
    }
}
