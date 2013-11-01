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

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class ObstacleFactory implements Factory<ObstacleFactoryRequest>
{

    @Override
    public Entity create(ObstacleFactoryRequest request)
    {
        Entity entity = new Entity();
        entity.addComponent(new State(request.pos, Vec4.VEC4_ZERO, request.scale));
        entity.addComponent(new BBoxComponent(new Vec3(0.5f), new Vec3(0, 0.5f, 0)));
        if (request.visible)
        {
            entity.addComponent(new Renderable(request.type, new Vec3(0.75f, 0.75f, 0.75f), 1.0f));
        }

        return entity;
    }
}
