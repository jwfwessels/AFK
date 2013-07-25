/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot;

import afk.bot.london.Robot;
import afk.bot.london.RobotLoader;
import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
public abstract class RobotEngine 
{
    protected ArrayList<Robot> robots;
    protected RobotLoader robotLoader;
    
    public abstract Robot[] getRobotInstances();
    public abstract void AddRobot(String path);
    
    
}
