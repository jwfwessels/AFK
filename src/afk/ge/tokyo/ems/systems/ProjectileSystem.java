package afk.ge.tokyo.ems.systems;

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.HeightmapLoader;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Life;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.factories.GenericFactory;
import afk.ge.tokyo.ems.factories.GenericFactoryRequest;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.HeightmapNode;
import afk.ge.tokyo.ems.nodes.ProjectileNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.hackoeur.jglm.support.FastMath;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Jw
 */
public class ProjectileSystem implements ISystem
{

    private Engine engine;
    private GenericFactory factory = new GenericFactory();
    private GenericFactoryRequest explosionRequest;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        try
        {
            explosionRequest = GenericFactoryRequest.load("explosionProjectile");
        }
        catch (IOException ex)
        {
            ex.printStackTrace(System.err);
            return false;
        }
        return true;
    }

    @Override
    public void update(float t, float dt)
    {

        List<ProjectileNode> bullets = engine.getNodeList(ProjectileNode.class);
        List<CollisionNode> nodes = engine.getNodeList(CollisionNode.class);
        HeightmapNode hnode = engine.getNodeList(HeightmapNode.class).get(0);

        bulletLoop:
        for (ProjectileNode bullet : bullets)
        {
            // collision with terrain
            if (hnode != null)
            {
                if (HeightmapLoader.under(bullet.state.prevPos, hnode.heightmap))
                {
                    bang(bullet, bullet.state.prevPos);
                    continue;
                } else if (HeightmapLoader.under(bullet.state.pos, hnode.heightmap))
                {
                    bang(bullet, bullet.state.pos);
                    continue;
                } else
                {
                    Vec3 intersection = HeightmapLoader.getIntersection(bullet.state.prevPos, bullet.state.pos, 0.1f, hnode.heightmap);
                    if (intersection != null)
                    {
                        bang(bullet, intersection);
                        continue;
                    }
                }
            }

            // collision testing for box
            for (CollisionNode node : nodes)
            {
                // to stop shells from exploding inside the tank's barrel:
                Controller controller = node.entity.getComponent(Controller.class);
                if (controller != null && controller == bullet.bullet.parent)
                {
                    continue;
                }

                BBox bbox = new BBox(node.state, node.bbox);
                if (bbox.isLineInBox(bullet.state.prevPos, bullet.state.pos))
                {
                    Life life = node.entity.getComponent(Life.class);
                    if (life != null)
                    {
                        life.hp -= bullet.bullet.damage;
                    }

                    // notify the victim that they got shot
                    // (only if the victim was not an innocent wall)
                    Controller victim = node.entity.getComponent(Controller.class);
                    if (victim != null)
                    {
                        victim.events.gotHit = true;
                        // notify the shooter that they shot someone
                        bullet.bullet.parent.events.didHit = true;
                    }


                    bang(bullet);
                    continue bulletLoop;
                }
            }

            // range testing
            float dist = bullet.state.prevPos.subtract(bullet.state.pos).getLength();
            bullet.bullet.rangeLeft -= dist;
            if (Float.compare(bullet.bullet.rangeLeft, 0) <= 0)
            {
                engine.removeEntity(bullet.entity);
            }
        }
    }

    @Override
    public void destroy()
    {
    }

    /**
     * This function determines whether entity A will, and has crossed paths
     * with entity B. We make use of the following formulas:
     * <pre>
     * A = a1 - b1
     * B = (a2 - a1) - (b2 - b1)
     * d^2 = A^2 - ((A · B)^2 / B^2)
     * t = (-(A·B) - Sqr((A·B)^2 - B^2 (A^2 - (r(a) + r(b))^2))) / B^2
     * </pre> if B^2 = 0, then either both a and b are: stationary or moving in
     * the same direction at the same speed, and can thus not collide.
     * <p>
     * if d^2 > (r(a) + r(b))^2 - the sum of the entities radii squared. then
     * the entities can never collide on there current trajectories.
     * </p><p>
     * if t is greater or equal to 0, and smaller than 1. Then entities a and b
     * intersect in the current time step.
     * </p>
     *
     * @param a
     * @param b
     * @return
     */
    protected boolean intersectionTesting(State a, State b)
    {
        Vec3 B = (a.pos.subtract(a.prevPos)).subtract(b.pos.subtract(b.prevPos));
        double bSqr = B.getLengthSquared();
        if (Double.compare(bSqr, 0.0f) == 0)
        {
            return false;
        }
        Vec3 A = a.prevPos.subtract(b.prevPos);
        double aSqr = A.getLengthSquared();
        double rrSqr = ((a.scale.add(b.scale)).getLengthSquared()) / 2;
        double aDotb = (A.dot(B));
        double aDotbSqr = aDotb * aDotb;
        double d2 = aSqr - (aDotbSqr / bSqr);

        if (Double.compare(d2, rrSqr) > 0)
        {
            return false;
        }
        //find fastInv double implimentation
        double t = (-(aDotb) - FastMath.sqrtFast((float) ((aDotbSqr) - bSqr * (aSqr - (rrSqr))))) / bSqr;
        if (Double.compare(t, 0) < 0 || Double.compare(t, 1) >= 0)
        {
            return false;
        }
        return true;
    }

    private void bang(ProjectileNode bullet)
    {
        this.bang(bullet, bullet.state.pos);
    }

    private void bang(ProjectileNode bullet, Vec3 where)
    {
        Entity entity = factory.create(explosionRequest);
        entity.addComponent(new State(where, Vec4.VEC4_ZERO, new Vec3(1)));
        engine.addEntity(entity);
        engine.removeEntity(bullet.entity);
    }
}
