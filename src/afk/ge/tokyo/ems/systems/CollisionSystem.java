package afk.ge.tokyo.ems.systems;

import afk.bot.london.RobotEvent;
import afk.ge.BBox;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.nodes.CollisionNode;
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
        
        for (CollisionNode nodeA : nodes)
        {
            // stop collision detection between static objects
            if (!nodeA.entity.has(Velocity.class)) continue;
            
            BBox boxA = new BBox(nodeA.state, nodeA.bbox.extent);
            for (CollisionNode nodeB : nodes)
            {
                if (nodeA == nodeB) continue;
                
                BBox boxB = new BBox(nodeB.state, nodeB.bbox.extent);
                
                if (boxA.isBoxInBox(boxB))
                {
                    Controller controller = nodeA.entity.get(Controller.class);
                    if (controller != null)
                    {
                        controller.events.hitWall = true;
                    }
                    controller = nodeB.entity.get(Controller.class);
                    if (controller != null)
                    {
                        controller.events.hitWall = true;
                    }
                }
            }
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
