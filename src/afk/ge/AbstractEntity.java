/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge;

import afk.ge.tokyo.EntityManager;
import afk.gfx.GfxEntity;
import afk.gfx.athens.AthensEntity;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.hackoeur.jglm.support.FastMath;

/**
 *
 * @author Jw
 */
public abstract class AbstractEntity
{

    public String name;
    protected float ANGULAR_VELOCITY = 1.0f;
    protected float VELOCITY = 1.0f;
    protected GfxEntity gfxPos;
    protected EntityState current;
    protected EntityState previous;
    protected float mass;
    protected float size;
    protected Vec3 scale;
    protected EntityManager entityManager;
    protected float TOTAL_LIFE;
    protected float life;

    public GfxEntity getgfxEntity()
    {
        return gfxPos;
    }

    /**
     * This abstract method must be implemented in extending classes. Its
     * purpose is to decrements a Entities life based on the amount of Damage
     * passed as a parameter. as well as perform or call any additional methods
     * associated with the entities death.
     *
     * @param DAMAGE
     */
    public abstract void hit(float DAMAGE);

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
     * This protected constructor is used during the construction of concrete
     * Entities. This may need to be restructured at a later stage.
     *
     * @param gfxEntity
     * @param entityManager
     */
    protected AbstractEntity(GfxEntity gfxEntity, EntityManager entityManager)
    {
        gfxPos = gfxEntity;
        current = new EntityState(gfxPos.position);
        previous = new EntityState(current);
        this.entityManager = entityManager;
    }

    /**
     * sets the colour of the graphics object related to this entity. its passed
     * a Vec3 object which uses float values between [0.0 ,1.0] to indicate the
     * (red, green, blue) values of the colour.
     *
     * @param colour
     */
    public void setColour(Vec3 colour)
    {
        gfxPos.colour = colour;
    }

    /**
     * sets the current state equal to a new entityState object which is a deep
     * copy of state.
     *
     * @param state
     */
    public void setState(EntityState state)
    {
        current = new EntityState(state);
    }

    /**
     * abstract method that must be implemented in extending classes.
     * <p>
     * this method stores an instances of the previous (current) entity state
     * and then determines the entities new position using the RK4 integration
     * method, to update the current state. Overriding methods need to implement
     * this functionality at a minimum.
     *
     * @param t
     * @param dt
     * @param flags
     */
    public abstract void update(float t, float dt);

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
    public static void integrate(EntityState state, float t, float dt)
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
    public static Derivative evaluate(EntityState state, float t, float dt, Derivative derivative)
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

    /**
     *
     * @param state
     * @param par
     * @param output
     */
    public static void forces(EntityState state, float par, Derivative output)
    {
        //output.force = state.position.multiply(-10);
        output.force = Vec3.VEC3_ZERO;
    }

    /**
     * Interpolate gets passed the an alpha value that is used to determine the
     * the contribution of the previous and current logical states to the
     * graphic state to be rendered. the alpha is the offset of the render call
     * between logic updates to maintain a frame-rate independent of a game
     * update frequency.
     *
     * @param alpha
     * @return
     */
    protected EntityState interpolate(double alphaD)
    {
        float alpha = (float)alphaD;
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
    /**
     * render is called to update the graphical entities position based on the
     * most recent entity states (previous and current). it calls interpolate
     * with an alpha value that symbolizes the offset from the most recent logic
     * step to this render step. It then updates the graphics entities rotation
     * and position.
     *
     * @param alpha
     */
    public void render(double alpha)
    {
        EntityState gfxState = interpolate(alpha);
        gfxPos.position = (gfxState.position);
        gfxPos.rotation = (gfxState.rotation);
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
    protected boolean intersectionTesting(AbstractEntity a, AbstractEntity b)
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
    protected float isVisible(AbstractEntity a, AbstractEntity b, float halfFOV, float viewingDistanceSqr)
    {
        float yRot = a.current.rotation.getY();
        float xRot = a.current.rotation.getX();
        float zRot = a.current.rotation.getZ();
        Vec3 aToB = b.current.position.subtract(a.current.position);
        float adistB = aToB.getLengthSquared();
        if (Float.compare(adistB, viewingDistanceSqr) > 0)
        {
            return Float.NaN;
        }
        Mat4 rotationMatrix = new Mat4(1.0f);
        rotationMatrix = Matrices.rotate(rotationMatrix, xRot, AthensEntity.X_AXIS);
        rotationMatrix = Matrices.rotate(rotationMatrix, yRot, AthensEntity.Y_AXIS);
        rotationMatrix = Matrices.rotate(rotationMatrix, zRot, AthensEntity.Z_AXIS);
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
