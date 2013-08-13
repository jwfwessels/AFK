package afk.gfx;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.Random;

/**
 *
 * @author Daniel
 */
public class GfxUtils
{
    public static final Random random = new Random();
    
    // direction vector to be rotated
    // represents a rotation of 0, 0, 0 degrees
    // TODO: this whole thing should probably be sorted out with quaternions :/
    public static final Vec4 ANCHOR = new Vec4(1.0f,0.0f,0.0f,0.0f);
    
    public static final Vec3 X_AXIS = new Vec3(1,0,0);
    public static final Vec3 Y_AXIS = new Vec3(0,1,0);
    public static final Vec3 Z_AXIS = new Vec3(0,0,1);
    
    public static final long NANOS_PER_SECOND = 1000000000l;
    
    
    public static float jitter(float x, float j)
    {
        return randomLerp(x-j, x+j);
    }
    
    public static float gaussJitter(float x, float j)
    {
        return x + (float)random.nextGaussian()*j;
    }
    
    public static float randomLerp(float a, float b)
    {
        return a + (b - a) * random.nextFloat();
    }
}
