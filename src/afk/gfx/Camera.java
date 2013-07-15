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
public abstract class Camera
{
    /** Camera location. */
    public Vec3 eye;
    /** Point that the camera is looking at. */
    public Vec3 at;
    /** Up direction from the camera. */
    public Vec3 up;
           
    /** Matrix for OpenGL. */
    public Mat4 view = null, projection = null;
    
    /** The direction vector of the camera. Normalised. Do not modify. */
    public Vec3 dir;
    
    /** The right vector of the camera. Normalised. Do not modify. */
    public Vec3 right;

    /**
     * Creates a new camera object with the given initial properties.
     * @param eye Camera location.
     * @param at Point that the camera is looking at.
     * @param up Up direction from the camera.
     * @param fovY Vertical field-of-view.
     * @param near Near clipping plane.
     * @param far Far clipping plane.
     */
    public Camera(Vec3 eye, Vec3 at, Vec3 up)
    {
        this.eye = eye;
        this.at = at;
        this.up = up;
    }
    
    /**
     * Call this when you need the view matrix updated. Also updates the
     * direction and right vectors.
     */
    public void updateView()
    {
        dir = at.subtract(eye).getUnitVector();
        right = dir.cross(up).getUnitVector();
        
        view = Matrices.lookAt(eye, at, up);
    }
    
    /**
     * Call this when you need thew projection matrix updated.
     * @param aspect The aspect ratio of the scene.
     */
    public abstract void updateProjection(float aspect);
    
    /**
     * Moves the camera forward.
     * @param amount The amount to move by. Use a negative number for backwards.
     */
    public void moveForward(float amount)
    {
        Vec3 step = dir.multiply(amount);
        
        eye = eye.add(step);
        at = at.add(step);
    }
    
    /**
     * Move the camera to the right.
     * @param amount The amount to move by. Use a negative number to move left.
     */
    public void moveRight(float amount)
    {
        Vec3 step = right.multiply(amount);
        
        eye = eye.add(step);
        at = at.add(step);
    }
    
    /**
     * Rotate the camera.
     * @param lateral The lateral (left-right) rotation.
     * @param vertical The vertical (up-down) rotation.
     */
    public void rotate(float lateral, float vertical)
    {
        Vec4 d = new Vec4(dir.getX(), dir.getY(), dir.getZ(), 0.0f);

        Mat4 rot = new Mat4(1.0f);
        rot = Matrices.rotate(rot, vertical, right);
        rot = Matrices.rotate(rot, lateral, up);

        d = rot.multiply(d);

        at = eye.add(new Vec3(d.getX(),d.getY(),d.getZ()));
    }
}
