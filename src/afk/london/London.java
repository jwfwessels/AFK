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
    
    /*public Robot loadBot(String path)
    {
        return botLoader.LoadRobot(path);
    }*/
    
    public Robot[] InitialiseBots()
    {
        return botLoader.getBots();
    }
    
    public void AddRobot(String path)
    {
        botLoader.AddRobot(path);
    }

    public void registerBot(Robot bot)
    {
        robots.add(bot);
    }
    
    public String getBotLoadingError()
    {
        return botLoader.getError();
    }
    
    public void resetBotLoader()
    {
       // botLoader.clearMaps();
        robots.clear();
        botLoader = new RobotLoader();
        System.gc();
    }
    
    /**
     * Give feedback to all the bots.
     * @param events List of RobotEvent objects to give to each robot.
     */
//    public void feedback(ArrayList<RobotEvent> events)
//    {
//        for (int i = 0; i < events.size(); i++)
//        {
//            Robot robot = robots.get(i);
//            robot.events = events.get(i);
//        }
//    }

    public ArrayList<Robot> getRobots()
    {
        return robots;
//        return (ArrayList<Robot>)robots.clone();
    }
}
