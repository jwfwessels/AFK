/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens;

import afk.gfx.AbstractCamera;
import com.hackoeur.jglm.Vec3;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author daniel
 */
public class DebugLine extends AthensEntity
{
    private Vec3[] line;

    public void set(Vec3[] line)
    {
        this.line = line;
    }

    @Override
    protected void draw(GL2 gl, AbstractCamera camera, Vec3 sun)
    {
        // by default, active sets visibility of entity
        if (!active)
        {
            return;
        }

        worldMatrix = createWorldMatrix();

        if (shader != null)
        {
            shader.use(gl); 
            shader.updateUniform(gl, "world", worldMatrix);
            shader.updateUniform(gl, "view", camera.view);
            shader.updateUniform(gl, "projection", camera.projection);

            if (colour != null)
            {
                shader.updateUniform(gl, "colour", colour);
            }

            shader.updateUniform(gl, "opacity", opacity);
        }

        if (opacity < 1.0f)
        {
            gl.glEnable(GL.GL_BLEND);
        }
        
        gl.glBegin(GL.GL_LINE_STRIP);
        {
            for (int i = 0; i < line.length; i++)
            {
                gl.glVertex3f(line[i].getX(),line[i].getY(),line[i].getZ());
            }
        }
        gl.glEnd();

        if (opacity < 1.0f)
        {
            gl.glDisable(GL.GL_BLEND);
        }
    }
}
