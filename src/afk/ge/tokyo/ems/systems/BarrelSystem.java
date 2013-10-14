package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.nodes.BarrelNode;
import com.hackoeur.jglm.Vec3;
import java.util.List;
import static afk.ge.ems.Utils.*;
import afk.ge.tokyo.ems.factories.ProjectileFactory;
import afk.ge.tokyo.ems.factories.ProjectileFactoryRequest;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Jw
 */
public class BarrelSystem implements ISystem
{
    private Engine engine;
    private ProjectileFactory factory = new ProjectileFactory();

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
            if (engine.getFlag(node.controller.id, AIM_UP))
            {
                node.velocity.av = new Vec4(0, 0, 0, -node.barrel.angularVelocity);
            } else if (engine.getFlag(node.controller.id, AIM_DOWN))
            {
                node.velocity.av = new Vec4(0, 0, 0, node.barrel.angularVelocity);
            } else
            {
                node.velocity.av = Vec4.VEC4_ZERO;
            }
            if (engine.getFlag(node.controller.id, ATTACK_ACTION))
            {
                node.weapon.timeSinceLastFire += dt;
                if (node.weapon.timeSinceLastFire >= node.weapon.fireInterval)
                {
                    node.weapon.timeSinceLastFire = 0;
                    fire(node);
                }
            }
        }
    }

    public void fire(BarrelNode node)
    {
        State state = getWorldState(node.entity);
        float barrelLength = node.barrel.length * state.scale.getZ();
        Vec3 forward = getForward(state);
        engine.addEntity(factory.create(new ProjectileFactoryRequest(
                state.pos.add(forward.multiply(barrelLength)),
                state.rot,
                new Vec3(0.3f, 0.3f, -0.3f),
                forward, "projectile", new Vec3(0.5f,0.5f,0.5f),
                node.weapon, node.controller)));
    }

    @Override
    public void destroy()
    {
    }
}
