package afk.ge.tokyo.ems.systems;

import afk.ge.EntityState;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.nodes.MovementNode;
import com.hackoeur.jglm.Vec3;
import java.util.List;

/**
 *
 * @author daniel
 */
public class MovementSystem implements ISystem
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
        List<MovementNode> nodes = engine.getNodeList(MovementNode.class);
        
        for (MovementNode node : nodes)
        {
            integrate(node.state, node.velocity, t, dt);
        }
    }

    @Override
    public void destroy()
    {
    }
    
    /**
     * inner class used as a data structure to perform integration for entity
     * position updates.
     */
    public static class Derivative
    {
        Vec3 velocity;
        Vec3 force;
    }
    
    /**
     * This method performs RK4 integration to approximate the position of an
     * entityState after a time-step. this method takes advantage of the fact
     * that a better approximation of a function can be reached if we use its
     * higher order derivatives. This results in much faster convergence than
     * Euler integration.
     *
     * @see <a
     * href="http://gafferongames.com/game-physics/integration-basics/">RK4
     * source 1<a/>
     * @see <a
     * href="http://stackoverflow.com/questions/1668098/runge-kutta-rk4-integration-for-game-physics">RK4
     * source 2<a/>
     *
     * @param state
     * @param t
     * @param dt
     */
    public static void integrate(State state, Velocity velocity, float t, float dt)
    {
        //        state.velocity = new Vec3(0.01f, 0, 0);
        Derivative a = evaluate(state, velocity, t, 0.0f, null);
        Derivative b = evaluate(state, velocity, t, dt * 0.5f, a);
        Derivative c = evaluate(state, velocity, t, dt * 0.5f, b);
        Derivative d = evaluate(state, velocity, t, dt, c);
        state.pos = state.pos.add(d.velocity.add(a.velocity.add((b.velocity.add(c.velocity)).multiply(2))).multiply((float) ((1.0f / 6.0f) * dt)));
        //TODO
        //state.orientation
        //state.angularMomentum
        //state.recalculate();
    }

    /**
     * this function evaluates a state in terms of the current time t and the
     * length of time for one time step dt. this function returns a derivative
     * object.
     *
     * @param state
     * @param t <i>current time</i>
     * @param dt <i>time-step length</i>
     * @param derivative
     * @return
     */
    public static Derivative evaluate(State state, Velocity velocity,
            float t, float dt, Derivative derivative)
    {
        if (derivative != null)
        {
            state.pos = state.pos.add(derivative.velocity.multiply(dt));
        }
        Derivative output = new Derivative();
        output.velocity = velocity.v;
        //        output.force = acceleration(state, t + dt);
        //TODO
        //output.spin
        //output.force
        //output.torque
        return output;
    }
    
}
