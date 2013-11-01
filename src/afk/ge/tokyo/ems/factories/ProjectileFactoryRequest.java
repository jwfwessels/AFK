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
 package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Weapon;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class ProjectileFactoryRequest implements FactoryRequest
{
    protected Vec3 pos;
    protected Vec4 rot;
    protected Vec3 scale;
    protected Vec3 forward;
    protected String type;
    protected Vec3 colour;
    protected Weapon weapon;
    protected Controller parent;

    public ProjectileFactoryRequest(Vec3 pos, Vec4 rot, Vec3 scale,
            Vec3 forward, String type, Vec3 colour, Weapon weapon,
            Controller parent)
    {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        this.forward = forward;
        this.type = type;
        this.colour = colour;
        this.weapon = weapon;
        this.parent = parent;
    }
    
    
    
    
}
