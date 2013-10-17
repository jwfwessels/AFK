package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class Sonar
{
    public Vec3 min = Vec3.VEC3_ZERO;
    public Vec3 max = Vec3.VEC3_ZERO;
    public Vec3 offset = Vec3.VEC3_ZERO;

    public Sonar()
    {
    }

    public Sonar(Vec3 min, Vec3 max, Vec3 offset)
    {
        this.min = min;
        this.max = max;
        this.offset = offset;
    }
    
}
