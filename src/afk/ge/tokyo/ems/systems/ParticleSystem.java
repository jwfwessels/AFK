package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.Entity;
import afk.ge.tokyo.ems.ISystem;
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
    EntityManager manager;
    Engine engine;
    
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
                            manager.makePie(enode.state, enode.emitter);
			engine.removeEntity(enode.entity);
		}
		else
		{
			enode.emitter.timeSinceLastSpawn += dt;
			if (enode.emitter.timeSinceLastSpawn >= enode.emitter.spawnInterval)
                            manager.makePie(enode.state, enode.emitter);
		}
	}
    }

    @Override
    public void destroy()
    {
    }
    
}
