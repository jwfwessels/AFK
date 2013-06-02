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

    private float mass = 1;
    private float size = 1;
    private GfxEntity gfxPos;
    private EntityState current;
    private EntityState previous;

    protected class Derivative
    {

        Vec3 dPosition;
        Vec3 dVelocity;
    }

    public Entity(GfxEntity gfxEntity)
    {
        gfxPos = gfxEntity;
        current = new EntityState(gfxPos.getPosition());
        previous = new EntityState(current);

    }

    void update(float t, float dt)
    {
        previous = new EntityState(current);
        integrate(current, t, dt);

    }

    private void integrate(EntityState state, float t, float dt)
    {
        Derivative a = evaluate(state, t, 0.0f, null);
        Derivative b = evaluate(state, t, dt * 0.5f, a);
        Derivative c = evaluate(state, t, dt * 0.5f, b);
        Derivative d = evaluate(state, t, dt, c);

        state.position.add(d.dPosition.add(a.dPosition.add((b.dPosition.add(c.dPosition)).multiply(2))).multiply((float) (1 / 6 * dt)));
        //TODO
        //state.momentum
        //state.orientation
        //state.angularMomentum
//        state.recalculate();
    }

    private Derivative evaluate(EntityState state, float t, float dt, Derivative derivative)
    {
        if (derivative != null)
        {
            state.position.add(derivative.dPosition.multiply(dt));

        }
        Derivative output = new Derivative();
        output.dPosition = state.velocity;
        output.dVelocity = acceleration(state, t + dt);
        //TODO
        //output.spin
        //output.force
        //output.torque
        return output;
    }

    private Vec3 acceleration(EntityState state, float d)
    {
        float k = 10;
        float b = 1;
        return state.position.multiply(-k).subtract(state.velocity.multiply(b));
    }

    void render(float alpha)
    {
        EntityState gfxState = interpolate(alpha);
        gfxPos.setPosition(gfxState.position);
    }

    private EntityState interpolate(float alpha)
    {
        EntityState tempState = new EntityState(current);
        tempState.position = previous.position.multiply(1 - alpha).add(current.position.multiply(alpha));
        return tempState;
    }
}
