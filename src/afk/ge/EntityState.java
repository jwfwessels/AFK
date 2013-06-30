/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class EntityState
{
    public Vec3 position;
    public Vec3 momentum;
    public Vec3 velocity;
    public Vec3 rotation;
    protected float mass;
    protected float inverseMass;

    public EntityState(Vec3 position)
    {
        this.position = position;
        System.out.println("postion" + position);
        System.out.println("this.postion" + this.position);
        this.momentum = Vec3.VEC3_ZERO;
        this.velocity = Vec3.VEC3_ZERO;
        this.rotation = Vec3.VEC3_ZERO;
        this.mass = 1.0f;
        this.inverseMass = 1.0f / mass;
    }

    public EntityState(EntityState instance)
    {
        this.position = instance.position;
        this.momentum = instance.momentum;
        this.velocity = instance.velocity;
        this.rotation = instance.rotation;
        this.mass = instance.mass;
        this.inverseMass = instance.inverseMass;
    }

    protected void recalculate()
    {
        velocity = momentum.multiply(inverseMass);
    }
}
