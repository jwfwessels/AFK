/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.gfx.athens;

import afk.gfx.AbstractCamera;
import afk.gfx.PerspectiveCamera;
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
    
    private Mat4 createWorldMatrix(AbstractCamera camera)
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
    protected void draw(GL2 gl, AbstractCamera camera, Vec3 sun)
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
