package afk.ge.tokyo.ems.factories;

import afk.bot.Robot;
import afk.ge.ems.FactoryRequest;
import com.hackoeur.jglm.Vec3;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class RobotFactoryRequest implements FactoryRequest
{
    protected Robot robot;
    protected Vec3 spawn;
    protected Vec3 colour;

    public RobotFactoryRequest(Robot robot, Vec3 spawn, Vec3 colour)
    {
        this.robot = robot;
        this.spawn = spawn;
        this.colour = colour;
    }
    
    
}
