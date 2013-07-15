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
public class OrthoCamera extends Camera
{
    /** Near clipping plane. */
    public float znear;
    /** Far clipping plane. */
    public float zfar;
    /** Left clipping plane. */
    public float xleft;
    /** Right clipping plane. */
    public float xright;
    /** Bottom clipping plane. */
    public float ybottom;
    /** Top clipping plane. */
    public float ytop;

    public OrthoCamera(
            Vec3 eye, Vec3 at, Vec3 up,
            float znear, float zfar,
            float xleft, float xright,
            float ybottom, float ytop)
    {
        super(eye, at, up);
        this.znear = znear;
        this.zfar = zfar;
        this.xleft = xleft;
        this.xright = xright;
        this.ybottom = ybottom;
        this.ytop = ytop;
    }

    @Override
    public void updateProjection(float aspect)
    {
        projection = Matrices.ortho(xleft, xright, ybottom, ytop, znear, zfar);
    }
    
}
