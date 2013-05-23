package afk.gfx;

/**
 *
 * @author daniel
 */
public class Resource // TODO: make abstract and stuff
{
    // TODO: types to add: bilboards and imposters, sounds (although not necessarily part of the gfx engine)
    public static final int WAVEFRONT_MESH = 0, PRIMITIVE_MESH = 1, HEIGHTMAP_MESH = 2, TEXTURE_2D = 3, TEXTURE_CUBE = 4, MATERIAL = 5, SHADER = 6;
    
    public static final int NUM_MESH_TYPES = 3;
    public static final int NUM_TEX_TYPES = 2;
    public static final int NUM_MAT_TYPES = 1;
    public static final int NUM_SHADER_TYPES = 1;
    
    public static final int NUM_RES_TYPES = NUM_MESH_TYPES + NUM_TEX_TYPES + NUM_MAT_TYPES + NUM_SHADER_TYPES;
    
    protected int type;
    protected String name;

    public Resource(int type, String name)
    {
        this.type = type;
        this.name = name;
    }
    
    // TODO: when this becomes some abstract superclass, the following should be uncommented
    //public abstract void load();
    //public abstract void unload();

    public String getName()
    {
        return name;
    }

    public int getType()
    {
        return type;
    }
    
}
