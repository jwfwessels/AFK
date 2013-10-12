package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class ObstacleFactoryRequest implements FactoryRequest
{
    protected Vec3 pos;
    protected Vec3 scale;
    protected String type;
    protected float opacity;
    
    public ObstacleFactoryRequest(Vec3 pos, Vec3 scale, String type)
    {
        this(pos,scale,type,1.0f);
    }

    public ObstacleFactoryRequest(Vec3 pos, Vec3 scale, String type, float opacity)
    {
        this.pos = pos;
        this.scale = scale;
        this.type = type;
        this.opacity = opacity;
    }
    
    
}
