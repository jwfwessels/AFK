package afk.bot.london;

import afk.bot.Robot;
import java.util.Arrays;
import java.util.UUID;

/**
 * @author Jessica
 */
public abstract class TankRobot implements Robot
{

    public static final int NUM_ACTIONS = 5;
    //Index mapping of flag array
    public static final int MOVE_FRONT = 0;
    public static final int MOVE_BACK = 1;
    public static final int TURN_CLOCK = 2;
    public static final int TURN_ANTICLOCK = 3;
    public static final int ATTACK_ACTION = 4;
    private boolean[] actionFlags;
    protected RobotEvent events;
    private UUID id;

    public TankRobot()
    {
        actionFlags = new boolean[NUM_ACTIONS];
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
        if (index <= NUM_ACTIONS && index >= 0)
        {
            actionFlags[index] = value;
        }
    }

    @Override
    public final boolean[] getActionFlags()
    {
        return Arrays.copyOf(actionFlags, actionFlags.length);
    }

    protected final void moveForward()
    {
        setFlag(MOVE_FRONT, true);
        setFlag(MOVE_BACK, false);
    }

    protected final void moveBackwards()
    {
        setFlag(MOVE_BACK, true);
        setFlag(MOVE_FRONT, false);
    }

    protected final void turnClockwise()
    {
        setFlag(TURN_CLOCK, true);
        setFlag(TURN_ANTICLOCK, false);
    }

    protected final void turnAntiClockwise()
    {
        setFlag(TURN_ANTICLOCK, true);
        setFlag(TURN_CLOCK, false);
    }

    protected final void attack()
    {
        setFlag(ATTACK_ACTION, true);
    }

    @Override
    public final void clearFlags()
    {
        for (int x = 0; x < NUM_ACTIONS; x++)
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
