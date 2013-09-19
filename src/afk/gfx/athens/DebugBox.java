/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens;

import afk.gfx.Camera;
import com.hackoeur.jglm.Vec3;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class DebugBox extends AthensEntity
{
    private Vec3 v;
    
    float[][][] t = {
        {{1,1,1},{1,1,-1},{-1,1,-1},{-1,1,1}},
        {{-1,-1,1},{-1,1,1},{1,1,1},{1,-1,1}},
        {{-1,-1,-1},{-1,-1,1},{1,-1,1},{1,-1,-1}},
        {{-1,1,-1},{-1,-1,-1},{1,-1,-1},{1,1,-1}}
    };

    public void setV(Vec3 v)
    {
        this.v = v;
    }

    @Override
    protected void draw(GL2 gl, Camera camera, Vec3 sun)
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
        
        for (int i = 0; i < t.length; i++)
        {
            gl.glBegin(GL.GL_LINE_STRIP);
            {
                for (int j = 0; j < t[i].length; j++)
                {
                    gl.glVertex3f(v.getX()*t[i][j][0],v.getY()*t[i][j][1],v.getZ()*t[i][j][2]);
                }
            }
            gl.glEnd();
        }

        if (opacity < 1.0f)
        {
            gl.glDisable(GL.GL_BLEND);
        }
    }
    
}
