package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.nodes.TankBarrelNode;
import com.hackoeur.jglm.Vec3;
import java.util.List;
import static afk.ge.tokyo.ems.Utils.*;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Jw
 */
public class TankBarrelSystem implements ISystem
{

    public static final int BARREL_AV = 5;
    Engine engine;
    EntityManager entityManager;

    public TankBarrelSystem(EntityManager entityManager)
    {
        this.entityManager = entityManager;
    }

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
            if (engine.getFlag(node.controller.id, AIM_UP))
            {
                node.velocity.av = new Vec4(0, 0, 0, -BARREL_AV);
            } else if (engine.getFlag(node.controller.id, AIM_DOWN))
            {
                node.velocity.av = new Vec4(0, 0, 0, BARREL_AV);
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

    public void fire(TankBarrelNode node)
    {
        State state = getWorldState(node.entity);
        float barrelLength = node.barrel.length * state.scale.getZ();
        Vec3 forward = getForward(state);
        entityManager.createProjectileNEU(node.controller.id, node.weapon,
                new State(state, forward.multiply(barrelLength)), forward);
    }

    @Override
    public void destroy()
    {
    }
}
