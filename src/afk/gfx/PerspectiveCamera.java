package afk.gfx;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 * Represents a camera in a 3D scene. Handles all the movement and rotation of
 * the camera, as well as calculating the view and projection matrices for
 * OpenGL.
 * 
 * @author Daniel
 */
public class PerspectiveCamera extends AbstractCamera
{

    /** Vertical field-of-view. */
    public float fovY;
    /** Near clipping plane. */
    public float near;
    /** Far clipping plane. */
    public float far;

    /**
     * Creates a new camera object with the given initial properties.
     * @param eye Camera location.
     * @param at Point that the camera is looking at.
     * @param up Up direction from the camera.
     * @param fovY Vertical field-of-view.
     * @param near Near clipping plane.
     * @param far Far clipping plane.
     */
    public PerspectiveCamera(Vec3 eye, Vec3 at, Vec3 up, float fovY, float near, float far)
    {
        super(eye, at, up);
        this.fovY = fovY;
        this.near = near;
        this.far = far;
    }
    
    @Override
    public void updateProjection(float aspect)
    {
        projection = Matrices.perspective(fovY, aspect, near, far);
    }
}
