package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class SpinnyCamera
{
    public float angularVelocity = 0;
    public float pitch = 0;
    public float distance = 0;
    public Vec3 target = Vec3.VEC3_ZERO;

    public SpinnyCamera()
    {
    }

    public SpinnyCamera(float angularVelocity, float pitch, float distance, Vec3 target)
    {
        this.angularVelocity = angularVelocity;
        this.pitch = pitch;
        this.distance = distance;
        this.target = target;
    }
    
}
