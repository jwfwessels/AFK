package afk.gfx;

/**
 * An abstract adapter class for receiving graphics events. The methods in this
 * class are empty. This class exists as convenience for creating listener objects.
 * <br/>
 * Extend this class to create a graphics event listener and override the
 * methods for the events of interest. (If you implement the GfxListener
 * interface, you have to define all of the methods in it. This abstract class
 * defines null methods for them all, so you can only have to define methods for
 * events you care about.)
 * <br/>
 * Create a listener object using the extended class and then register it with a
 * graphics engine using the engines's addGfxListener method. When a graphics
 * event occurs, the relevant method in the listener object is invoked, and the
 * information is passed to it.
 * @author Daniel
 */
public class GfxAdapter implements GfxListener
{

    @Override
    public void mouseClicked(int x, int y, int button)
    {}

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y)
    {}

    @Override
    public void mousePressed(int x, int y, int button)
    {}

    @Override
    public void mouseReleased(int x, int y, int button)
    {}

    @Override
    public void keyPressed(int keyCode)
    {}

    @Override
    public void keyReleased(int keyCode)
    {}

    @Override
    public void update(float delta)
    {}
    
}
