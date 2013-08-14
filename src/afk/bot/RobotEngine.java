/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot;

import afk.bot.london.Robot;
import afk.bot.london.RobotException;
import afk.bot.london.RobotLoader;
import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
public abstract class RobotEngine 
{
    protected RobotLoader robotLoader;
    
    public abstract Robot[] getRobotInstances() throws RobotException;
    public abstract void addRobot(String path) throws RobotException;
    
    
}
