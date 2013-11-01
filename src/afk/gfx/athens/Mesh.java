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

/**
 * Abstract Mesh class. Stores a displaylist in a handle.
 * @author daniel
 */
public abstract class Mesh extends AthensResource
{
    protected int handle;
    
    public static final int VERTICES_PER_TRIANGLE = 3;
    public static final int TRIANGLES_PER_QUAD = 2;
    public static final int VERTICES_PER_QUAD = VERTICES_PER_TRIANGLE * TRIANGLES_PER_QUAD;
    public static final int DIMESIONS = 3;

    public Mesh(int type, String name)
    {
        super(type,name);
    }
    
    public void draw(GL2 gl)
    {
       gl.glCallList(handle);
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        handle = gl.glGenLists(1);
    }
    
    @Override
    public void unload(GL2 gl)
    {
        loaded.set(false);
        gl.glDeleteLists(handle, 1);
    }
}
