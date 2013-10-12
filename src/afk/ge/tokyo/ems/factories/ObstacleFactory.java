package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class ObstacleFactory implements Factory<ObstacleFactoryRequest>
{

    @Override
    public Entity create(ObstacleFactoryRequest request)
    {
        Entity entity = new Entity();
        entity.add(new State(request.pos, Vec4.VEC4_ZERO, request.scale));
        entity.add(new BBoxComponent(new Vec3(0.5f),new Vec3(0,0.5f,0)));
        entity.add(new Renderable(request.type, new Vec3(0.75f, 0.75f, 0.75f),request.opacity));

        return entity;
    }
    
}
