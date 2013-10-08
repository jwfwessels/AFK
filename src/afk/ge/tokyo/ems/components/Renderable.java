package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class Renderable {
    
    public String type = "";
    public Vec3 colour = Vec3.VEC3_ZERO;

    public Renderable()
    {
    }

    public Renderable(String type, Vec3 colour )
    {
        this.type = type;
        this.colour = colour;
    }
    
}
