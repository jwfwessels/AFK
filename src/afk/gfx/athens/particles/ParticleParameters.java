/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens.particles;

import com.hackoeur.jglm.Vec3;
import com.jogamp.graph.geom.AABBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Daniel
 */
public class ParticleParameters
{
    /** Uniform linear acceleration of particles. Useful for gravity and maybe wind. */
    Vec3 acceleration = Vec3.VEC3_ZERO;
    
    /** Deviation of initial direction of particles. */
    Vec3 angleJitter = Vec3.VEC3_ZERO;
    
    /** Base initial speed (magnitude of velocity) of particles. */
    float speed = 0;
    
    /** Deviation of initial speed of particles. */
    float speedJitter = 0;
    
    /**
     * Time between particle spawns (in seconds). If this is set to exactly zero, the emitter
     * will become and explosion, effectively emitting all particles at once and
     * then deactivating.
     */
    float spawnInterval = 0;
    
    /** Maximum lifetime (in seconds) that a particle may exist for. */
    float maxLife = Float.POSITIVE_INFINITY;
    
    /** Deviation of maximum lifetime (in seconds). */
    float lifeJitter = 0;
    
    /** Maximum number of particles. */
    
    int numParticles = 1; // TODO: maybe replace with a "density factor" to allow global control of particle count
    
    /** If true, particles will disperse in a uniform direction. */
    boolean noDirection = false;
    
    /**
     * Bounding box within which particles are allowed to exists.
     * Can be null to represent infinite space.
     */
    AABBox boundingBox = null;
    
    /**
     * Loads a set of particle parameters from file.
     * @param name Name of the particle system
     * @return An object representing the particle parameters in the file.
     * @throws IOException If an error occurred loading or parsing the file.
     */
    public static ParticleParameters loadFromFile(String name)
            throws IOException
    {
        ParticleParameters params = new ParticleParameters();
        BufferedReader br = new BufferedReader(new FileReader(name));
        try
        {
            
            String line;
            for(int lineNumber = 1; (line = br.readLine()) != null; lineNumber++)
            {
                line = line.replaceFirst(";.*$", "").trim();
                
                if (line.isEmpty()) continue;
                
                String[] split1 = line.split("\\s*=\\s*");
                
                String param = split1[0];
                String value = split1[1];
                
                try
                {
                    if ("acceleration".equals(param))
                        params.acceleration = parseVec3(value);
                    else if ("angleJitter".equals(param))
                        params.angleJitter = parseVec3(value);
                    else if ("speed".equals(param))
                        params.speed = Float.parseFloat(value);
                    else if ("speedJitter".equals(param))
                        params.speedJitter = Float.parseFloat(value);
                    else if ("spawnInterval".equals(param))
                        params.spawnInterval = Float.parseFloat(value);
                    else if ("maxLife".equals(param))
                        params.maxLife = Float.parseFloat(value);
                    else if ("lifeJitter".equals(param))
                        params.lifeJitter = Float.parseFloat(value);
                    else if ("numParticles".equals(param))
                        params.numParticles = Integer.parseInt(value);
                    else if ("noDirection".equals(param))
                        params.noDirection = Boolean.parseBoolean(value);
                    else if ("boundingBox".equals(param))
                        params.boundingBox = parseAABBox(value);
                    else
                        throw new IOException("Invalid parameter name on line " + lineNumber);
                }
                catch (NumberFormatException nfe)
                {
                    throw new IOException("Error on line " + lineNumber + ": "
                            + nfe.getMessage());
                }
            }
        }
        finally
        {
            br.close();
        }
        return params;
        
    }
    
    private static Vec3 parseVec3(String values)
    {
        System.out.println("Parsing [" + values + "] as Vec3.");
        String[] split = values.split("\\s+");
        
        if (split.length != 3)
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 3, found " + split.length);
        
        return new Vec3(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2])
            );
    }
    private static AABBox parseAABBox(String values)
    {
        String[] split = values.split("\\s+");
        
        if (split.length != 6)
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 6, found " + split.length);
        
        return new AABBox(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2]),
                Float.parseFloat(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5])
            );
    }
    
    
    private ParticleParameters() {}

    private ParticleParameters(Vec3 acceleration, Vec3 angleJitter, float speed,
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
