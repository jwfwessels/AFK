package afk.gfx.athens;

import afk.gfx.Resource;
import java.io.IOException;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public abstract class AthensResource extends Resource
{

    public AthensResource(int type, String name)
    {
        super(type, name);
    }
    
    public abstract void load(GL2 gl) throws IOException;
    public abstract void unload(GL2 gl);
    
    
}
