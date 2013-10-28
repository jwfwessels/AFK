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

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.TerrainDisplacement;
import afk.gfx.athens.TypeFactory;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import static java.lang.Math.*;

/**
 *
 * @author daniel
 */
public class BoulderFactory implements Factory<BoulderFactoryRequest>
{

    @Override
    public Entity create(BoulderFactoryRequest request)
    {
        Entity entity = new Entity();
        
        float rangeX = request.maxX - request.minX;
        float rangeZ = request.maxZ - request.minZ;
        
        boolean ok = false;
        
        float x = 0;
        float z = 0;
        
        while (!ok)
        {
            x = (float)(random()*rangeX+request.minX);
            z = (float)(random()*rangeZ+request.minZ);

            float avoidSq = request.avoidance*request.avoidance;

            ok = true;
            for (int i = 0; i < request.avoidPoints.length && ok; i++)
            {
                float dx = x-request.avoidPoints[i].getX();
                float dy = z-request.avoidPoints[i].getZ();
                
                if (dx*dx+dy*dy < avoidSq)
                {
                    ok = false;
                }
            }
        }
        
        float yRot = (float)random()*360.0f;
        
        float rangeScale = request.maxScale - request.minScale;
        float scale = (float)random()*rangeScale+request.minScale;
        
        float xRot = (float)random()*request.tiltAmount-request.tiltAmount/2;
        float zRot = (float)random()*request.tiltAmount-request.tiltAmount/2;
        
        entity.addComponent(new State(
                new Vec3(x,0,z),
                new Vec4(xRot, yRot, zRot, 0),
                new Vec3(scale)));
        
        entity.addComponent(new TerrainDisplacement(-request.groundSink));
        entity.addComponent(new BBoxComponent(new Vec3(0.5f), new Vec3(0, 0.5f, 0)));
        int type = (int)(random()* TypeFactory.NUM_BOULDER_TYPES);
        entity.addComponent(new Renderable("boulder_"+type, new Vec3(0.75f, 0.75f, 0.75f), 1.0f));
        
        return entity;
    }
    
}
