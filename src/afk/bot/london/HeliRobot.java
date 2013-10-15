package afk.bot.london;

import afk.bot.Aimable;
import afk.bot.Attackable;

/**
 * @author Jessica
 */
public abstract class HeliRobot extends AbstractRobot implements Aimable, Attackable
{

    public static final int NUM_ACTIONS = 13;
    //Index mapping of flag array
    public static final int MOVE_FRONT = 0;
    public static final int MOVE_BACK = 1;
    public static final int TURN_CLOCK = 2;
    public static final int TURN_ANTICLOCK = 3;
    public static final int ATTACK_ACTION = 4;
    public static final int AIM_CLOCK = 5;
    public static final int AIM_ANTICLOCK = 6;
    public static final int AIM_UP = 7;
    public static final int AIM_DOWN = 8;
    public static final int MOVE_LEFT = 9;
    public static final int MOVE_RIGHT = 10;
    public static final int MOVE_UP = 11;
    public static final int MOVE_DOWN = 12;

    public HeliRobot()
    {
        super(NUM_ACTIONS);
    }

    // to make the super constructor hidden from subclasses
    private HeliRobot(int numActions)
    {
        super(numActions);
    }

    @Override
    public void init()
    {
        super.init();
        setType("heli");
    }

    /**
     * Moves the helicopter forward this game tick.
     */
    protected final void moveForward()
    {
        setFlag(MOVE_FRONT, true);
        setFlag(MOVE_BACK, false);
    }

    /**
     * Moves the helicopter backward this game tick.
     */
    protected final void moveBackwards()
    {
        setFlag(MOVE_BACK, true);
        setFlag(MOVE_FRONT, false);
    }

    /**
     * Moves the helicopter left this game tick;
     */
    protected final void moveLeft()
    {
        setFlag(MOVE_LEFT, true);
        setFlag(MOVE_RIGHT, false);
    }

    /**
     * Moves the helicopter right this game tick;
     */
    protected final void moveRight()
    {
        setFlag(MOVE_RIGHT, true);
        setFlag(MOVE_LEFT, false);
    }

    /**
     * Turns the helicopter clockwise this game tick.
     */
    protected final void turnClockwise()
    {
        setFlag(TURN_CLOCK, true);
        setFlag(TURN_ANTICLOCK, false);
    }

    /**
     * Turns the helicopter anticlockwise this game tick.
     */
    protected final void turnAntiClockwise()
    {
        setFlag(TURN_ANTICLOCK, true);
        setFlag(TURN_CLOCK, false);
    }

    /**
     * Attempts to fire a projectile this game tick.
     */
    @Override
    public final void attack()
    {
        setFlag(ATTACK_ACTION, true);
    }

    @Override
    public final void aimClockwise()
    {
        setFlag(AIM_CLOCK, true);
        setFlag(AIM_ANTICLOCK, false);
    }

    @Override
    public final void aimAntiClockwise()
    {
        setFlag(AIM_ANTICLOCK, true);
        setFlag(AIM_CLOCK, false);
    }

    @Override
    public final void aimUp()
    {
        setFlag(AIM_UP, true);
        setFlag(AIM_DOWN, false);
    }

    @Override
    public final void aimDown()
    {
        setFlag(AIM_DOWN, true);
        setFlag(AIM_UP, false);
    }

    public final void target(VisibleBot target, float give)
    {
        RobotUtils.target(this, this, target, events, give);
    }
}
