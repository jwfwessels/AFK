package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.EntityManager;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.factories.ParticleFactory;
import afk.ge.tokyo.ems.factories.ParticleFactoryRequest;
import afk.ge.tokyo.ems.nodes.ParticleEmitterNode;
import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author jwfwessels
 */
public class ParticleSystem implements ISystem
{

    private EntityManager manager;
    private Engine engine;
    private ParticleFactory factory = new ParticleFactory();

    public ParticleSystem(EntityManager manager)
    {
        this.manager = manager;
    }

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
