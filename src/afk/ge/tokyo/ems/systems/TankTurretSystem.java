package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TankTurretNode;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankTurretSystem implements ISystem
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
        List<TankTurretNode> nodes = engine.getNodeList(TankTurretNode.class);
        for (TankTurretNode node : nodes)
        {
            if (engine.getFlag(node.controller.id, AIM_CLOCK))
            {
                node.state.rot = node.state.rot.add(node.velocity.av);
            } else if (engine.getFlag(node.controller.id, AIM_ANTICLOCK))
            {
                node.state.rot = node.state.rot.add(node.velocity.av.getNegated());
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
