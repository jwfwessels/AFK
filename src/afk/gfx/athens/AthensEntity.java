package afk.gfx.athens;

import afk.gfx.athens.particles.ParticleParams;
import afk.gfx.Camera;
import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import javax.media.opengl.GL2;

/**
 *
 * @author daniel
 */
public class AthensEntity extends GfxEntity
{
    public static final Vec3 X_AXIS = new Vec3(1,0,0);
    public static final Vec3 Y_AXIS = new Vec3(0,1,0);
    public static final Vec3 Z_AXIS = new Vec3(0,0,1);
    
    protected Mesh mesh = null;
    protected Texture texture = null;
    protected Object material = null; // TODO: placeholder for materials in future
    protected Shader shader = null;
    protected ParticleParams particleParams;
    
    public boolean active = true;
    
    protected Mat4 createWorldMatrix()
    {
        Mat4 monkeyWorld = new Mat4(1f);

        monkeyWorld = Matrices.translate(monkeyWorld, getPosition());

        monkeyWorld = Matrices.rotate(monkeyWorld, yRot, Y_AXIS);
        
        monkeyWorld = Matrices.rotate(monkeyWorld, xRot, X_AXIS);

        monkeyWorld = Matrices.rotate(monkeyWorld, zRot, Z_AXIS);

        monkeyWorld = Matrices.scale(monkeyWorld, getScale());
        
        return monkeyWorld;
    }
    
    protected void update(float delta)
    {
        // do nothing
    }
    
    protected void draw(GL2 gl, Camera camera, Vec3 sun) // TODO: replace with Camera and Sun/Light objects later
    {
        // by default, active sets visibility of entity
        if (!active) return;
        
        shader.use(gl);
        
        // TODO: figure out how to do texturing. May only allow single texture, but could allow multitexturing or bump/normal mapping later
        //tex.use(gl, GL.GL_TEXTURE0);
        //shader.updateUniform(gl, "tex", 0);
        
        shader.updateUniform(gl, "world", createWorldMatrix());
        shader.updateUniform(gl, "view", camera.view);
        shader.updateUniform(gl, "projection", camera.projection);
        
        shader.updateUniform(gl, "sun", sun);
        shader.updateUniform(gl, "eye", camera.eye);
        
        if (colour != null)
            shader.updateUniform(gl, "colour", colour);
        
        mesh.draw(gl);
    }
    
    // TODO: add more functions for attaching each kind of resource
    
    public void attachResource(ParticleParams particleParams)
    {
        this.particleParams = particleParams;
    }
}
