package afk.gfx.athens;

import afk.gfx.Resource;
import static afk.gfx.Resource.*;
import afk.gfx.athens.particles.ParticleParameters;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import javax.media.opengl.GL2;

/**
 *
 * @author daniel
 */
public class ResourceManager
{
    private Map<String, AthensResource>[] resources = new Map[Resource.NUM_RESOURCE_TYPES];
    private Map<String, AthensEntity> types = new HashMap<String, AthensEntity>();
    
    protected Queue<AthensResource> loadQueue = new LinkedList<AthensResource>();
    protected Queue<AthensResource> unloadQueue = new LinkedList<AthensResource>();
    
    public ResourceManager()
    {
        for (int i = 0; i < resources.length; i++)
        {
            resources[i] = new HashMap<String, AthensResource>();
        }
    }
    
    public void update(GL2 gl)
    {
        // unload resources in unload queue
        while (!unloadQueue.isEmpty())
        {
            AthensResource resource = unloadQueue.poll();
            System.out.println("Unloading " + resource);

            resources[resource.getType()].remove(resource.getName()).unload(gl);
        }

        // load resources in load queue
        while (!loadQueue.isEmpty())
        {
            AthensResource resource = loadQueue.poll();
            System.out.println("Loading " + resource);
            try
            {
                resource.load(gl);
                resources[resource.getType()].put(resource.getName(), resource);
            } catch (IOException ex)
            {
                System.err.println("Unable to load " + resource + ": " + ex.getMessage());
            }
        }
    }
    
    public void dispose(GL2 gl)
    {
        for (int i = 0; i < resources.length; i++)
        {
            for (Map.Entry<String, AthensResource> entry : resources[i].entrySet())
            {
                AthensResource resource = entry.getValue();
                System.out.println("Unloading " + resource);
                resource.unload(gl);
            }
        }
    }
    
    public Resource getResource(int type, String name)
    {
        AthensResource resource = resources[type].get(name);

        if (resource == null)
        {
            resource = create(type, name);
            resources[type].put(name, resource);
            
            if (!unloadQueue.remove(resource) )
            {
                loadQueue.add(resource);
            }
        }
        
        return resource;
    }
    
    public void unloadResource(Resource resource)
    {
        // can't unload resource that isn't loaded
        if (resources[resource.getType()].containsKey(resource.getName())
                && !loadQueue.remove((AthensResource) resource))
        {
            unloadQueue.add((AthensResource) resource);
        }
        
    }
    
    private static AthensResource create(int type, String name)
    {
        // TODO: maybe look into making this a bit more flexible...?
        
        switch (type)
        {
            case WAVEFRONT_MESH:
                return new WavefrontMesh(name);
            case PRIMITIVE_MESH:
                if (BillboardQuad.NAME.equalsIgnoreCase(name))
                    return new BillboardQuad();
                else if (Quad.NAME.equalsIgnoreCase(name))
                    return new Quad();
                else
                {
                    // TODO: throw something
                }
            break;
            case HEIGHTMAP_MESH:
                return new AthensTerrain(name);
            case TEXTURE_2D:
                return new Texture2D(name);
            case TEXTURE_CUBE:
                return new TextureCubeMap(name);
            case MATERIAL:
                // TODO:
            break;
            case SHADER:
                return new Shader(name);
            case PARTICLE_PARAMETERS:
                return new ParticleParameters(name);
            default:
                // TODO: throw something
            break;
        }
        return null;
    }
}
