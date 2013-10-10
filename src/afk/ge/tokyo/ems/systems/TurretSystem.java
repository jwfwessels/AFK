package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TurretNode;
import com.hackoeur.jglm.Vec4;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TurretSystem implements ISystem
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
            if (engine.getFlag(node.controller.id, AIM_CLOCK))
            {
                node.velocity.av = new Vec4(0,-node.turret.angularVelocity,0,0);
            } else if (engine.getFlag(node.controller.id, AIM_ANTICLOCK))
            {
                node.velocity.av = new Vec4(0, node.turret.angularVelocity,0,0);
            } else
            {
                node.velocity.av = Vec4.VEC4_ZERO;
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
