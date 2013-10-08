package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author daniel
 */
public class Velocity {
    
    public Vec3 v = Vec3.VEC3_ZERO; // velocity
    public Vec3 a = Vec3.VEC3_ZERO; // acceleration
    public Vec4 av = Vec4.VEC4_ZERO; // angular velocity
    public Vec4 aa = Vec4.VEC4_ZERO; // angular acceleration

    public Velocity()
    {
    }

    public Velocity(Vec3 v, Vec4 av)
    {
        this.v = v;
        this.av = av;
        a = Vec3.VEC3_ZERO;
        aa = Vec4.VEC4_ZERO;
    }
    
}
