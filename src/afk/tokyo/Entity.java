/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class Entity
{

    private GfxEntity gfxPos;
    private EntityState current;
    private EntityState previous;

    protected class Derivative
    {

        Vec3 velocity;
        Vec3 force;
    }

    public Entity(GfxEntity gfxEntity)
    {
        gfxPos = gfxEntity;
    }

    void update(double t, double dt)
    {
        previous = new EntityState(current);
        integrate(current, t, dt);

    }

    private void integrate(EntityState state, double t, double dt)
    {
        Derivative a = evaluate(state, t, 0.0, null);
        Derivative b = evaluate(state, t, dt * 0.5, a);
        Derivative c = evaluate(state, t, dt * 0.5, b);
        Derivative d = evaluate(state, t, dt, c);

        state.position.add(d.velocity.add(a.velocity.add((b.velocity.add(c.velocity)).multiply(2))).multiply((float) (1 / 6 * dt)));
        //TODO
        //state.momentum
        //state.orientation
        //state.angularMomentum
        state.recalculate();
    }

    private Derivative evaluate(EntityState state, double t, double dt, Derivative derivative)
    {
        if (derivative != null)
        {
        }
        Derivative output = new Derivative();
        output.velocity = state.velocity;
        //TODO
        //output.spin
        //output.force
        //output.torque
        return output;
    }
}
