package afk.gfx.athens.particles;

import afk.gfx.athens.Mesh;
import afk.gfx.athens.NDCQuad;
import afk.gfx.athens.Shader;
import afk.gfx.athens.WavefrontMesh;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import java.io.IOException;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class Particle
{
    Vec3 position;
    Vec3 velocity;
    float lifetime;
    boolean alive = false;

    public static Mesh BBMESH = null;
    
    public Particle(GL2 gl)
    {
        if (BBMESH == null) 
            try { BBMESH = new WavefrontMesh(gl, "tank.obj"); }
            catch (IOException ex) { ex.printStackTrace(System.err); }
    }
    
    public void set(Vec3 position, Vec3 velocity)
    {
        this.position = position;
        this.velocity = velocity;
        this.lifetime = 0;
        alive = true;
    }
    
    public void update(float delta, Vec3 acceleration)
    {
        // TODO: possibly do interpolation or other fancy physics stuff
        position = position.add(velocity.scale(delta));
        velocity = velocity.add(acceleration.scale(delta));
        
        lifetime += delta;
        
        // TODO: check stopping conditions
        // for now we just destroy when reaching y=0
        if (position.getY() < 0)
            alive = false;
    }
    
    protected Mat4 createWorldMatrix()
    {
        Mat4 world = new Mat4(1f);

        world = Matrices.translate(world, position);

        //TODO: world = Matrices.scale(world, getScale());
        world = Matrices.scale(world, new Vec3(0.5f,0.5f,0.5f));
        
        return world;
    }
    
    protected void draw(GL2 gl, Shader shader)
    {
        
        // TODO: figure out how to do texturing. May only allow single texture, but could allow multitexturing or bump/normal mapping later
        //tex.use(gl, GL.GL_TEXTURE0);
        //shader.updateUniform(gl, "tex", 0);
        
        shader.updateUniform(gl, "world", createWorldMatrix());
        
        BBMESH.draw(gl);
    }
}
