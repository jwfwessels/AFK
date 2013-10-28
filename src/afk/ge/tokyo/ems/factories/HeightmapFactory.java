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
import afk.ge.ems.FactoryException;
import afk.ge.tokyo.HeightmapLoader;
import afk.ge.tokyo.Tokyo;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class HeightmapFactory implements Factory<HeightmapFactoryRequest>
{

    @Override
    public Entity create(HeightmapFactoryRequest request)
    {
        Entity entity = new Entity();
        entity.addComponent(new State(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO,
                new Vec3(Tokyo.BOARD_SIZE)));
        entity.addComponent(new Renderable("floor", new Vec3(1.0f, 1.0f, 1.0f),1.0f));
        try
        {
            entity.addComponent(new HeightmapLoader().load(request.name, Tokyo.BOARD_SIZE, Tokyo.BOARD_SIZE, Tokyo.BOARD_SIZE));
        } catch (IOException ex)
        {
            throw new FactoryException(ex);
        }
        return entity;
    }
    
}
