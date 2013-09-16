package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TankTracksNode;
import com.hackoeur.jglm.Vec3;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankTracksSystem implements ISystem
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
        List<TankTracksNode> nodes = engine.getNodeList(TankTracksNode.class);
        for (TankTracksNode node : nodes)
        {
            float angle = -(float) Math.toRadians(node.state.rot.getY());
            float sin = (float) Math.sin(angle);
            float cos = (float) Math.cos(angle);
            if (engine.getFlag(node.controller.id, MOVE_FRONT))
            {
                node.velocity.v = new Vec3(-(node.motor.topSpeed * sin), 0, node.motor.topSpeed * cos);
            } else if (engine.getFlag(node.controller.id, MOVE_BACK))
            {
                node.velocity.v = new Vec3(node.motor.topSpeed * sin, 0, -(node.motor.topSpeed * cos));
            } else
            {
                node.velocity.v = Vec3.VEC3_ZERO;
            }
            if (engine.getFlag(node.controller.id, TURN_CLOCK))
            {
                node.velocity.av = new Vec3(0, -node.motor.angularVelocity, 0);
            } else if (engine.getFlag(node.controller.id, TURN_ANTICLOCK))
            {
                node.velocity.av = new Vec3(0, node.motor.angularVelocity, 0);
            } else
            {
                node.velocity.av = Vec3.VEC3_ZERO;
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
