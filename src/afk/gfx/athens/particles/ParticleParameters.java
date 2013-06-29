/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens.particles;

import com.hackoeur.jglm.Vec3;
import com.jogamp.graph.geom.AABBox;

/**
 *
 * @author Daniel
 */
public class ParticleParameters
{
    /** Uniform linear acceleration of particles. Useful for gravity and maybe wind. */
    Vec3 acceleration;
    /** Deviation of initial direction of particles. */
    Vec3 angleJitter;
    /** Base initial speed (magnitude of velocity) of particles. */
    float speed;
    /** Deviation of initial speed of particles. */
    float speedJitter;
    /**
     * Time between particle spawns (in seconds). If this is set to exactly zero, the emitter
     * will become and explosion, effectively emitting all particles at once and
     * then deactivating.
     */
    float spawnInterval;
    /** Maximum lifetime (in seconds) that a particle may exist for. */
    float maxLife;
    /** Maximum number of particles. */
    int numParticles; // TODO: maybe replace with a "density factor" to allow global control of particle count
    /** If true, particles will disperse in a uniform direction. */
    boolean noDirection;
    /** Bounding box within which particles are allowed to exists.
     Can be null to represent infinite space. */
    AABBox boundingBox;
    
    

    // TODO: allow loading from file
    public ParticleParameters(Vec3 acceleration, Vec3 angleJitter, float speed,
            float speedJitter, float spawnInterval, float maxLife, int numParticles,
            boolean noDirection, Vec3[] bb)
    {
        this.acceleration = acceleration;
        this.angleJitter = angleJitter;
        this.speed = speed;
        this.speedJitter = speedJitter;
        this.spawnInterval = spawnInterval;
        this.maxLife = maxLife;
        this.numParticles = numParticles;
        this.noDirection = noDirection;
        if (bb != null)
            boundingBox = new AABBox(bb[0].getX(), bb[0].getY(), bb[0].getZ(),
                    bb[1].getX(), bb[1].getY(), bb[1].getZ());
        else
            boundingBox = null;
    }
}
