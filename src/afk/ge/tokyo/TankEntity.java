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

    protected ProjectileEntity bullet;
    boolean shoot = false;

    public TankEntity(GfxEntity gfxEntity)
    {
        super(gfxEntity);
        mass = 2.0f;
    }

    void setProjectile(ProjectileEntity projectileEntity)
    {
        this.bullet = projectileEntity;
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
            bullet.setOrigin(current);
            bullet.update(t, dt, flags);
            shoot = true;
            System.out.println("BANG!!!");
        }
        //            dt = 10.0f;
        integrate(current, t, dt);
        //System.out.println("" + current.position.toString());
    }

    @Override
    void render(float alpha)
    {
        super.render(alpha);
        if (shoot)
        {
            bullet.render(alpha);
            System.out.println("boom");
            shoot = false;
        }
    }
}
