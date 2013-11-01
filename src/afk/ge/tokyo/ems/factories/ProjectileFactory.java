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
import afk.ge.tokyo.ems.components.Bullet;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Velocity;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class ProjectileFactory implements Factory<ProjectileFactoryRequest>
{

    @Override
    public Entity create(ProjectileFactoryRequest request)
    {
        Entity entity = new Entity();
        State state = new State(request.pos, request.rot, request.scale);
        entity.addComponent(state);

        entity.addComponent(new Velocity(request.forward.multiply(request.weapon.speed), Vec4.VEC4_ZERO));
        entity.addComponent(new Renderable(request.type, request.colour, 1.0f));
        entity.addComponent(new Bullet(request.weapon.range, request.weapon.damage, request.parent));

        return entity;
    }
    
}
