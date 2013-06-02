/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GfxEntity;
import static afk.london.Robot.*;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class Entity
{
    public static final float VELOCITY = 0.5f;
    public static final float ANGULAR_VELOCITY = 1.0f;

    private float mass = 1;
    private float size = 1;
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
        current = new EntityState(gfxPos.getPosition());
        previous = new EntityState(current);

    }

    void update(float t, float dt, boolean[] flags)
    {
        float angle = -(float) Math.toRadians(current.rotation.getY());
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);

        previous = new EntityState(current);
        current.velocity = Vec3.VEC3_ZERO;

        if (flags[MOVE_FRONT])
        {
            current.velocity = current.velocity.add(
                    new Vec3(-(VELOCITY * sin),
                    0,
                    (VELOCITY * cos)));
        } else if (flags[MOVE_BACK])
        {
            current.velocity = current.velocity.add(
                    new Vec3((VELOCITY * sin),
                    0,
                    -(VELOCITY * cos)));
        }
        if (flags[TURN_CLOCK])
        {
            current.rotation = current.rotation.add(new Vec3(0, ANGULAR_VELOCITY, 0));
        } else if (flags[TURN_ANTICLOCK])
        {
            current.rotation = current.rotation.add(new Vec3(0, -ANGULAR_VELOCITY, 0));
        }

        if (flags[ATTACK_ACTION])
        {
            System.out.println("BANG!!!");
        }

//            dt = 10.0f;
        integrate(current, t, dt);
        //System.out.println("" + current.position.toString());

    }

    private void integrate(EntityState state, float t, float dt)
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
//        state.recalculate();

    }

    private Derivative evaluate(EntityState state, float t, float dt, Derivative derivative)
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

    private void forces(EntityState state, float par, Derivative output)
    {
        output.force = state.position.multiply(-10);
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

    private EntityState interpolate(float alpha)
    {
        EntityState tempState = new EntityState(current);
        tempState.position = previous.position.multiply(1 - alpha).add(current.position.multiply(alpha));
        tempState.momentum = previous.momentum.multiply(1 - alpha).add(current.momentum.multiply(alpha));
        tempState.recalculate();
        return tempState;
    }
}
