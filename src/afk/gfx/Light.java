package afk.gfx;

import afk.ge.tokyo.Tokyo;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author daniel
 */
public class Light
{
    private OrthoCamera camera;

    public Light(Vec3 point)
    {
        camera = new OrthoCamera(point, Vec3.VEC3_ZERO, new Vec3(0,1,0),
                0.1f, 100.0f,
                -50.0f, 50.0f,
                -50.0f, 50.0f
            );
        camera.updateView();
        trim();
        camera.updateProjection(1);
    }
    
    public void update(Vec3 point)
    {
        camera.eye = point;
        camera.updateView();
        trim();
        camera.updateProjection(1);
    }

    public Vec3 getPoint()
    {
        return camera.eye;
    }

    public Camera getCamera()
    {
        return camera;
    }
    
    private void trim()
    {
        
        final float SIZE = Tokyo.BOARD_SIZE*0.7f;
        final float HEIGHT = 10.0f;
        
        Vec4[] ps = {
            camera.view.multiply(new Vec4(camera.eye.getX()-SIZE,-HEIGHT,camera.eye.getZ()+SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()+SIZE,-HEIGHT,camera.eye.getZ()+SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()+SIZE,-HEIGHT,camera.eye.getZ()-SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()-SIZE,-HEIGHT,camera.eye.getZ()-SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()-SIZE,HEIGHT,camera.eye.getZ()+SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()+SIZE,HEIGHT,camera.eye.getZ()+SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()+SIZE,HEIGHT,camera.eye.getZ()-SIZE,1)),
            camera.view.multiply(new Vec4(camera.eye.getX()-SIZE,HEIGHT,camera.eye.getZ()-SIZE,1))
        };  
        
        Vec4[] ps2 = {
            camera.view.multiply(new Vec4(-SIZE,-HEIGHT,SIZE,1)),
            camera.view.multiply(new Vec4(SIZE,-HEIGHT,SIZE,1)),
            camera.view.multiply(new Vec4(SIZE,-HEIGHT,-SIZE,1)),
            camera.view.multiply(new Vec4(-SIZE,-HEIGHT,-SIZE,1)),
            camera.view.multiply(new Vec4(-SIZE,HEIGHT,SIZE,1)),
            camera.view.multiply(new Vec4(SIZE,HEIGHT,SIZE,1)),
            camera.view.multiply(new Vec4(SIZE,HEIGHT,-SIZE,1)),
            camera.view.multiply(new Vec4(-SIZE,HEIGHT,-SIZE,1))
        };
        
        float left = ps2[0].getX(),
                right = ps2[0].getX(),
                top = ps2[0].getY(),
                bottom = ps2[0].getY(),
                near = -ps2[0].getZ(),
                far = -ps2[0].getZ();
        
        for (int i = 1; i < ps.length; i++)
        {
            left = Math.min(left, ps2[i].getX());
            right = Math.max(right, ps2[i].getX());
            top = Math.max(top, ps2[i].getY());
            bottom = Math.min(bottom, ps2[i].getY());
            near = Math.min(near, -ps2[i].getZ());
            far = Math.max(far, -ps2[i].getZ());
        }
        
        left += 10; right-=10; top -= 10; bottom += 10;
        
        float Sx = 2.0f/(right-left),
                Sy = 2.0f/(top-bottom),
                Ox = -0.5f*(right+left)*Sx,
                Oy = -0.5f*(top+bottom)*Sy;
        
        camera.xleft = left;
        camera.xright = right;
        camera.ybottom = bottom;
        camera.ytop = top;
        camera.znear = near;
        camera.zfar = far;
    }
    
}
