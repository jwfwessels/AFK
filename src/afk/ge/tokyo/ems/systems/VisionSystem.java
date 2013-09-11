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
            List<Float> thetas = new ArrayList<Float>();
            targetloop:
            for (TargetableNode tnode : tnodes)
            {
                if (tnode.entity == vnode.entity)
                    continue;
                
                float theta = isVisible(
                        vnode.state,
                        tnode.state,
                        vnode.vision.fovx,
                        vnode.vision.dist
                    );
                
                if (!Float.isNaN(theta))
                {
                    // stop tanks from targeting innocent walls
                    for (CollisionNode cnode : cnodes)
                    {
                        // to stop tanks from blocking their own vision
                        if (cnode.entity == vnode.entity || cnode.entity == tnode.entity) continue;

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
     * @param halfFOV
     * @param viewingDistanceSqr
     * @return
     */
    protected float isVisible(State a, State b, float fov, float viewingDistance)
    {
        float halfFOV = fov * 0.5f;
        float viewingDistanceSqr = viewingDistance*viewingDistance;
        
        float yRot = a.rot.getY();
        float xRot = a.rot.getX();
        float zRot = a.rot.getZ();
        Vec3 aToB = b.pos.subtract(a.pos);
        float adistB = aToB.getLengthSquared();
        if (Float.compare(adistB, viewingDistanceSqr) > 0)
        {
            return Float.NaN;
        }
        Mat4 rotationMatrix = new Mat4(1.0f);
        rotationMatrix = Matrices.rotate(rotationMatrix, xRot, X_AXIS);
        rotationMatrix = Matrices.rotate(rotationMatrix, yRot, Y_AXIS);
        rotationMatrix = Matrices.rotate(rotationMatrix, zRot, Z_AXIS);
        Vec4 A4 = rotationMatrix.multiply(new Vec4(0, 0, 1, 0));
        Vec3 A = new Vec3(A4.getX(), A4.getY(), A4.getZ());

        float theta = A.getUnitVector().dot(aToB.getUnitVector());
        theta = (float) FastMath.toDegrees(FastMath.acos(theta));
        //for testing
//        System.out.println(a.name + "    " + getSign(theta, A, aToB));
        float absTheta = Math.abs(theta);
        if (Float.compare(absTheta, halfFOV) > 0)
        {
            return Float.NaN;
        }
        theta = getSign(theta, A, aToB);
        return theta;
    }

    /**
     * this function determines whether a Entity B is to the right or left of
     * Entity A's current orientation. This is accomplished by means of a
     * reference Vector which is constructed to be 90° to the right of A's
     * orientation. Then by means of a dot (B · RightRef) we can determine
     * whether B is Obtuse to RightRef (-) or Acute (+).
     *
     * @param theta
     * @param A
     * @param B
     * @return
     */
    private float getSign(float theta, Vec3 A, Vec3 B)
    {
        Vec3 Aup = A.add(new Vec3(0, 1, 0));
        Vec3 ARightref = A.cross(Aup);
        float sign = B.dot(ARightref);
        if (Float.compare(sign, 0) < 0)
        {
            return -theta;
        } else
        {
            return theta;
        }
    }
    
}
