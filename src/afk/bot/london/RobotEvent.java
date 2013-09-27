package afk.bot.london;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import afk.ge.tokyo.ems.components.TargetingInfo;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class RobotEvent
{
	/**
	 * @see CollisionSystem
	 */
    public boolean hitWall;

	/**
	 * @see LifeSystem
	 */
    public float hp;
    
	/**
	 * @see MovementSystem
	 */
    public Vec3 pos;
    public Vec4 rot;

    /**
     * @see ProjectileSystem
     */
    public List<UUID> gotHit;
    @Deprecated
    public List<UUID> didHit; //Don't use this until it is actually implemented correctly
    
    /**
     * @see TankBarrelFeedbackSystem
     */
    public float barrel; // TODO: this is just a duplication of the rotation vector W property

    /**
     * @see TankTurretFeedbackSystem
     */
    public float turret; // TODO this is just a duplication of the rotation vector Y property
    
    /**
     * List of visible bots as angles from where tank is facing. Empty if there are none.
     * @see VisionSystem
     */
    public List<TargetingInfo> visibleBots;
    
    public RobotEvent()
    {
        hitWall = false;
        hp = 0;
        pos = new Vec3();
        rot = new Vec4();
        gotHit = new ArrayList<UUID>();
        didHit = new ArrayList<UUID>();
        barrel = 0;
        turret = 0;
        visibleBots = new ArrayList<TargetingInfo>();
    }
    
    @Deprecated
    public List<TargetingInfo> getVisibleBots()
    {
        return new ArrayList<TargetingInfo>(visibleBots);
    }

    @Deprecated
    public boolean gotHit()
    {
        return gotHit.size() > 0;
    }

    @Deprecated
    public boolean didHit()
    {
        return didHit.size() > 0;
    }

    @Deprecated
    public boolean hitWall()
    {
        return hitWall;
    }
    
}
