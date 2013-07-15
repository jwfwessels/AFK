/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx;

import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class PerspectiveCamera extends Camera
{
    /** Vertical field-of-view. */
    public float fovY;
    /** Near clipping plane. */
    public float near;
    /** Far clipping plane. */
    public float far;

    public PerspectiveCamera(
            Vec3 eye, Vec3 at, Vec3 up,
            float fovY, float near, float far)
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
