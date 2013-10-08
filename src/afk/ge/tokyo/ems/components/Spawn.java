package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class Spawn
{
    public Vec3 pos = Vec3.VEC3_ZERO;
    public Vec4 rot = Vec4.VEC4_ZERO;

    public Spawn()
    {
    }
    
    public Spawn(Vec3 pos, Vec4 rot)
    {
        this.pos = pos;
        this.rot = rot;
    }
    
}
