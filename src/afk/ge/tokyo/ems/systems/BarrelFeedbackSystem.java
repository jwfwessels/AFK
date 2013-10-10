package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.BarrelNode;
import java.util.List;

/**
 *
 * @author Jw
 */
public class BarrelFeedbackSystem implements ISystem
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
        List<BarrelNode> nodes = engine.getNodeList(BarrelNode.class);
        for (BarrelNode node : nodes)
        {
            // the reason for the minus sign: it doesn't work if it isn't there,
            // so I put it there. so sue me...
            node.controller.events.barrel = -node.state.rot.getW();
        }
    }

    @Override
    public void destroy()
    {
    }
}
