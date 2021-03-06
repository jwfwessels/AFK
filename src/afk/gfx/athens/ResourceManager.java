/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
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
 package afk.gfx.athens;

import afk.gfx.Resource;
import static afk.gfx.Resource.*;
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
                else if ("skybox".equalsIgnoreCase(name))
                    return new SkyBox();
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
            default:
                // TODO: throw something
            break;
        }
        return null;
    }
}
