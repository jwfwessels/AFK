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
    protected boolean visible;
    
    public ObstacleFactoryRequest(Vec3 pos, Vec3 scale, String type)
    {
        this(pos,scale,type,true);
    }

    public ObstacleFactoryRequest(Vec3 pos, Vec3 scale, String type, boolean visible)
    {
        this.pos = pos;
        this.scale = scale;
        this.type = type;
        this.visible = visible;
    }
    
    
}
