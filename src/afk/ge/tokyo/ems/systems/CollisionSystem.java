package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.MovementNode;
import static afk.ge.tokyo.ems.systems.MovementSystem.integrate;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class CollisionSystem implements ISystem
{
    Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<CollisionNode> nodes = engine.getNodeList(CollisionNode.class);
        
        for (CollisionNode node : nodes)
        {
            
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
