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
 package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class ObstacleFactoryRequest implements FactoryRequest
{
    protected Vec3 pos;
    protected Vec3 scale;
    protected String type;
    protected boolean visible;
    
    public ObstacleFactoryRequest(Vec3 pos, Vec3 scale, String type)
    {
        this(pos,scale,type,true);
    }

    public ObstacleFactoryRequest(Vec3 pos, Vec3 scale, String type, boolean visible)
    {
        this.pos = pos;
        this.scale = scale;
        this.type = type;
        this.visible = visible;
    }
    
    
}
