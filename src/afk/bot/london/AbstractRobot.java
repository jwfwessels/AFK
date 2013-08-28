package afk.bot.london;

import afk.bot.Robot;
import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public abstract class AbstractRobot implements Robot
{
    private boolean[] actionFlags;
    protected RobotEvent events;
    private UUID id;

    public AbstractRobot(int numActions)
    {
        actionFlags = new boolean[numActions];
        events = new RobotEvent();

        id = UUID.randomUUID();
    }
    

    @Override
    public final UUID getId()
    {
        return id;
    }

    @Override
    public final void setFlag(int index, boolean value)
    {
        if (index <= actionFlags.length && index >= 0)
        {
            actionFlags[index] = value;
        }
    }

    @Override
    public final boolean[] getActionFlags()
    {
        return Arrays.copyOf(actionFlags, actionFlags.length);
    }

    @Override
    public final void clearFlags()
    {
        for (int x = 0; x < actionFlags.length; x++)
        {
            actionFlags[x] = false;
        }
    }

    @Override
    public final void feedback(RobotEvent event)
    {
        this.events = event;
    }
    
}
