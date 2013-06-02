/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class EntityState
{

    protected Vec3 position;
    protected Vec3 momentum;
    protected Vec3 velocity;
    protected float mass;
    protected float inverseMass;

    public EntityState(Vec3 position)
    {
        this.position = position;
        System.out.println("postion" + position);
        System.out.println("this.postion" + this.position);
        this.momentum = new Vec3(0, 0, 0);
        this.velocity = new Vec3(0, 0, 0);
        this.mass = 1.0f;
        this.inverseMass = 1.0f / mass;
    }

    protected EntityState(EntityState instance)
    {
        position = instance.position;
        momentum = instance.momentum;
        velocity = instance.velocity;
        mass = instance.mass;
        inverseMass = instance.inverseMass;
    }

    void recalculate()
    {
        velocity = momentum.multiply(inverseMass);
    }
}
