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
 package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.factories.ParticleFactory;
import afk.ge.tokyo.ems.factories.ParticleFactoryRequest;
import afk.ge.tokyo.ems.nodes.ParticleEmitterNode;
import java.util.List;

/**
 *
 * @author jwfwessels
 */
public class ParticleSystem implements ISystem
{

    private Engine engine;
    private ParticleFactory factory = new ParticleFactory();

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<ParticleEmitterNode> enodes = engine.getNodeList(ParticleEmitterNode.class);

        for (ParticleEmitterNode enode : enodes)
        {
            if (enode.emitter.spawnInterval == 0)
            {
                for (int i = 0; i < enode.emitter.numParticles; i++)
                {
                    engine.addEntity(factory.create(new ParticleFactoryRequest(enode.emitter, enode.state)));
                }
                engine.removeEntity(enode.entity);
            } else
            {
                enode.emitter.timeSinceLastSpawn += dt;
                if (enode.emitter.timeSinceLastSpawn >= enode.emitter.spawnInterval)
                {
                    engine.addEntity(factory.create(new ParticleFactoryRequest(enode.emitter, enode.state)));
                }
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
