package afk.gfx.athens.particles;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class Particle
{
    Vec3 position;
    Vec3 velocity;
    float lifetime;
    boolean alive = false;

    public Particle()
    { }
    
    public void set(Vec3 position, Vec3 velocity)
    {
        this.position = position;
        this.velocity = velocity;
        this.lifetime = 0;
    }
    
    public void update(float delta, Vec3 acceleration)
    {
        // TODO: possibly do interpolation or other fancy physics stuff
        position = position.add(velocity.scale(delta));
        velocity = velocity.add(acceleration.scale(delta));
        
        lifetime += delta;
        
        // TODO: check stopping conditions
    }
}
