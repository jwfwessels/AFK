package afk.ge.tokyo.ems.components;

import java.util.UUID;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author naude-r
 */
public class TargetingInfo
{
  public final Vec3 pos;
  public final float bearing;
  public final float elevation;
  public final UUID id;
  
  public TargetingInfo(Vec3 pos, float bearing, float elevation, UUID id) {
    this.pos = new Vec3(pos);
    this.bearing = bearing;
    this.elevation = elevation;
    this.id = id;
  }
}
