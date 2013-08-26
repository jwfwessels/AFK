/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot;

import afk.bot.london.RobotEvent;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public interface RobotEngine
{

    public UUID addRobot(String path) throws RobotException;

    //public void loadRobot(String path) throws RobotException; // refactored to separate RobotLoader interface
    
    /// refactor
    public void execute();

    // FIXME: remove once db system is up and running ???
    public boolean[] getFlags(UUID id);

    public void setEvents(UUID id, RobotEvent events);
}
