package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.LifetimeNode;
import java.util.List;

/**
 *
 * @author jwfwessels
 */
public class LifetimeSystem implements ISystem
{
    Engine engine;
    EntityManager manager;
    
    public LifetimeSystem(EntityManager manager)
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
        List<LifetimeNode> nodes = engine.getNodeList(LifetimeNode.class);
        
        for (LifetimeNode node : nodes)
        {
            node.lifetime.life += dt;
            if (node.lifetime.life >= node.lifetime.maxLife)
            {
                engine.removeEntity(node.entity);
                
                // FIXME: this must go somewhere else 
                manager.recyclePie(node.entity);
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
