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
    public abstract void run();
    
    /*
     * Methods bot actions
     * Declared as final to prevent overriding
     */
    protected final void move(int units) 
    {
        throw new UnsupportedOperationException();
    }
    
    protected final void turn(int degrees)
    {
        throw new UnsupportedOperationException();
    }
    
    protected final void attack()
    {
        throw new UnsupportedOperationException();
    }
}
