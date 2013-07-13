package afk.gfx;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Represents a graphical resource that can be loaded, unloaded, and attached to
 * a graphics entity.
 * @author Daniel
 */
public abstract class Resource
{
    // TODO: types to add: imposters, sounds (although not necessarily part of the gfx engine)
    /**
     * Resource types.
     */
    public static final int WAVEFRONT_MESH = 0, PRIMITIVE_MESH = 1,
            HEIGHTMAP_MESH = 2, TEXTURE_2D = 3, TEXTURE_CUBE = 4, MATERIAL = 5,
            SHADER = 6, PARTICLE_PARAMETERS = 7;
    
    /** The number of resource types currently accounted for. */
    public static final int NUM_RESOURCE_TYPES = 8;
    
    /** The type of this resource. */
    protected int type;
    /** The name of this resource. */
    protected String name;
    
    /** True if the resource is loaded, false otherwise. */
    protected AtomicBoolean loaded = new AtomicBoolean(false);

    /**
     * Creates a new resource with the specified type and name.
     * @param type the resource type.
     * @param name the resource name.
     */
    public Resource(int type, String name)
    {
        this.type = type;
        this.name = name;
    }

    /**
     * Gets the resource name.
     * @return the resource name.
     */
    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return getName();
    }

    /**
     * Gets the resource type.
     * @return the resource type.
     */
    public int getType()
    {
        return type;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Resource)
        {
            Resource other = (Resource) obj;
            if (this.name == null)
            {
                if (other.name != null) return false;
            }
            else if (!this.name.equalsIgnoreCase(other.name)) return false;
            
            return other.type == this.type;
        }
        return false;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + this.type;
        hash = 37 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * Checks the load status of the resource.
     * @return true if the resource is currently loaded in memory, false
     * otherwise.
     */
    public boolean isLoaded()
    {
        return loaded.get();
    }
    
}
