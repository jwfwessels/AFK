package afk.gfx;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class Light
{
    private Camera camera;
    private Vec3 point;

    public Light(Vec3 point)
    {
        this.point = point;
        camera = new OrthoCamera(point, Vec3.VEC3_ZERO, new Vec3(0,1,0),
                0.1f, 100.0f,
                -50.0f, 50.0f,
                -50.0f, 50.0f
            );
        camera.updateProjection(1);
        camera.updateView();
    }
    
    public void update(Vec3 point)
    {
        this.point = point;
        camera.at = point;
        camera.updateProjection(1);
        camera.updateView();
    }

    public Vec3 getPoint()
    {
        return point;
    }

    public Camera getCamera()
    {
        return camera;
    }
    
}
