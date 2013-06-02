/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

/**
 * @author Jessica
 */
public abstract class Robot
{
    //Method user will have to implement

    private final int NUM_ACTIONS = 5;
    //Index mapping of flag array
    private final int MOVE_FRONT = 0;
    private final int MOVE_BACK = 1;
    private final int TURN_CLOCK = 2;
    private final int TURN_ANTICLOCK = 3;
    private final int ATTACK_ACTION = 4;
    private boolean[] actionFlags;
    private boolean[] eventFlags;

    /*
     * Abstract method that will be implemented by user
     */
    public abstract void run();

    /*
     * Methods bot actions
     * Declared as final to prevent overriding
     */
    public Robot()
    {
        actionFlags = new boolean[NUM_ACTIONS];
    }

    private void setFlag(int index)
    {
        if (index <= NUM_ACTIONS && index >= 0)
        {
            actionFlags[index] = true;
        }
    }

    public boolean[] getActionFlags()
    {
        return actionFlags;
    }

    protected final void moveForward()
    {
        //throw new UnsupportedOperationException();
        setFlag(MOVE_FRONT);
    }

    protected final void moveBackwards()
    {
        //throw new UnsupportedOperationException();
        setFlag(MOVE_BACK);
    }

    protected final void turnClockwise()
    {
        //throw new UnsupportedOperationException();
        setFlag(TURN_CLOCK);
    }

    protected final void turnAntiClockwise()
    {
        //throw new UnsupportedOperationException();
        setFlag(TURN_ANTICLOCK);
    }
    protected final void attack()
    {    
        //throw new UnsupportedOperationException();
        setFlag(ATTACK_ACTION);
    }
    public void clearFlags()
    {
        for(int x = 0; x < NUM_ACTIONS; x++)
        {
            actionFlags[x] = false;
        }
    }
}
