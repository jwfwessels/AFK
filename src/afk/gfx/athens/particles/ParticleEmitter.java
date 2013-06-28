package afk.gfx.athens.particles;

import afk.gfx.Camera;
import afk.gfx.athens.Mesh;
import afk.gfx.athens.Shader;
import afk.gfx.athens.Texture;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class ParticleEmitter
{
    /// PARAMETERS ///
    
    Vec3 minPosition, maxPosition;
    Vec3 direction, acceleration;
    float angleJitter;
    float minSpeed, maxSpeed;
    
    /** Time between spawns. Use zero for "explosion" */
    float spawnRate;
    
    /// RESOURCES ///
    
    public Mesh mesh;
    public Texture texture;
    public Shader shader;
    
    /// STUFF ///
    
    Random rand = new Random();
    
    
    Vec3 tangent, bitangent;
    
    private float timeSinceLastSpawn = 0;
    public boolean active = false;
    
    int numParticles = 0;
    
    // TODO: make a global object pool?
    Particle[] particles;
    private Queue<Particle> available = new ConcurrentLinkedQueue<Particle>();

    public ParticleEmitter(Vec3 position, Vec3 positionJitter, Vec3 direction,
            float angleJitter, float speed, float speedJitter, Vec3 acceleration,
            int maxParticles, float spawnRate)
    {
        minPosition = position.subtract(positionJitter);
        maxPosition = position.add(positionJitter);
        this.direction = direction;
        this.acceleration = acceleration;
        
        // find best cardinal axis
        float xs = position.getX(); xs *= xs;
        float ys = position.getY(); ys *= ys;
        float zs = position.getZ(); zs *= zs;
        Vec3 cardinal;
        if (ys < zs && ys < xs)
            cardinal = new Vec3(0,1,0);
        else if (zs < xs && zs < ys)
            cardinal = new Vec3(0,0,1);
        else
            cardinal = new Vec3(1,0,0);
        
        tangent = direction.cross(cardinal).getUnitVector();
        bitangent = direction.cross(tangent).getUnitVector();
        
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
        // update each particle
        for (int i = 0; i < particles.length; i++)
        {
            if (particles[i].alive)
                particles[i].update(delta, acceleration);
            
            // check if particle still alive after update
            if (!particles[i].alive)
                available.add(particles[i]);
        }
        
        // spawn new particles
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
            else // explode!
            {
                for (int i = 0; i < particles.length; i++)
                {
                    spawn();
                }
                active = false;
            }
        }
    }
    
    public void draw(GL2 gl, Camera camera, Vec3 sun)
    {
        shader.use(gl);
        
        shader.updateUniform(gl, "view", camera.view);
        shader.updateUniform(gl, "projection", camera.projection);
        
        shader.updateUniform(gl, "sun", sun);
        shader.updateUniform(gl, "eye", camera.eye);
        
        for (int i = 0; i < particles.length; i++)
        {
            if (particles[i].alive)
            {
                particles[i].draw(gl, mesh, camera, shader);
            }
        }
    }
    
    private float randomLerp(float a, float b)
    {
        return a + (b - a) * rand.nextFloat();
    }
    
    private void spawn()
    {
        Particle p = available.poll();
        if (p != null)
        {
            Vec3 pos = new Vec3(
                    randomLerp(minPosition.getX(), maxPosition.getX()),
                    randomLerp(minPosition.getY(), maxPosition.getY()),
                    randomLerp(minPosition.getZ(), maxPosition.getZ())
                );
            
            Vec3 dir;
            
            if (angleJitter > 0)
            {
                // uniform cone distribution
                // TODO: doesn't seem to work right
                
                float phi = (rand.nextFloat()*2.0f-1.0f)*(float)Math.PI;
                float theta = rand.nextFloat()*angleJitter;

                // sinθ(cosϕu+sinϕv)+cosθa 
                dir = (
                        tangent.scale((float)Math.cos(phi))
                        .add(bitangent.scale((float)Math.sin(phi)))
                    ).scale((float)Math.sin(theta))
                    .add(direction.scale((float)Math.sin(theta)))
                    .getUnitVector();
            }
            else
            {
                 // uniform sphere distribution
                dir = new Vec3(
                        (float)rand.nextGaussian(),
                        (float)rand.nextGaussian(),
                        (float)rand.nextGaussian()
                    )
                    .getUnitVector();
            }
            
            float speed = randomLerp(minSpeed, maxSpeed);
            
            p.set(pos, dir.scale(speed));
        }
    }
}
