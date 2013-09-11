package afk.ge.tokyo.ems.systems;

import afk.ge.BBox;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.TargetableNode;
import afk.ge.tokyo.ems.nodes.VisionNode;
import static afk.gfx.GfxUtils.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
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
            List<float[]> thetas = new ArrayList<float[]>();
            targetloop:
            for (TargetableNode tnode : tnodes)
            {
                if (tnode.entity == vnode.entity)
                {
                    continue;
                }

                float[] theta = isVisible(
                        vnode.state,
                        tnode.state,
                        vnode.vision.fovx,
                        vnode.vision.dist);

                if (theta != null)
                {
                    // stop tanks from targeting innocent walls
                    for (CollisionNode cnode : cnodes)
                    {
                        // to stop tanks from blocking their own vision
                        if (cnode.entity == vnode.entity || cnode.entity == tnode.entity)
                        {
                            continue;
                        }

                        BBox bbox = new BBox(cnode.state, cnode.bbox.extent);
                        if (bbox.isLineInBox(vnode.state.pos, tnode.state.pos))
                        {
                            continue targetloop;
                        }
                    }

                    thetas.add(theta);
                }
            }
            Controller controller = vnode.entity.get(Controller.class);
            if (controller != null)
            {
                controller.events.visibleBots = thetas;
            }
        }
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
    protected float[] isVisible(State a, State b, float fov, float viewingDistance)
    {
        // point lies outside of viewing distance, reject
        if (Float.compare(b.pos.subtract(a.pos).getLengthSquared(),
                viewingDistance * viewingDistance) > 0)
        {
            return null;
        }

        // rotate a normal vector along the Z-axis using the entity's rotation
        // to get a normal in the entity's viewing direction
        Mat4 rotationMatrix = new Mat4(1.0f);
        rotationMatrix = Matrices.rotate(rotationMatrix, a.rot.getX(), X_AXIS);
        rotationMatrix = Matrices.rotate(rotationMatrix, a.rot.getZ(), Z_AXIS);
        rotationMatrix = Matrices.rotate(rotationMatrix, a.rot.getY(), Y_AXIS);

        // now we have a set of axes relative to the tank
        Vec4 forward = rotationMatrix.multiply(Z_AXIS.toDirection());
        float[] me = getAngles(forward.getXYZ());

        Vec3 d = b.pos.subtract(a.pos);

        float[] them = getAngles(d);

        float relativeBearing = (float)Math.toDegrees(them[0] - me[0]);
        float relativeElevation = (float)Math.toDegrees(them[1] - me[1]);

        float halfFOV = fov * 0.5f;
        if (within(relativeBearing, halfFOV) && within(relativeElevation, halfFOV))
        {
            return new float[]
            {
                relativeBearing, relativeElevation
            };
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
