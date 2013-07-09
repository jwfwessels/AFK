package afk.gfx;

/**
 *
 * @author Daniel
 */
public abstract class Resource
{
    // TODO: types to add: bilboards and imposters, sounds (although not necessarily part of the gfx engine)
    public static final int WAVEFRONT_MESH = 0, PRIMITIVE_MESH = 1,
            HEIGHTMAP_MESH = 2, TEXTURE_2D = 3, TEXTURE_CUBE = 4, MATERIAL = 5,
            SHADER = 6, PARTICLE_PARAMETERS = 7;
    
    protected int type;
    protected String name;

    public Resource(int type, String name)
    {
        this.type = type;
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public String toString()
    {
        return getName();
    }

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
    
}
