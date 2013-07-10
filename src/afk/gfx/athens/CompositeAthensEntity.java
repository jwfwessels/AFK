package afk.gfx.athens;

import afk.gfx.Camera;
import afk.gfx.CompositeGfxEntity;
import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.Collection;
import javax.media.opengl.GL2;

/**
 * Athens implementation of the composite graphics entity class.
 * @author Daniel
 */
public class CompositeAthensEntity
    extends AthensEntity
    implements CompositeGfxEntity
{
    private Collection<AthensEntity> subEntities;

    protected CompositeAthensEntity()
    {
        subEntities = new ArrayList<AthensEntity>();
    }
    
    @Override
    public void addEntity(GfxEntity entity)
    {
        subEntities.add((AthensEntity)entity);
    }

    @Override
    protected void update(float delta)
    {
        for (AthensEntity entity :subEntities)
            entity.update(delta);
    }

    @Override
    protected void draw(GL2 gl, Camera camera, Vec3 sun)
    {
        this.draw(gl,camera,sun,createWorldMatrix());
    }

    @Override
    protected void draw(GL2 gl, Camera camera, Vec3 sun, Mat4 worldMatrix)
    {
        for (AthensEntity entity :subEntities)
        {
            Mat4 subWorldMatrix = entity.createWorldMatrix();
            
            // TODO: add matrix multiplication to jglm.
            //entity.draw(gl, camera, sun, worldMatrix.multiply(subWorldMatrix));
        }
    }
    
}
