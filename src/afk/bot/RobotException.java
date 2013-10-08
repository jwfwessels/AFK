/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot;

/**
 *
 * @author Jessica
 */
public class RobotException extends Exception
{

    public RobotException(String message)
    {
        super(message);
    }

    public RobotException(Throwable cause)
    {
        super(cause);
    }
    
    
    public RobotException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
