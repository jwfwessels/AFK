package afk.ge.tokyo.ems.components;

/**
 *
 * @author daniel
 */
public class Vision {
    
    public float dist;
    public float fovy;
    public float fovx;

    public Vision(float dist, float fovy, float fovx)
    {
        this.dist = dist;
        this.fovy = fovy;
        this.fovx = fovx;
    }
    
}
