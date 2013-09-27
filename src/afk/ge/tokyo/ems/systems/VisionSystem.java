package afk.ge.tokyo.ems.systems;

import afk.ge.BBox;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.Entity;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.Utils;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.TargetingInfo;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.TargetableNode;
import afk.ge.tokyo.ems.nodes.VisionNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author daniel
 */
public class VisionSystem implements ISystem
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
        List<VisionNode> vnodes = engine.getNodeList(VisionNode.class);
        List<TargetableNode> tnodes = engine.getNodeList(TargetableNode.class);
        List<CollisionNode> cnodes = engine.getNodeList(CollisionNode.class);

        for (VisionNode vnode : vnodes)
        {
            List<TargetingInfo> thetas = new ArrayList<TargetingInfo>();
            targetloop:
            for (TargetableNode tnode : tnodes)
            {
                if (sameBot(vnode, tnode.entity))
                {
                    continue;
                }
                
                State state = Utils.getWorldState(vnode.entity);

                TargetingInfo info = isVisible(
                        state,
                        tnode.state,
                        vnode.vision.fovx,
                        vnode.vision.dist,
                        tnode.entity);

                if (info != null)
                {
                    // stop tanks from targeting innocent walls
                    for (CollisionNode cnode : cnodes)
                    {
                        // to stop tanks from blocking their own vision
                        if (sameBot(vnode, cnode.entity) || cnode.entity == tnode.entity)
                        {
                            continue;
                        }

                        BBox bbox = new BBox(cnode.state, cnode.bbox);
                        if (bbox.isLineInBox(state.pos, tnode.state.pos))
                        {
                            continue targetloop;
                        }
                    }

                    thetas.add(info);
                }
            }
            vnode.controller.events.visibleBots = thetas;
        }
    }
    
    public boolean sameBot(VisionNode a, Entity b)
    {
        Controller controller = b.get(Controller.class);
        return (controller != null && controller.id == a.controller.id);
    }

    @Override
    public void destroy()
    {
    }

    /**
     * This function determines whether entity A can see entity B. there are two
     * checks.
     * <p>
     * First we confirm whether the 2 objects are within viewing distance of
     * each other.
     * <p>
     * Then we get the angle between there relative vectors by taking the dot
     * product of (A·B). to see whether B falls within A's Field Of View.
     *
     * @param a
     * @param b
     * @param fov
     * @param viewingDistance
     * @return
     */
    protected TargetingInfo isVisible(State a, State b, float fov, float viewingDistance, Entity targetBot)
    {
        // point lies outside of viewing distance, reject
        if (Float.compare(b.pos.subtract(a.pos).getLengthSquared(),
                viewingDistance * viewingDistance) > 0)
        {
            return null;
        }

        float[] me = getAngles(Utils.getForward(a));

        Vec3 d = b.pos.subtract(a.pos);

        float[] them = getAngles(d);

        float relativeBearing = (float)Math.toDegrees(them[0] - me[0]);
        float relativeElevation = (float)Math.toDegrees(them[1] - me[1]);

        float halfFOV = fov * 0.5f;
        if (within(relativeBearing, halfFOV) && within(relativeElevation, halfFOV))
        {
            final Controller controller = targetBot.get(Controller.class);
            return new TargetingInfo(b.pos, relativeBearing, relativeElevation, controller != null ? controller.id : null);
        }

        // outside of FOV
        return null;
    }

    public static float[] getAngles(Vec3 v)
    {
        float bearing = (float) FastMath.atan2(v.getZ(), v.getX());
        float elevation = (float) FastMath.atan2(v.getY(),
                FastMath.sqrtFast(v.getX() * v.getX() + v.getZ() * v.getZ()));
        return new float[]
        {
            bearing, elevation
        };
    }

    public static boolean within(float x, float range)
    {
        return x >= -range && x <= range;
    }
}
