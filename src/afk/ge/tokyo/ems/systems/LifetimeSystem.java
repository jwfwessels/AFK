package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.LifetimeNode;
import java.util.List;

/**
 *
 * @author jwfwessels
 */
public class LifetimeSystem implements ISystem
{
    private Engine engine;

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
            node.lifetime.life -= dt;
            if (node.lifetime.life <= 0)
            {
                engine.removeEntity(node.entity);
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
