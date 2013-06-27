package afk.gfx.athens.particles;

import afk.gfx.Camera;
import afk.gfx.athens.BillboardQuad;
import afk.gfx.athens.Mesh;
import afk.gfx.athens.NDCQuad;
import afk.gfx.athens.Shader;
import afk.gfx.athens.WavefrontMesh;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
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

    // TODO: sort this nonsense out:
    protected static Mesh BBMESH = null;
    
    protected Particle(GL2 gl)
    {
        if (BBMESH == null) 
//            try { BBMESH = new WavefrontMesh(gl, "tank.obj"); }
//            catch (IOException ex) { ex.printStackTrace(System.err); }
            BBMESH = new BillboardQuad(gl);
    }
    
    protected void set(Vec3 position, Vec3 velocity)
    {
        this.position = position;
        this.velocity = velocity;
        this.lifetime = 0;
        alive = true;
    }
    
    protected void update(float delta, Vec3 acceleration)
    {
        // TODO: possibly do interpolation or other fancy physics stuff
        position = position.add(velocity.scale(delta));
        velocity = velocity.add(acceleration.scale(delta));
        
        lifetime += delta;
        
        // TODO: check stopping conditions
        // for now (testing) we'll just destroy when reaching y=0
        if (position.getY() < 0)
            alive = false;
    }
    
    protected Mat4 createWorldMatrix(Camera camera)
    {
        Mat4 world = new Mat4(1f);

        world = Matrices.translate(world, position);
        
        float x = camera.dir.getX();
        float y = camera.dir.getY();
        float z = camera.dir.getZ();
        
        float roty, rotr;
        
        if (z >= 0)
            roty = -(float)FastMath.toDegrees(FastMath.atan2(z, x));
        else
            roty = (float)FastMath.toDegrees(FastMath.atan2(-z, x));
        
        if (y >= 0)
            rotr = -(float)FastMath.toDegrees(FastMath.atan2(-y, FastMath.sqrtFast(x*x+z*z)));
        else
            rotr = (float)FastMath.toDegrees(FastMath.atan2(y, FastMath.sqrtFast(x*x+z*z)));
        
        world = Matrices.rotate(
                world,
                rotr,
                camera.right);
        world = Matrices.rotate(
                world,
                roty,
                new Vec3(0,1,0));
        
        //TODO: world = Matrices.scale(world, getScale());
        
        return world;
    }
    
    protected void draw(GL2 gl, Camera camera, Shader shader)
    {
        
        // TODO: figure out how to do texturing. May only allow single texture, but could allow multitexturing or bump/normal mapping later
        //tex.use(gl, GL.GL_TEXTURE0);
        //shader.updateUniform(gl, "tex", 0);
        
        shader.updateUniform(gl, "world", createWorldMatrix(camera));
        
        BBMESH.draw(gl);
    }
}
