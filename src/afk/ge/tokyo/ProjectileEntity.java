/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.EntityState;
import afk.ge.AbstractEntity;
import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class ProjectileEntity extends AbstractEntity
{

    AbstractEntity parent;
    private final float DAMAGE;
    private float RANGE;
    private EntityState origin;
    float dist = 0;

    public ProjectileEntity(GfxEntity gfxEntity, EntityManager entityManager, AbstractEntity parent, float damage)
    {
        super(gfxEntity, entityManager);
        this.parent = parent;
        DAMAGE = damage;
        life = TOTAL_LIFE = damage;
        size = 0.14f;
        mass = 0.5f;
        VELOCITY = 10f;
        RANGE = 10;
    }

    @Override
    public void update(float t, float dt)
    {
        float angle = -(float) Math.toRadians(current.rotation.getY());
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        previous = new EntityState(current);
        current.velocity = new Vec3(-(VELOCITY * sin), 0, VELOCITY * cos);
        integrate(current, t, dt);
        checkColision();
        checkRange();
    }

    @Override
    public void setState(EntityState state)
    {
        origin = new EntityState(state);
        origin.position = origin.position.add(new Vec3(0, 0.8f, 0));
        current = new EntityState(origin);
    }

    private void checkColision()
    {
        for (int i = 0; i < entityManager.entities.size(); i++)
        {
            AbstractEntity b = entityManager.entities.get(i);
            if (intersectionTesting(this, b))
            {
                System.out.println(this.parent.name + " ==> " + b.name);
                b.hit(DAMAGE);
                hit(DAMAGE);

                // TODO: possible create explosion at the /exact/ location of impact?
//                entityManager.makeExplosion(this.current.position.add(new Vec3(0, 0.75f, 0)), this.parent, 0);
            }
        }
    }

    @Override
    public void hit(float DAMAGE)
    {
        life -= DAMAGE;
        System.out.println(name + " life: " + life);
        if (Float.compare(life, 0) <= 0)
        {
            entityManager.removeSubEntity(this);
        }
    }

    private void checkRange()
    {
        dist = current.position.subtract(previous.position).getLength();
        //for testing
//        float totaldist = current.position.subtract(origin.position).getLength();
//        System.out.println("totaldist: " + totaldist);
        RANGE -= dist;
        if (Float.compare(RANGE, 0) <= 0)
        {
            entityManager.removeSubEntity(this);
        }
    }
}
