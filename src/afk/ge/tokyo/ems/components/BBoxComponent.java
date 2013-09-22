package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class BBoxComponent {
    
    public Vec3 extent;
    public Vec3 offset;

    public BBoxComponent(Vec3 extent, Vec3 offset)
    {
        this.extent = extent;
        this.offset = offset;
    }
    
}
