package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class Velocity {
    
    public Vec3 v; // velocity
    public Vec3 a; // acceleration
    public Vec3 av; // angular velocity
    public Vec3 aa; // angular acceleration

    public Velocity(Vec3 v, Vec3 av)
    {
        this.v = v;
        this.av = av;
        a = Vec3.VEC3_ZERO;
        aa = Vec3.VEC3_ZERO;
    }
    
}
