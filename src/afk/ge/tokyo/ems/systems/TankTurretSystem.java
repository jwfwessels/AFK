package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TankTurretNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankTurretSystem implements ISystem
{
    public static final int TURRET_AV = 10;

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
                node.velocity.av = new Vec4(0,-TURRET_AV,0,0);
            } else if (engine.getFlag(node.controller.id, AIM_ANTICLOCK))
            {
                node.velocity.av = new Vec4(0, TURRET_AV,0,0);
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
