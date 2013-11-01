/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
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
