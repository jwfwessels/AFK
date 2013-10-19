package afk.ge.tokyo.ems.components;

import afk.bot.london.RobotEvent;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public class Controller
{
    public UUID id = null;
    public boolean[] inputFlags = null;
    public RobotEvent events = null;
    public int score = 0;

    public Controller()
    {
    }

    public Controller(UUID id)
    {
        this.id = id;
        events = new RobotEvent();
    }
}
