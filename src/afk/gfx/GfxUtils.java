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
 package afk.gfx;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class GfxUtils
{
    public static final Random random = new Random();
    
    // direction vector to be rotated
    // represents a rotation of 0, 0, 0 degrees
    // TODO: this whole thing should probably be sorted out with quaternions :/
    public static final Vec4 ANCHOR = new Vec4(1.0f,0.0f,0.0f,0.0f);
    
    public static final Vec3 X_AXIS = new Vec3(1,0,0);
    public static final Vec3 Y_AXIS = new Vec3(0,1,0);
    public static final Vec3 Z_AXIS = new Vec3(0,0,1);
    
    public static final long NANOS_PER_SECOND = 1000000000l;
    
    
    public static float jitter(float x, float j)
    {
        return randomLerp(x-j, x+j);
    }
    
    public static float gaussJitter(float x, float j)
    {
        return x + (float)random.nextGaussian()*j;
    }
    
    public static float randomLerp(float a, float b)
    {
        return a + (b - a) * random.nextFloat();
    }
}
