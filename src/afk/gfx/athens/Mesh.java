package afk.gfx.athens;

import java.io.IOException;
import javax.media.opengl.GL2;

/**
 * Abstract Mesh class. Stores a displaylist in a handle.
 * @author daniel
 */
public abstract class Mesh extends AthensResource
{
    protected int handle;
    
    public static final int VERTICES_PER_TRIANGLE = 3;
    public static final int TRIANGLES_PER_QUAD = 2;
    public static final int VERTICES_PER_QUAD = VERTICES_PER_TRIANGLE * TRIANGLES_PER_QUAD;
    public static final int DIMESIONS = 3;

    public Mesh(int type, String name)
    {
        super(type,name);
    }
    
    public void draw(GL2 gl)
    {
       gl.glCallList(handle);
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        handle = gl.glGenLists(1);
    }
    
    @Override
    public void unload(GL2 gl)
    {
        gl.glDeleteLists(handle, 1);
    }
}
