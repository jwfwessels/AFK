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
 package afk.gfx;

import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class OrthoCamera extends AbstractCamera
{
    public float ytop, ybottom, xleft, xright, znear, zfar;

    public OrthoCamera(Vec3 eye, Vec3 at, Vec3 up, float top, float bottom,
            float left, float right, float near, float far)
    {
        super(eye, at, up);
        this.ytop = top;
        this.ybottom = bottom;
        this.xleft = left;
        this.xright = right;
        this.znear = near;
        this.zfar = far;
    }

    @Override
    public void updateProjection(float w, float h)
    {
        projection = Matrices.ortho(xleft, xright, ybottom, ytop, znear, zfar);
    }
    
}
