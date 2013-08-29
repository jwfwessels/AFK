package afk.gfx;

import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class OrthoCamera extends AbstractCamera
{
    public float ytop, ybottom, xleft, xright, znear, zfar;

    public OrthoCamera(Vec3 eye, Vec3 at, Vec3 up, float top, float bottom,
            float left, float right, float near, float far)
    {
        super(eye, at, up);
        this.ytop = top;
        this.ybottom = bottom;
        this.xleft = left;
        this.xright = right;
        this.znear = near;
        this.zfar = far;
    }

    @Override
    public void updateProjection(float w, float h)
    {
        projection = Matrices.ortho(xleft, xright, ybottom, ytop, znear, zfar);
    }
    
}
