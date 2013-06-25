/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;
import afk.gfx.GfxEntity;
import static afk.tokyo.AbstractEntity.VELOCITY;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class ProjectileEntity extends AbstractEntity
{

    public ProjectileEntity(GfxEntity gfxEntity)
    {
        super(gfxEntity);
        mass = 0.5f;
    }

    public void setOrigin(EntityState origin)
    {
        current = new EntityState(origin);
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
    }
}
