package afk.ge.tokyo.ems.components;

import java.awt.image.BufferedImage;

/**
 *
 * @author Daniel
 */
public class ImageComponent
{
    private BufferedImage image;
    private boolean updated;

    public ImageComponent(BufferedImage image)
    {
        this.image = image;
    }

    public void setImage(BufferedImage image)
    {
        this.image = image;
        updated = true;
    }

    public BufferedImage getImage()
    {
        return image;
    }

    public boolean isUpdated()
    {
        return updated;
    }

    public void setUpdated(boolean updated)
    {
        this.updated = updated;
    }
    
}
