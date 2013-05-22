package afk.gfx.athens;


import com.jogamp.common.nio.Buffers;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * Abstract Mesh class. Stores a displaylist in a handle.
 * @author daniel
 */
public abstract class Mesh
{
    protected int handle;
    
    public static final int VERTICES_PER_TRIANGLE = 3;
    public static final int TRIANGLES_PER_QUAD = 2;
    public static final int VERTICES_PER_QUAD = VERTICES_PER_TRIANGLE * TRIANGLES_PER_QUAD;
    public static final int DIMESIONS = 3;

    public Mesh(GL2 gl)
    {
        handle = gl.glGenLists(1);
    }
    
    public void draw(GL2 gl)
    {
       gl.glCallList(handle);
    }
}
