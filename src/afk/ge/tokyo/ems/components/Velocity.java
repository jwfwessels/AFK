package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class Velocity {
    
    public Vec3 v;
    public Vec3 av;

    public Velocity()
    {
        this.v = Vec3.VEC3_ZERO;
        this.av = Vec3.VEC3_ZERO;
    }
    
}
