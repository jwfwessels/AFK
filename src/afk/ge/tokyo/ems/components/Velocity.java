package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class Velocity {
    
    public Vec3 v;
    public Vec3 av;

    public Velocity(Vec3 v, Vec3 av)
    {
        this.v = v;
        this.av = av;
    }
    
}
