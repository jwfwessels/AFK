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

    public ProjectileEntity(GfxEntity gfxEntity, EntityManager entityManager)
    {
        super(gfxEntity, entityManager);
        size = 0.14f;
        mass = 0.5f;
        VELOCITY = 2.5f;
    }

    @Override
    public void update(float t, float dt, boolean[] flags)
    {
        float angle = -(float) Math.toRadians(current.rotation.getY());
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        previous = new EntityState(current);
        current.velocity = new Vec3(-(VELOCITY * sin), 0, VELOCITY * cos);
        integrate(current, t, dt);
        checkColision();
    }

    private void checkColision()
    {
        for (int i = 0; i < entityManager.entities.size(); i++)
        {
            if (intersectionTesting(this, entityManager.entities.get(i)))
            {
                System.out.println(this + " --> " + i);
                entityManager.RomoveSubEntity(this);
                // TODO: possible create explosion at the /exact/ location of impact?
                entityManager.makeExplosion(this.current.position.add(new Vec3(0, 1, 0)));
            }
        }
    }
}
