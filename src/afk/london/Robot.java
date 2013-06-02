/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

/**
 *
 * @author Jessica
 */
public abstract class Robot 
{
    //Method user will have to implement
    private final int NUM_ACTIONS = 3;
    
    //Index mapping of flag array
    private final int MOVE_ACTION = 0;
    private final int TURN_ACTION = 1;
    private final int ATTACK_ACTION = 2;  
    
    private int[] actionFlags;
    
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
        actionFlags = new int[NUM_ACTIONS];          
    }
    private void setFlag(int index, int value)
    {
        if(index <= NUM_ACTIONS && index >= 0)
        {
            actionFlags[index] = value;
        }
    }
    
    public int[] getFlags()
    {
        return actionFlags.clone();
    }
    
    protected final void move(int units) 
    {
        //throw new UnsupportedOperationException();
        setFlag(MOVE_ACTION, units);
    }
    
    protected final void turn(int degrees)
    {
        //throw new UnsupportedOperationException();
        setFlag(TURN_ACTION, degrees);
    }
    
    protected final void attack()
    {    
        //throw new UnsupportedOperationException();
        setFlag(ATTACK_ACTION, 1);
    }
    
    public void clearFlags()
    {
        for(int x = 0; x < NUM_ACTIONS; x++)
        {
            actionFlags[x] = 0;
        }
    }
}
