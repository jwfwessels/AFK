package afk.gfx.athens;

import afk.gfx.Resource;
import afk.gfx.athens.particles.ParticleParameters;
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
    
    public static AthensResource create(int type, String name)
    {
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
                // TODO:
            break;
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
