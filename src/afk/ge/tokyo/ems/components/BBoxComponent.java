package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class BBoxComponent {
    
    public Vec3 extent = Vec3.VEC3_ZERO;
    public Vec3 offset = Vec3.VEC3_ZERO;

    public BBoxComponent()
    {
    }

    public BBoxComponent(Vec3 extent, Vec3 offset)
    {
        this.extent = extent;
        this.offset = offset;
    }
    
}
