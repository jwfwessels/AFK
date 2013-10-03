/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens;

import afk.gfx.Camera;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class BillboardEntity extends AthensEntity
{
    private boolean spherical;

    protected BillboardEntity(boolean spherical)
    {
        this.spherical = spherical;
    }
    
    private Mat4 createWorldMatrix(Camera camera)
    {
        Mat4 world = new Mat4(1f);

        world = Matrices.translate(world, getWorldPosition());
        
        float x = camera.dir.getX();
        float y = camera.dir.getY();
        float z = camera.dir.getZ();
        
        float roty, rotr;
        
        if (z >= 0)
            roty = -(float)FastMath.toDegrees(FastMath.atan2(z, x));
        else
            roty = (float)FastMath.toDegrees(FastMath.atan2(-z, x));
        
        if (y >= 0)
            rotr = -(float)FastMath.toDegrees(FastMath.atan2(-y, FastMath.sqrtFast(x*x+z*z)));
        else
            rotr = (float)FastMath.toDegrees(FastMath.atan2(y, FastMath.sqrtFast(x*x+z*z)));
        
        // TODO: zRot must have an effect on the rotation of the billboard
        
        if (spherical)
            world = Matrices.rotate(
                    world,
                    rotr,
                    camera.right);
        world = Matrices.rotate(
                world,
                roty,
                new Vec3(0,1,0));
        
        world = Matrices.scale(world, getWorldScale());
        
        return world;
    }

    @Override
    protected void draw(GL2 gl, Camera camera, Vec3 sun)
    {
        if (!active) return;
        
        worldMatrix = createWorldMatrix(camera);
        
        if (shader != null)
        {
            shader.use(gl);

            if (texture != null)
            {
                texture.use(gl, GL2.GL_TEXTURE0);
                shader.updateUniform(gl, "tex", 0);
            }

            shader.updateUniform(gl, "world", worldMatrix);
            shader.updateUniform(gl, "view", camera.view);
            shader.updateUniform(gl, "projection", camera.projection);

            shader.updateUniform(gl, "sun", sun);
            shader.updateUniform(gl, "eye", camera.eye);
            
            shader.updateUniform(gl, "life", life);

            if (colour != null)
                shader.updateUniform(gl, "colour", colour);
            
            shader.updateUniform(gl, "opacity", opacity);
        }
        
        if (mesh != null)
            mesh.draw(gl);
    }
    
}
