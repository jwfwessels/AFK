package afk.gfx.athens.particles;

import afk.gfx.Camera;
import afk.gfx.Resource;
import afk.gfx.athens.AthensEntity;
import static afk.gfx.GfxUtils.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class ParticleEmitter extends AthensEntity
{
    // direction vector to be rotated
    // represents a rotation of 0, 0, 0 degrees
    // TODO: this whole thing should probably be sorted out with quaternions :/
    private static final Vec4 ANCHOR = new Vec4(1.0f,0.0f,0.0f,0.0f);
    
    /// STUFF ///
    
    private Random rand = new Random();
    
    private float timeSinceLastSpawn = 0;
    
    // TODO: make a global object pool?
    private Particle[] particles;
    private Queue<Particle> available = new ConcurrentLinkedQueue<Particle>();
    
    private ParticleParameters particleParams;

    public ParticleEmitter()
    {
        active = false;
    }

    @Override
    public void attachResource(Resource resource)
    {
        if (resource.getType() == Resource.PARTICLE_PARAMETERS)
        {
            particleParams = (ParticleParameters)resource;
            
            particles = new Particle[particleParams.numParticles];
            for (int i = 0; i < particles.length; i++)
            {
                particles[i] = new Particle();
                available.add(particles[i]);
            }
        }
        else super.attachResource(resource);
    }
    
    @Override
    protected void update(float delta)
    {
        // update each particle
        for (int i = 0; i < particles.length; i++)
        {
            if (particles[i].alive)
                particles[i].update(delta, particleParams.acceleration,
                        particleParams.boundingBox);
            
            // check if particle still alive after update
            if (!particles[i].alive)
                available.add(particles[i]);
        }
        
        // spawn new particles
        if (active)
        {
            if (particleParams.spawnInterval > 0)
            {
                timeSinceLastSpawn += delta;

                while (timeSinceLastSpawn > particleParams.spawnInterval)
                {
                    timeSinceLastSpawn -= particleParams.spawnInterval;
                    spawn(timeSinceLastSpawn);
                }
            }
            else // explode!
            {
                for (int i = 0; i < particles.length; i++)
                {
                    spawn(0);
                }
                active = false;
            }
        }
    }
    
    @Override
    public void draw(GL2 gl, Camera camera, Vec3 sun)
    {
        if (shader != null)
        {
            shader.use(gl);

            if (texture != null)
            {
                texture.use(gl, GL2.GL_TEXTURE0);
                shader.updateUniform(gl, "tex", 0);
            }

            shader.updateUniform(gl, "view", camera.view);
            shader.updateUniform(gl, "projection", camera.projection);

            shader.updateUniform(gl, "sun", sun);
            shader.updateUniform(gl, "eye", camera.eye);

            if (colour != null)
                shader.updateUniform(gl, "colour", colour);
            
            shader.updateUniform(gl, "opacity", opacity);
        }
        
        for (int i = 0; i < particles.length; i++)
        {
            if (particles[i].alive)
            {
                particles[i].draw(gl, mesh, camera, getShader());
            }
        }
    }
    
    private float jitter(float x, float j)
    {
        return randomLerp(x-j, x+j);
    }
    
    private float randomLerp(float a, float b)
    {
        return a + (b - a) * rand.nextFloat();
    }
    
    private void spawn(float delta)
    {
        Vec3 move = getWorldPosition();
        Vec3 rot = getWorldRotation();
        Vec3 scale = getWorldScale();
        
        Particle p = available.poll();
        if (p == null) return;
        if (p.alive)
        {
            available.add(p);
            return;
        }
            
        Vec3 pos = new Vec3(
                jitter(move.getX(), scale.getX()),
                jitter(move.getY(), scale.getY()),
                jitter(move.getZ(), scale.getZ())
            );

        Vec3 dir;


        if (particleParams.noDirection)
        {
            // uniform sphere distribution
            dir = new Vec3(
                    (float)rand.nextGaussian(),
                    (float)rand.nextGaussian(),
                    (float)rand.nextGaussian()
                )
                .getUnitVector();
        }
        else
        {
            Mat4 rotation = Matrices.rotate(new Mat4(1.0f), jitter(rot.getX(), particleParams.angleJitter.getX()), X_AXIS);
            rotation = Matrices.rotate(rotation, jitter(rot.getY(), particleParams.angleJitter.getY()), Y_AXIS);
            rotation = Matrices.rotate(rotation, jitter(rot.getZ(), particleParams.angleJitter.getZ()), Z_AXIS);

            Vec4 newRotation = rotation.multiply(ANCHOR);

            dir = new Vec3(newRotation.getX(),newRotation.getY(),newRotation.getZ());
        }

        float speed = jitter(particleParams.speed, particleParams.speedJitter);

        p.set(pos, dir.scale(speed),
                jitter(particleParams.maxLife, particleParams.lifeJitter),
                jitter(particleParams.scale, particleParams.scaleJitter));
        p.update(delta, particleParams.acceleration,
                particleParams.boundingBox);
    }
}
