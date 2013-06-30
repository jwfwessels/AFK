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
    private RobotLoader botLoader;

    public London()
    {
        robots = new ArrayList<Robot>();
        botLoader = new RobotLoader();
    }
    
    public Robot loadBot(String path)
    {
        return botLoader.LoadRobot(path);
    }

    public void registerBot(Robot bot)
    {
        robots.add(bot);
    }

    public ArrayList<Robot> getRobots()
    {
        return robots;
//        return (ArrayList<Robot>)robots.clone();
    }
}
