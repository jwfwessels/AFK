package afk.gfx;

/**
 * The listener interface for receiving graphics events (input events, updates
 * etc).
 * The class that is interested in processing a graphics event either implements
 * this interface (and all the methods it contains) or extends the abstract
 * GfxEventAdapter class (overriding only the methods of interest).
 * <br/>
 * The listener object created from that class is then registered with a
 * graphics engine using the engine's addGfxListener method. When a graphics
 * event occurs, the relevant method in the listener object is then invoked, and
 * the information is passed to it.
 * @author Daniel
 */
public interface GfxListener
{
    public void mouseClicked(int x, int y, int button);
    
    public void mouseMoved(int oldx, int oldy, int x, int y);
    
    public void mousePressed(int x, int y, int button);
    
    public void mouseReleased(int x, int y, int button);
    
    public void keyPressed(int keyCode);
    
    public void keyReleased(int keyCode);
    
    /**
     * Called whenever a graphics update occurs. This is useful if the graphics
     * engine is set to autodraw and you want to use the graphics engine's
     * update loop to do your own updates.
     * @param delta The number of seconds since the last update.
     */
    public void update(float delta);
    
}