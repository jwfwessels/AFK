/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot;

import afk.bot.london.Robot;
import afk.bot.london.RobotLoader;
import java.util.ArrayList;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public abstract class RobotEngine 
{
    protected RobotLoader robotLoader;
    
    public abstract Robot[] getRobotInstances();
    public abstract void addRobot(String path);
    
    
    /// refactor
    public abstract void execute();
    // FIXME: remove once db system is up and running
    public abstract boolean[] getFlags(UUID id);
    
    
}
