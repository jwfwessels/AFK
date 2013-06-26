package afk.gfx.athens;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class Billboard extends AthensEntity
{

    public Billboard()
    {
        
    }

    @Override
    protected void draw(GL2 gl, Mat4 camera, Mat4 proj, Vec3 sun, Vec3 eye)
    {
        Mat4 newCamera = new Mat4(
                new Vec4(1, 0, 0, camera.<Vec4>getColumn(0).getW()),
                new Vec4(0, 1, 0, camera.<Vec4>getColumn(1).getW()),
                new Vec4(0, 0, 1, camera.<Vec4>getColumn(2).getW()),
                camera.<Vec4>getColumn(3)
            );
        
        super.draw(gl, newCamera, proj, sun, eye);
    }
}
