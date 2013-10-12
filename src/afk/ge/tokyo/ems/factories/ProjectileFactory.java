package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.tokyo.ems.components.Bullet;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.components.Velocity;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class ProjectileFactory implements Factory<ProjectileFactoryRequest>
{

    @Override
    public Entity create(ProjectileFactoryRequest request)
    {
        Entity entity = new Entity();
        State state = new State(request.pos, request.rot, request.scale);
        entity.add(state);

        entity.add(new Velocity(request.forward.multiply(request.weapon.speed), Vec4.VEC4_ZERO));
        entity.add(new Renderable(request.type, request.colour, 1.0f));
        entity.add(new Bullet(request.weapon.range, request.weapon.damage, request.parent));

        return entity;
    }
    
}
