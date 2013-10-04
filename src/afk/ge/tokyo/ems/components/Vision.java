package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Vision {
    
    public float dist = 0;
    public float fovy = 0;
    public float fovx = 0;

    public Vision()
    {
    }

    public Vision(float dist, float fovy, float fovx)
    {
        this.dist = dist;
        this.fovy = fovy;
        this.fovx = fovx;
    }
    
}
