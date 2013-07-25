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
    public London()
    {
        robotLoader = new RobotLoader();
    }
    
    @Override
    public Robot[] getRobotInstances()
    {
        return robotLoader.getRobotInstances();
    }
    
    @Override
    public void addRobot(String path)
    {
        robotLoader.AddRobot(path);
    }

   /* public void registerBot(Robot bot)
    {
        robots.add(bot);
    }
    
    public void resetBotLoader()
    {
        robots.clear();
        robotLoader = new RobotLoader();
        System.gc();
    }

    public ArrayList<Robot> getRobots()
    {
        return robots;
    }*/
}
