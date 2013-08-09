package afk.ge.tokyo.ems.components;

import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class Renderable {
    public String mesh;
    public String shader;
    public String texture;
    public Vec3 colour;
    
    // FIXME: hack
    public GfxEntity gfx;
    
    // TODO: ResourcePackage resources;

    public Renderable(String mesh, String shader, String texture, Vec3 colour, GfxEntity gfx)
    {
        this.mesh = mesh;
        this.shader = shader;
        this.texture = texture;
        this.colour = colour;
        this.gfx = gfx;
    }
}
