/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;
import com.jogamp.graph.geom.AABBox;

/**
 *
 * @author jwfwessels
 */
public class ParticleEmitter
{
    /** Uniform linear acceleration of particles. Useful for gravity and maybe wind. */
    public Vec3 acceleration = Vec3.VEC3_ZERO;
    
    /** Deviation of initial direction of particles. */
    public Vec3 angleJitter = Vec3.VEC3_ZERO;
    
    /** Base initial speed (magnitude of velocity) of particles. */
    public float speed = 0;
    
    /** Deviation of initial speed of particles. */
    public float speedJitter = 0;
    
    /** Base scale of particles. */
    public float scale = 1;
    
    /** Deviation of scale of particles. */
    public float scaleJitter = 0;
    
    /**
     * Time between particle spawns (in seconds). If this is set to exactly zero, the emitter
     * will become and explosion, effectively emitting all particles at once and
     * then deactivating.
     */
    public float spawnInterval = 0;
    
    public float timeSinceLastSpawn = 0;
    
    /** Maximum lifetime (in seconds) that a particle may exist for. */
    public float maxLife = Float.POSITIVE_INFINITY;
    
    /** Deviation of maximum lifetime (in seconds). */
    public float lifeJitter = 0;
    
    /** Number of particles (if explosion). */
    public int numParticles = 1;
    
    /** If true, particles will disperse in a uniform direction. */
    public boolean noDirection = false;
    
    /** Colour of spawned particles. */
    public Vec3 colour = Vec3.VEC3_ZERO;
    
    /**
     * Bounding box within which particles are allowed to exists.
     * Can be null to represent infinite space.
     */
    public AABBox boundingBox = null;

    public ParticleEmitter()
    {
    }

    public ParticleEmitter(ParticleEmitter other)
    {
        this.acceleration = other.acceleration;
        this.angleJitter = other.angleJitter;
        this.boundingBox = other.boundingBox;
        this.colour = other.colour;
        this.lifeJitter = other.lifeJitter;
        this.maxLife = other.maxLife;
        this.noDirection = other.noDirection;
        this.numParticles = other.numParticles;
        this.scale = other.scale;
        this.scaleJitter = other.scaleJitter;
        this.spawnInterval = other.spawnInterval;
        this.speed = other.speed;
        this.speedJitter = other.speed;
        this.timeSinceLastSpawn = other.timeSinceLastSpawn;
    }

    
    
    
}
