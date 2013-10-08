package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.ems.FactoryException;
import afk.ge.tokyo.HeightmapLoader;
import afk.ge.tokyo.Tokyo;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.State;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class HeightmapFactory implements Factory<HeightmapFactoryRequest>
{

    @Override
    public Entity create(HeightmapFactoryRequest request)
    {
        Entity entity = new Entity();
        entity.add(new State(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO,
                new Vec3(Tokyo.BOARD_SIZE)));
        entity.add(new Renderable("floor", new Vec3(1.0f, 1.0f, 1.0f)));
        try
        {
            entity.add(new HeightmapLoader().load(request.name, Tokyo.BOARD_SIZE, Tokyo.BOARD_SIZE, Tokyo.BOARD_SIZE));
        } catch (IOException ex)
        {
            throw new FactoryException(ex);
        }
        return entity;
    }
    
}
