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

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public abstract class AbstractCamera implements Camera
{
    
    /** Camera location. */
    public Vec3 eye;
    /** Point that the camera is looking at. */
    public Vec3 at;
    /** Up direction from the camera. */
    public Vec3 up;
           
    /** Matrix for OpenGL. */
    public Mat4 view = null, projection = null;
    
    /** The direction vector of the camera. Normalised. Do not modify. */
    public Vec3 dir;
    
    /** The right vector of the camera. Normalised. Do not modify. */
    public Vec3 right;
    
    /**
     * Creates a new camera object with the given initial properties.
     * @param eye Camera location.
     * @param at Point that the camera is looking at.
     * @param up Up direction from the camera.
     */
    public AbstractCamera(Vec3 eye, Vec3 at, Vec3 up)
    {
        this.eye = eye;
        this.at = at;
        this.up = up;
    }
    
    @Override
    public void updateView()
    {
        dir = at.subtract(eye).getUnitVector();
        right = dir.cross(up).getUnitVector();
        
        view = Matrices.lookAt(eye, at, up);
    }
    
    @Override
    public void moveForward(float amount)
    {
        Vec3 step = dir.multiply(amount);
        
        eye = eye.add(step);
        at = at.add(step);
    }
    
    @Override
    public void moveRight(float amount)
    {
        Vec3 step = right.multiply(amount);
        
        eye = eye.add(step);
        at = at.add(step);
    }
    
    @Override
    public void rotate(float lateral, float vertical)
    {
        Vec4 d = new Vec4(dir.getX(), dir.getY(), dir.getZ(), 0.0f);

        Mat4 rot = new Mat4(1.0f);
        rot = Matrices.rotate(rot, vertical, right);
        rot = Matrices.rotate(rot, lateral, up);

        d = rot.multiply(d);

        at = eye.add(new Vec3(d.getX(),d.getY(),d.getZ()));
    }
}
