package afk.ge.tokyo.ems.systems;

import afk.bot.london.RobotEvent;
import afk.ge.BBox;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import com.hackoeur.jglm.Vec3;
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
                
                // FIXME: bots check against each other twice
                if (boxA.isBoxInBox(boxB))
                {
                    nodeA.state.set(nodeA.state.prevPos, nodeA.state.prevRot, nodeA.state.prevScale);
                    Velocity v = nodeA.entity.get(Velocity.class);
                    v.v = v.a = v.av = v.aa = Vec3.VEC3_ZERO;
                    nodeB.state.set(nodeB.state.prevPos, nodeB.state.prevRot, nodeB.state.prevScale);
                    v = nodeB.entity.get(Velocity.class);
                    if (v != null)
                        v.v = v.a = v.av = v.aa = Vec3.VEC3_ZERO;
                    
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
