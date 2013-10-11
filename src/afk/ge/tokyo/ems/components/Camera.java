package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class Camera
{
    /** Camera location. */
    public Vec3 eye = Vec3.VEC3_ZERO;
    /** Point that the camera is looking at. */
    public Vec3 at = Vec3.VEC3_ZERO;
    /** Up direction from the camera. */
    public Vec3 up = Vec3.VEC3_ZERO;

    public Camera()
    {
    }

    public Camera(Vec3 eye, Vec3 at, Vec3 up)
    {
        this.eye = eye;
        this.at = at;
        this.up = up;
    }
    
    
}
