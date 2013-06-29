/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;

/**
 *
 * @author Jw
 */
public abstract class AbstractEntity
{

    public float ANGULAR_VELOCITY = 1.0f;
    public float VELOCITY = 0.5f;
    protected GfxEntity gfxPos;
    protected EntityState current;
    protected EntityState previous;
    protected float mass;
    protected float size;
    protected EntityManager entityManager;

    protected class Derivative
    {

        Vec3 velocity;
        Vec3 force;
    }

    public AbstractEntity(GfxEntity gfxEntity, EntityManager entityManager)
    {
        gfxPos = gfxEntity;
        current = new EntityState(gfxPos.getPosition());
        previous = new EntityState(current);
        this.entityManager = entityManager;

    }

    public void setState(EntityState state)
    {
        current = new EntityState(state);
    }

    protected Derivative evaluate(EntityState state, float t, float dt, Derivative derivative)
    {
        if (derivative != null)
        {
            state.position = state.position.add(derivative.velocity.multiply(dt));
            state.momentum = state.momentum.add(derivative.force.multiply(dt));
            //            state.recalculate();
        }
        Derivative output = new Derivative();
        output.velocity = state.velocity;
        //        output.force = acceleration(state, t + dt);
        forces(state, t + dt, output);
        //TODO
        //output.spin
        //output.force
        //output.torque
        return output;
    }

    protected void forces(EntityState state, float par, Derivative output)
    {
        output.force = state.position.multiply(-10);
    }

    protected void integrate(EntityState state, float t, float dt)
    {
        //        state.velocity = new Vec3(0.01f, 0, 0);
        Derivative a = evaluate(state, t, 0.0f, null);
        Derivative b = evaluate(state, t, dt * 0.5f, a);
        Derivative c = evaluate(state, t, dt * 0.5f, b);
        Derivative d = evaluate(state, t, dt, c);
        state.position = state.position.add(d.velocity.add(a.velocity.add((b.velocity.add(c.velocity)).multiply(2))).multiply((float) ((1.0f / 6.0f) * dt)));
        state.momentum = state.momentum.add(d.force.add(a.force.add((b.force.add(c.force)).multiply(2))).multiply((float) ((1.0f / 6.0f) * dt)));
        //TODO
        //state.orientation
        //state.angularMomentum
        state.recalculate();
    }

    protected EntityState interpolate(float alpha)
    {
        EntityState tempState = new EntityState(current);
        //state = currentState * alpha = previousState* (1.0-alpha);
        tempState.position = previous.position.multiply(1 - alpha).add(current.position.multiply(alpha));
        tempState.momentum = previous.momentum.multiply(1 - alpha).add(current.momentum.multiply(alpha));
        tempState.recalculate();
        return tempState;
    }

    //    private Vec3 acceleration(EntityState state, float d)
    //    {
    //        float k = 10;
    //        float b = 1;
    //        return state.position.multiply(-k).subtract(state.velocity.multiply(b));
    //    }
    void render(float alpha)
    {
        EntityState gfxState = interpolate(alpha);
        gfxPos.setPosition(gfxState.position);
        gfxPos.setRotation(gfxState.rotation);
        //        System.out.println("gfxState.position " + gfxState.position.toString());
    }

    abstract void update(float t, float dt, boolean[] flags);

    boolean intersectionTesting(AbstractEntity a, AbstractEntity b)
    {
        Vec3 B = (a.current.position.subtract(a.previous.position)).subtract(b.current.position.subtract(b.previous.position));
        double bSqr = B.getLengthSquared();
        if (Double.compare(bSqr, 0.0f) == 0)
        {
            return false;
        }
        Vec3 A = a.previous.position.subtract(b.previous.position);
        double aSqr = A.getLengthSquared();
        double rrSqr = (a.size + b.size) * (a.size + b.size);
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
