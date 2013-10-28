/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
