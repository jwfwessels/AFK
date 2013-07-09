/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens.particles;

import afk.gfx.athens.AthensResource;
import com.hackoeur.jglm.Vec3;
import com.jogamp.graph.geom.AABBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class ParticleParameters extends AthensResource
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

    public ParticleParameters(String name)
    {
        super(PARTICLE_PARAMETERS, name);
    }

    @Override
    public void load(GL2 gl) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("particles/"+name+".px"));
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
                        this.acceleration = parseVec3(value);
                    else if ("angleJitter".equals(param))
                        this.angleJitter = parseVec3(value);
                    else if ("speed".equals(param))
                        this.speed = Float.parseFloat(value);
                    else if ("speedJitter".equals(param))
                        this.speedJitter = Float.parseFloat(value);
                    else if ("spawnInterval".equals(param))
                        this.spawnInterval = Float.parseFloat(value);
                    else if ("maxLife".equals(param))
                        this.maxLife = Float.parseFloat(value);
                    else if ("lifeJitter".equals(param))
                        this.lifeJitter = Float.parseFloat(value);
                    else if ("numParticles".equals(param))
                        this.numParticles = Integer.parseInt(value);
                    else if ("noDirection".equals(param))
                        this.noDirection = Boolean.parseBoolean(value);
                    else if ("boundingBox".equals(param))
                        this.boundingBox = parseAABBox(value);
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
    }

    @Override
    public void unload(GL2 gl)
    {
        // TODO: nothing to unload?
    }
}
