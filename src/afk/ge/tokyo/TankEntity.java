/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.gfx.GfxEntity;
import afk.london.Robot;
import static afk.ge.tokyo.AbstractEntity.ANGULAR_VELOCITY;
import static afk.ge.tokyo.AbstractEntity.VELOCITY;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class TankEntity extends AbstractEntity
{

//    protected GfxEntity projectileGfxEntity;
//    boolean shoot = false;

    public TankEntity(GfxEntity gfxEntity, EntityManager entityManager)
    {
        super(gfxEntity, entityManager);
        size = 1.4f;
        mass = 2.0f;
    }

    @Override
    void update(float t, float dt, boolean[] flags)
    {
        float angle = -(float) Math.toRadians(current.rotation.getY());
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        previous = new EntityState(current);
        current.velocity = Vec3.VEC3_ZERO;
        if (flags[Robot.MOVE_FRONT])
        {
            current.velocity = current.velocity.add(new Vec3(-(VELOCITY * sin), 0, VELOCITY * cos));
        } else if (flags[Robot.MOVE_BACK])
        {
            current.velocity = current.velocity.add(new Vec3(VELOCITY * sin, 0, -(VELOCITY * cos)));
        }
        if (flags[Robot.TURN_CLOCK])
        {
            current.rotation = current.rotation.add(new Vec3(0, ANGULAR_VELOCITY, 0));
        } else if (flags[Robot.TURN_ANTICLOCK])
        {
            current.rotation = current.rotation.add(new Vec3(0, -ANGULAR_VELOCITY, 0));
        }
        if (flags[Robot.ATTACK_ACTION])
        {
            ProjectileEntity bullet = (ProjectileEntity) entityManager.createProjectile();
            bullet.setState(current);
            System.out.println("BANG!!!");
        }
        integrate(current, t, dt);
    }
}
