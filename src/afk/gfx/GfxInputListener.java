package afk.gfx;

/**
 *
 * @author daniel
 */
public interface GfxInputListener
{
    
    public void mouseClicked(int x, int y, int button);
    
    public void mouseMoved(int oldx, int oldy, int x, int y);
    
    public void mousePressed(int x, int y, int button);
    
    public void mouseReleased(int x, int y, int button);
    
    public void keyPressed(int keyCode);
    
    public void keyReleased(int keyCode);
    
}
