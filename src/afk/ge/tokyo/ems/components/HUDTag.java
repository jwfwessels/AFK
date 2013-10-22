package afk.ge.tokyo.ems.components;

/**
 *
 * @author Daniel
 */
public class HUDTag
{
    public int xOffset = 0;
    public int minY = 0;
    public float worldY = 0;
    
    public boolean centerX = false;
    public boolean centerY = false;

    public HUDTag()
    {
    }

    public HUDTag(int xOffset, int minY, float worldY, boolean centerX, boolean centerY)
    {
        this.xOffset = xOffset;
        this.minY = minY;
        this.worldY = worldY;
        this.centerX = centerX;
        this.centerY = centerY;
    }
}
