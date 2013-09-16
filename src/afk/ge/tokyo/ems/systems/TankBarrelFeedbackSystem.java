package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TankBarrelNode;
import com.hackoeur.jglm.Vec3;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankBarrelFeedbackSystem implements ISystem
{
    public static final int BARREL_AV = 1;

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
        List<TankBarrelNode> nodes = engine.getNodeList(TankBarrelNode.class);
        for (TankBarrelNode node : nodes)
        {
            node.controller.events.barrel = node.state.rot.getZ();
        }
    }

    @Override
    public void destroy()
    {
    }
}
