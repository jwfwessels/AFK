package afk.gfx.athens.particles;

import com.hackoeur.jglm.Vec3;
import com.jogamp.graph.math.VectorUtil;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 *
 * @author Daniel
 */
public class ParticleEmitter
{
    /// PARAMETERS ///
    
    Vec3 minPosition, maxPosition;
    Vec3 direction;
    float angleJitter;
    float minSpeed, maxSpeed;
    
    /** Time between spawns. Use zero for "explosion" */
    float spawnRate;
    
    /// STUFF ///
    
    Vec3 tangent, bitangent;
    
    private float timeSinceLastSpawn = 0;
    boolean active = false;
    
    int numParticles = 0;
    
    // TODO: make a global object pool?
    Particle[] particles;
    private Queue<Particle> available = new ConcurrentLinkedQueue<Particle>();

    public ParticleEmitter(Vec3 position, Vec3 positionJitter, Vec3 direction, float angleJitter, float speed, float speedJitter, int maxParticles, float spawnRate)
    {
        minPosition = position.subtract(positionJitter);
        maxPosition = position.add(positionJitter);
        this.direction = direction;
        
        
        
        this.angleJitter = angleJitter;
        minSpeed = speed - speedJitter;
        maxSpeed = speed + speedJitter;
        this.spawnRate = spawnRate;
        
        particles = new Particle[maxParticles];
        for (int i = 0; i < maxParticles; i++)
        {
            particles[i] = new Particle();
            available.add(particles[i]);
        }
    }
    
    public void update(float delta)
    {
        
        if (active)
        {
            if (spawnRate > 0)
            {
                timeSinceLastSpawn += delta;

                while (timeSinceLastSpawn > spawnRate)
                {
                    spawn();
                    timeSinceLastSpawn -= spawnRate;
                }
            }
            else
            {
                for (int i = 0; i < particles.length; i++)
                {
                    spawn();
                }
                active = false;
            }
        }
    }
    
    public void spawn()
    {
        Particle p = available.poll();
        if (p != null)
        {
            Vec3 position = minPosition.lerp(maxPosition, (float)Math.random());
            // sinθ(cosϕu+sinϕv)+cosθa 
            // TODO: Vec3 angle = Math.sin(theta) Math.sin(theta);
            float speed = minSpeed + (maxSpeed - minSpeed) * (float)Math.random();
            
        }
    }
}
