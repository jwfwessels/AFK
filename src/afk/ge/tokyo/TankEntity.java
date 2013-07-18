/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.EntityState;
import afk.ge.AbstractEntity;
import afk.gfx.GfxEntity;
import afk.london.Robot;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;

/**
 *
 * @author Jw
 */
public class TankEntity extends AbstractEntity
{

    protected float RateOfFire;
    protected float lastShot;
    protected float FOV;
    private final int viewingDistanceSqr;
    // TODO: just a quick temp hack variable to get feedback working...
    protected boolean hitwall;

    public TankEntity(GfxEntity gfxEntity, EntityManager entityManager, float totalLife)
    {
        super(gfxEntity, entityManager);
        life = TOTAL_LIFE = totalLife;
        size = 1.4f;
        mass = 2.0f;
        RateOfFire = Tokyo.DELTA * 120;
        lastShot = -1;
        FOV = 90;
        viewingDistanceSqr = 10 * 10;
        VELOCITY = 1f;
        ANGULAR_VELOCITY = 1f;
    }

    @Override
    public void update(float t, float dt, boolean[] flags)
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
            fireProjectile(t);
        }
        integrate(current, t, dt);
        // TODO: temporary? doing quick-and-dirty bounds checking...
        checkWalls();
    }

    private void fireProjectile(float t)
    {
        float ready = t - lastShot;
        if (lastShot == -1 || Float.compare(ready, RateOfFire) >= 0)
        {
            lastShot = t;
            ProjectileEntity bullet = entityManager.createProjectile(this);
            bullet.setColour(new Vec3(0.75f, 0.0f, 0.0f));
            bullet.setState(current);
            System.out.println("BANG!!!");
        }
    }

    protected ArrayList<Float> checkVisible()
    {
        float halfFOV = FOV / 2;
        ArrayList<Float> targets = new ArrayList();
        for (int i = 0; i < entityManager.entities.size(); i++)
        {
            AbstractEntity b = entityManager.entities.get(i);
            if (b != this)
            {

                float theta = isVisible(this, b, halfFOV, viewingDistanceSqr);
                if (!Float.isNaN(theta))
                {
                    System.out.println(this.name + " <(©)> " + b.name + "  " + theta + "°");
                    targets.add(theta);
                }
            }
        }
        return targets;
    }

    @Override
    public void hit(float DAMAGE)
    {
        life -= DAMAGE;
        System.out.println(name + " life: " + life);
        if (Float.compare(life, 0) <= 0)
        {
            entityManager.RomoveEntity(this);
            entityManager.makeExplosion(this.current.position.add(new Vec3(0, 0, 0)), this, 1);
        }
    }

    // TODO: rudimentary bounds checking, just to make sure tanks don't venture off into the wild.
    private void checkWalls()
    {
        hitwall = false;

        float x = checkWall(current.position.getX());
        float y = checkWall(current.position.getY());
        float z = checkWall(current.position.getZ());

        if (hitwall)
        {
            current.position = new Vec3(x, y, z);
        }
    }

    private float checkWall(float comp)
    {
        float halfBoardSize = Tokyo.BOARD_SIZE * 0.5f;

        if (comp < -halfBoardSize)
        {
            hitwall = true;
            comp = -halfBoardSize;
        } else if (comp > halfBoardSize)
        {
            hitwall = true;
            comp = halfBoardSize;
        }

        return comp;
    }
}
