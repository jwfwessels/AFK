package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.nodes.MovementNode;
import com.hackoeur.jglm.Vec3;
import static java.lang.Math.*;
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
            node.state.setPrev(node.state.pos, node.state.rot, node.state.scale);
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
        Vec3 dx = Vec3.VEC3_ZERO;
        Vec3 dv = Vec3.VEC3_ZERO;
        
        // angular
        Vec3 dax = Vec3.VEC3_ZERO;
        Vec3 dav = Vec3.VEC3_ZERO;
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
        Derivative a = evaluate(state, velocity, t, 0.0f, new Derivative());
        Derivative b = evaluate(state, velocity, t, dt * 0.5f, a);
        Derivative c = evaluate(state, velocity, t, dt * 0.5f, b);
        Derivative d = evaluate(state, velocity, t, dt, c);
        
        state.pos = state.pos.add(d.dx.add(a.dx.add((b.dx.add(c.dx)).multiply(2))).multiply((float) (dt / 6.0f)));
        state.rot = state.rot.add(d.dax.add(a.dax.add((b.dax.add(c.dax)).multiply(2))).multiply((float) (dt / 6.0f)));
        velocity.v = velocity.v.add(d.dv.add(a.dv.add((b.dv.add(c.dv)).multiply(2))).multiply((float) (dt / 6.0f)));
        velocity.av = velocity.av.add(d.dav.add(a.dav.add((b.dav.add(c.dav)).multiply(2))).multiply((float) (dt / 6.0f)));
    }

    /**
     * this function evaluates a state in terms of the current time t and the
     * length of time for one time step dt. this function returns a derivative
     * object.
     *
     * @param state
     * @param velocity
     * @param t <i>current time</i>
     * @param dt <i>time-step length</i>
     * @param derivative
     * @return
     */
    public static Derivative evaluate(final State state, final Velocity velocity,
            final float t, final float dt, final Derivative derivative)
    {
        //Vec3 pos = state.pos.add(derivative.dx.multiply(dt));
        Vec3 v = velocity.v.add(derivative.dv.multiply(dt));
        Vec3 av = velocity.av.add(derivative.dav.multiply(dt));
        
        Derivative output = new Derivative();
        output.dx = v;
        output.dv = Vec3.VEC3_ZERO; //acceleration(velocity,dt);
        output.dax = av;
        output.dav = Vec3.VEC3_ZERO; //angularAcceleration(velocity,dt);
        
        return output;
    }
    
    public static Vec3 acceleration(final Velocity velocity, final float dt)
    {
        final float c = 0f;
        
        return velocity.v.getNegated().scale((float)pow(c, dt)).add(velocity.a);
    }
    
    public static Vec3 angularAcceleration(final Velocity velocity, final float dt)
    {
        final float c = 0f;
        
        return velocity.av.getNegated().scale((float)pow(c, dt)).add(velocity.aa);
    }
    
}
