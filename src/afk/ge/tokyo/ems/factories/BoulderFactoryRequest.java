package afk.ge.tokyo.ems.factories;

import afk.ge.ems.FactoryRequest;
import com.hackoeur.jglm.Vec3;

/**
 * Request for a boulder to be produced from a BoulderFactory.
 * @author daniel
 */
public class BoulderFactoryRequest implements FactoryRequest
{
    protected float minX, maxX, minZ, maxZ;
    protected float minScale, maxScale;
    protected float groundSink;
    protected Vec3[] avoidPoints;
    protected float avoidance;
    protected float tiltAmount;

    public BoulderFactoryRequest(float minX, float maxX, float minZ, float maxZ, float minScale, float maxScale, float groundSink, Vec3[] avoidPoints, float avoidance, float tiltAmount)
    {
        this.minX = minX;
        this.maxX = maxX;
        this.minZ = minZ;
        this.maxZ = maxZ;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.groundSink = groundSink;
        this.avoidPoints = avoidPoints;
        this.avoidance = avoidance;
        this.tiltAmount = tiltAmount;
    }
}
