package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TurretNode;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class TurretFeedbackSystem implements ISystem
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
        List<TurretNode> nodes = engine.getNodeList(TurretNode.class);
        for (TurretNode node : nodes)
        {
            // it's negative because of some weird discrepancy between left and right and clock/anticlock
            // I don't know the details but this shit gives me a headache, so don't ask me about it
            node.controller.events.turret = -node.state.rot.getY();
        }
    }

    @Override
    public void destroy()
    {
    }
}
