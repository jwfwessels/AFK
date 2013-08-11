/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.bot.RobotEngine;
import afk.ge.AbstractEntity;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.ControllerNode;
import afk.ge.tokyo.ems.nodes.ProjectileNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import java.util.List;

/**
 *
 * @author Jw
 */
public class ProjectileSystem implements ISystem
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

        List<ProjectileNode> nodesA = engine.getNodeList(ProjectileNode.class);
        List<CollisionNode> nodesB = engine.getNodeList(CollisionNode.class);
        for (ProjectileNode projectileNode : nodesA)
        {
            for (CollisionNode collisionNode : nodesB)
            {
                if (intersectionTesting(projectileNode.state, collisionNode.state))
                {
                    System.out.println(projectileNode.relation.parent + " ==> " + collisionNode);
//                    b.hit(DAMAGE);
//                    hit(DAMAGE);

                    // TODO: possible create explosion at the /exact/ location of impact?
//                    entityManager.makeExplosion(this.current.position.add(new Vec3(0, 0.75f, 0)), this.parent, 0);
                }

                float dist = projectileNode.state.pos.subtract(projectileNode.state.pos).getLength();
                projectileNode.bullet.rangeLeft -= dist;
                if (Float.compare(projectileNode.bullet.rangeLeft, 0) <= 0)
                {
                    engine.removeEntity(projectileNode.entity);
                }


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
        System.out.println("HIT!");
        return true;
    }
}
