package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class HUD
{
    public Integer top = null;
    public Integer right = null;
    public Integer bottom = null;
    public Integer left = null;

    public HUD()
    {
    }

    public HUD(Integer top, Integer right, Integer bottom, Integer left)
    {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }
    
}
