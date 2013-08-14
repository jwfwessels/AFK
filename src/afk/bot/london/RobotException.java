/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

/**
 *
 * @author Jessica
 */
public class RobotException extends Exception
{
    private String message = "";
    public RobotException(String _message)
    {
        message = _message;
    }
    
    @Override
    public String getMessage()
    {
        return message;
    }
}
