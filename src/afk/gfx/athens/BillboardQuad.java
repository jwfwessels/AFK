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

import java.io.IOException;
import javax.media.opengl.GL2;

public class BillboardQuad extends Mesh
{
    public static final String NAME = "billboard";

    public BillboardQuad()
    {
        super(PRIMITIVE_MESH, NAME);
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        super.load(gl);
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(0, 0.5f, -0.5f);

                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(0, -0.5f, -0.5f);

                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(0, -0.5f, 0.5f);

                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(0, 0.5f, 0.5f);
            }
            gl.glEnd();
        }
        gl.glEndList();
        
        loaded.set(true);
    }
}
