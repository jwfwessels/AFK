/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
public class London
{

    private ArrayList<Robot> robots;
    private RobotLoader robotLoader;

    public London()
    {
        robots = new ArrayList<Robot>();
        robotLoader = new RobotLoader();
    }
    
    public Robot[] InitialiseBots()
    {
        return robotLoader.getRobotInstances();
    }
    
    public void AddRobot(String path)
    {
        robotLoader.AddRobot(path);
    }

    public void registerBot(Robot bot)
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
    }
}
