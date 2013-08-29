package afk.gfx;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;

/**
 *
 * @author Daniel
 */
public class HUDCamera extends AbstractCamera
{
    public float ytop, ybottom, xleft, xright;

    public HUDCamera(float top, float bottom,
            float left, float right)
    {
        super(null, null, null);
        this.ytop = top;
        this.ybottom = bottom;
        this.xleft = left;
        this.xright = right;
    }

    @Override
    public void updateView()
    {
        view = Mat4.MAT4_IDENTITY;
    }
    

    @Override
    public void updateProjection(float aspect)
    {
        projection = Matrices.ortho2d(xleft, xright, ybottom, ytop);
    }
    
}
