package afk.ge.tokyo.ems.components;

import java.awt.image.BufferedImage;

/**
 *
 * @author Daniel
 */
public class Heightmap
{
    public BufferedImage heightmap = null;
    
    public float width = 0, length = 0, height = 0;
    public int xGrid = 0, yGrid = 0;
    
    public float[][][] vertices = null;
    public float[][][] normals = null;

    public Heightmap()
    {
    }
    
}
