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
public class Billboard extends AthensEntity
{
    private boolean spherical;

    protected Billboard(boolean spherical)
    {
        this.spherical = spherical;
    }
    
    private Mat4 createWorldMatrix(Camera camera)
    {
        Mat4 world = new Mat4(1f);

        world = Matrices.translate(world, new Vec3(xMove,yMove,zMove));
        
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
        
        if (spherical)
            world = Matrices.rotate(
                    world,
                    rotr,
                    camera.right);
        world = Matrices.rotate(
                world,
                roty,
                new Vec3(0,1,0));
        
        world = Matrices.scale(world, getScale());
        
        return world;
    }

    @Override
    protected void draw(GL2 gl, Camera camera, Vec3 sun)
    {
        if (!active) return;
        
        shader.use(gl);
        
        // TODO: figure out how to do texturing. May only allow single texture, but could allow multitexturing or bump/normal mapping later
        //tex.use(gl, GL.GL_TEXTURE0);
        //shader.updateUniform(gl, "tex", 0);
        
        shader.updateUniform(gl, "world", createWorldMatrix(camera));
        shader.updateUniform(gl, "view", camera.view);
        shader.updateUniform(gl, "projection", camera.projection);
        
        shader.updateUniform(gl, "sun", sun);
        shader.updateUniform(gl, "eye", camera.eye);
        
        if (colour != null)
            shader.updateUniform(gl, "colour", colour);
        
        mesh.draw(gl);
    }
    
}
