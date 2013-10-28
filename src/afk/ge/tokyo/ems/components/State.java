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
 package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author daniel
 */
public class State
{

    /// PREVIOUS STATE ///
    public Vec3 prevPos = Vec3.VEC3_ZERO;
    public Vec4 prevRot = Vec4.VEC4_ZERO;
    public Vec3 prevScale = new Vec3(1,1,1);
    /// CURRENT STATE ///
    public Vec3 pos = Vec3.VEC3_ZERO;
    public Vec4 rot = Vec4.VEC4_ZERO;
    public Vec3 scale = new Vec3(1,1,1);

    public State()
    {
    }

    public State(State original, Vec3 posShift)
    {
        this.prevPos = original.pos.add(posShift);
        this.prevRot = original.rot;
        this.prevScale = original.scale;
        this.pos = original.pos.add(posShift);
        this.rot = original.rot;
        this.scale = original.scale;
    }

    public State(Vec3 pos, Vec4 rot, Vec3 scale)
    {
        this.prevPos = pos;
        this.prevRot = rot;
        this.prevScale = scale;
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }

    public void setPrev(Vec3 prevPos, Vec4 prevRot, Vec3 prevScale)
    {
        this.prevPos = prevPos;
        this.prevRot = prevRot;
        this.prevScale = prevScale;
    }
    
    public void set(Vec3 pos, Vec4 rot, Vec3 scale)
    {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }
    
    public void reset(Vec3 pos, Vec4 rot, Vec3 scale)
    {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        this.prevPos = pos;
        this.prevRot = rot;
        this.prevScale = scale;
    }
}
