package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import afk.ge.tokyo.ems.components.Bullet;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.components.Weapon;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class ProjectileFactoryRequest implements FactoryRequest
{
    protected Vec3 pos;
    protected Vec4 rot;
    protected Vec3 scale;
    protected Vec3 forward;
    protected String type;
    protected Vec3 colour;
    protected Weapon weapon;
    protected UUID parent;

    public ProjectileFactoryRequest(Vec3 pos, Vec4 rot, Vec3 scale, Vec3 forward, String type, Vec3 colour, Weapon weapon, UUID parent)
    {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        this.forward = forward;
        this.type = type;
        this.colour = colour;
        this.weapon = weapon;
        this.parent = parent;
    }
    
    
    
    
}
