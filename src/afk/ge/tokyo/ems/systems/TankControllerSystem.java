/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.bot.london.Robot;
import afk.ge.EntityState;
import afk.ge.tokyo.EntityManager;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.RenderNode;
import afk.ge.tokyo.ems.nodes.TankControlNode;
import com.hackoeur.jglm.Vec3;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TankControllerSystem implements ISystem
{

    Engine engine;
    EntityManager manager;
    /// considr removing this, and useing component data///
    float VELOCITY = 1f;
    float ANGULAR_VELOCITY = 1f;
    ///
    
    public TankControllerSystem(EntityManager manager)
    {
        this.manager = manager;
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
        List<TankControlNode> nodes = engine.getNodeList(TankControlNode.class);
        System.out.println("I'm walking on " + nodes.size() + " sunshines");
        for (TankControlNode node : nodes)
        {
            boolean[] flags = node.controller.inputFlags;
            float angle = -(float) Math.toRadians(node.state.rot.getY());
            float sin = (float) Math.sin(angle);
            float cos = (float) Math.cos(angle);
            node.state.setPrev(node.state.pos, node.state.rot, node.state.scale);
            node.velocity.v = Vec3.VEC3_ZERO;
            if (flags[Robot.MOVE_FRONT])
            {
                node.velocity.v = node.velocity.v.add(new Vec3(-(VELOCITY * sin), 0, VELOCITY * cos));
            } else if (flags[Robot.MOVE_BACK])
            {
                node.velocity.v = node.velocity.v.add(new Vec3(VELOCITY * sin, 0, -(VELOCITY * cos)));
            }
            if (flags[Robot.TURN_CLOCK])
            {
                node.state.rot = node.state.rot.add(new Vec3(0, -ANGULAR_VELOCITY, 0));
            } else if (flags[Robot.TURN_ANTICLOCK])
            {
                node.state.rot = node.state.rot.add(new Vec3(0, ANGULAR_VELOCITY, 0));
            }
            System.out.println("v: " + node.velocity.v);
            System.out.println("rot: " + node.state.rot);
        }
    }

    @Override
    public void destroy()
    {
    }
}
