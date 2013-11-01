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
import afk.ge.ems.Pool;
import afk.ge.tokyo.ems.components.Lifetime;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Velocity;
import static afk.gfx.GfxUtils.ANCHOR;
import static afk.gfx.GfxUtils.X_AXIS;
import static afk.gfx.GfxUtils.Y_AXIS;
import static afk.gfx.GfxUtils.Z_AXIS;
import static afk.gfx.GfxUtils.jitter;
import static afk.gfx.GfxUtils.random;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.ArrayDeque;
import java.util.Queue;

/**
 *
 * @author Daniel
 */
public class ParticleFactory implements Factory<ParticleFactoryRequest>, Pool
{

    private Queue<Entity> pool = new ArrayDeque<Entity>();

    @Override
    public Entity create(ParticleFactoryRequest request)
    {
        Entity pie = getEntity();

        State state = pie.getComponent(State.class);
        Velocity velocity = pie.getComponent(Velocity.class);
        Lifetime lifetime = pie.getComponent(Lifetime.class);
        Renderable renderable = pie.getComponent(Renderable.class);

        Vec3 pos = request.state.pos;
        Vec4 rot = request.state.rot;
        Vec3 scale = request.state.scale;

        state.reset(new Vec3(
                jitter(pos.getX(), scale.getX()),
                jitter(pos.getY(), scale.getY()),
                jitter(pos.getZ(), scale.getZ())),
                Vec4.VEC4_ZERO,
                new Vec3(jitter(request.emitter.scale, request.emitter.scaleJitter)));

        Vec3 dir;
        if (request.emitter.noDirection)
        {
            // uniform sphere distribution
            dir = new Vec3(
                    (float) random.nextGaussian(),
                    (float) random.nextGaussian(),
                    (float) random.nextGaussian())
                    .getUnitVector();
        } else
        {
            // uniform cone distribution (I think)
            Mat4 rotation = Matrices.rotate(new Mat4(1.0f), jitter(rot.getX(), request.emitter.angleJitter.getX()), X_AXIS);
            rotation = Matrices.rotate(rotation, jitter(rot.getY(), request.emitter.angleJitter.getY()), Y_AXIS);
            rotation = Matrices.rotate(rotation, jitter(rot.getZ(), request.emitter.angleJitter.getZ()), Z_AXIS);

            Vec4 newRotation = rotation.multiply(ANCHOR);

            dir = new Vec3(newRotation.getX(), newRotation.getY(), newRotation.getZ());
        }

        float speed = jitter(request.emitter.speed, request.emitter.speedJitter);

        velocity.v = dir.scale(speed);
        velocity.a = request.emitter.acceleration;

        lifetime.life = jitter(request.emitter.maxLife, request.emitter.lifeJitter);
        lifetime.maxLife = lifetime.life;

        renderable.colour = request.emitter.colour;

        return pie;
    }

    @Override
    public Entity getEntity()
    {
        Entity pie = pool.poll();
        if (pie == null)
        {
            pie = new Entity("particle", this);
            pie.addComponent(new State(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO, Vec3.VEC3_ZERO));
            pie.addComponent(new Lifetime(0));
            pie.addComponent(new Velocity(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO));
            pie.addComponent(new Renderable("particle", Vec3.VEC3_ZERO, 1.0f));
        }
        return pie;
    }

    @Override
    public void returnEntity(Entity entity)
    {
        pool.add(entity);
    }
}
