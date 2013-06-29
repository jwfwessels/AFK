/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.gfx.GfxEntity;
import static afk.ge.tokyo.AbstractEntity.VELOCITY;
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
    }

    @Override
    void update(float t, float dt, boolean[] flags)
    {
        float angle = -(float) Math.toRadians(current.rotation.getY());
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
        previous = new EntityState(current);
        current.velocity = Vec3.VEC3_ZERO;
        current.velocity = current.velocity.add(new Vec3(-(VELOCITY * sin), 0, VELOCITY * cos));
        integrate(current, t, dt);
//        for (int i = 0; i < entityManager.entities.size(); i++)
//        {
            if (intersectionTesting(this, entityManager.entities.get(1)))
            {
//                System.out.println(this + " --> " + entityManager.entities.get(i));
            }
//        }
    }
}
