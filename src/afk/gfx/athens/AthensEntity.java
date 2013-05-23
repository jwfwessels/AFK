package afk.gfx.athens;

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
    protected Mesh mesh = null;
    protected Texture texture = null;
    protected Object material = null; // TODO: placeholder for materials in future
    protected Shader shader = null;
    
    protected Mat4 createWorldMatrix()
    {
        Mat4 monkeyWorld = new Mat4(1f);

        monkeyWorld = Matrices.translate(monkeyWorld, position);

        Vec3 yAxis = new Vec3(0, 1, 0);
        monkeyWorld = Matrices.rotate(monkeyWorld, rotation.getY(), yAxis);
        
        Vec3 xAxis = new Vec3(1, 0, 0);
        monkeyWorld = Matrices.rotate(monkeyWorld, rotation.getX(), xAxis);

        Vec3 zAxis = new Vec3(0, 0, 1);
        monkeyWorld = Matrices.rotate(monkeyWorld, rotation.getZ(), zAxis);

        monkeyWorld = Matrices.scale(monkeyWorld, scale);
        
        return monkeyWorld;
    }
    
    protected void draw(GL2 gl, Mat4 camera, Mat4 proj, Vec3 sun, Vec3 eye) // TODO: replace with Camera and Sun/Light objects later
    {
        shader.use(gl);
        
        // TODO: figure out how to do texturing. May only allow single texture, but could allow multitexturing or bump/normal mapping later
        //tex.use(gl, GL.GL_TEXTURE0);
        //shader.updateUniform(gl, "tex", 0);
        
        shader.updateUniform(gl, "world", createWorldMatrix());
        shader.updateUniform(gl, "view", camera);
        shader.updateUniform(gl, "projection", proj);
        
        shader.updateUniform(gl, "sun", sun);
        shader.updateUniform(gl, "eye", eye);
        
        mesh.draw(gl);
    }
}
