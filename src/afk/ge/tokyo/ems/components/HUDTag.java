package afk.ge.tokyo.ems.components;

/**
 *
 * @author Daniel
 */
public class HUDTag
{
    public float x = 0;
    public float y = 0;
    
    public boolean centerX = false;
    public boolean centerY = false;

    public HUDTag()
    {
    }

    public HUDTag(float x, float y, boolean centerX, boolean centerY)
    {
        this.x = x;
        this.y = y;
        this.centerX = centerX;
        this.centerY = centerY;
    }
}
