package afk.ge.tokyo.ems.systems;

import static afk.bot.london.TankRobot.*;
import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TankBarrelNode;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankBarrelSystem implements ISystem
{

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
                node.state.rot = node.state.rot.add(node.velocity.av);
            } else if (engine.getFlag(node.controller.id, AIM_DOWN))
            {
                node.state.rot = node.state.rot.add(node.velocity.av.getNegated());
            }
            if (engine.getFlag(node.controller.id, ATTACK_ACTION))
            {
                node.weapon.timeSinceLastFire += dt;
                if (node.weapon.timeSinceLastFire >= node.weapon.fireInterval)
                {
                    node.weapon.timeSinceLastFire = 0;
                    // TODO: spawn projectile at end of barrel?
                    entityManager.createProjectileNEU(node.entity, node.weapon, node.state);
                }
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
