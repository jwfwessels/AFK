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
    
    
    /** Vertical field-of-view. */
    public float fovY = 0;
    /** Near clipping plane. */
    public float near = 0;
    /** Far clipping plane. */
    public float far = 0;

    public Camera()
    {
    }

    public Camera(Vec3 eye, Vec3 at, Vec3 up, float fovY, float near, float far)
    {
        this.eye = eye;
        this.at = at;
        this.up = up;
        this.fovY = fovY;
        this.near = near;
        this.far = far;
    }
    
    
}
