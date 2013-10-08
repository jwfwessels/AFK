package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import com.hackoeur.jglm.Vec3;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class RobotFactoryRequest implements FactoryRequest
{
    protected UUID id;
    protected Vec3 spawn;
    protected Vec3 colour;

    public RobotFactoryRequest(UUID id, Vec3 spawn, Vec3 colour)
    {
        this.id = id;
        this.spawn = spawn;
        this.colour = colour;
    }
    
    
}
