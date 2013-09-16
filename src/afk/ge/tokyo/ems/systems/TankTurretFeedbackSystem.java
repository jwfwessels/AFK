package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TankTurretNode;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankTurretFeedbackSystem implements ISystem
{
    public static final int TURRET_AV = 3;

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
            System.out.println("Y: " + node.state.rot.getY());
            node.controller.events.turret = node.state.rot.getY();
        }
    }

    @Override
    public void destroy()
    {
    }
}
