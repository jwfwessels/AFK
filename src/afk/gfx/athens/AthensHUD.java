package afk.gfx.athens;

import afk.gfx.AbstractCamera;
import afk.gfx.GfxHUD;
import static afk.gfx.athens.Texture.imageToBytes;
import com.hackoeur.jglm.Mat4;
import static com.hackoeur.jglm.Matrices.scale;
import static com.hackoeur.jglm.Matrices.translate;
import com.hackoeur.jglm.Vec3;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class AthensHUD extends Texture implements GfxHUD
{
    private BufferedImage image;
    private AtomicBoolean updated = new AtomicBoolean(true);
    private Quad quad;
    private Shader shader;
    private int x, y;
    
    protected boolean used = false;

    public AthensHUD(BufferedImage image)
    {
        super(-1, null, GL2.GL_TEXTURE_2D);
        this.image = image;
        quad = new Quad();
        shader = new Shader("hud");
    }

    @Override
    public void load(GL2 gl) throws IOException
    {
        super.load(gl);
        
        setup(gl);
        
        setParameters(gl, Texture.texParamsDefault);
        
        quad.load(gl);
        shader.load(gl);
        
        loaded.set(true);
    }

    @Override
    public void unload(GL2 gl)
    {
        super.unload(gl);
        
        quad.unload(gl);
        shader.unload(gl);
    }
    
    protected void setup(GL2 gl)
    {
        int[] w_h = new int[3];
        ByteBuffer data = imageToBytes(image, w_h);
        
        Texture2D.setup(gl, data, w_h[0], w_h[1], w_h[2] == 3 ? GL.GL_RGB : GL.GL_RGBA);
        updated.set(false);
    }
    
    protected Mat4 createWorldMatrix()
    {
        Mat4 world = new Mat4(1f);

        world = translate(world, new Vec3(x, y, 0));

        world = scale(world, new Vec3(image.getWidth(), image.getHeight(), 0));
        
        return world;
    }
    
    protected void draw(GL2 gl, AbstractCamera camera)
    {
        Mat4 worldMatrix = createWorldMatrix();
        
        shader.use(gl);

        use(gl, GL2.GL_TEXTURE0);
        shader.updateUniform(gl, "tex", 0);

        shader.updateUniform(gl, "world", worldMatrix);
        shader.updateUniform(gl, "view", camera.view);
        shader.updateUniform(gl, "projection", camera.projection);
        
        gl.glBegin(GL2.GL_QUADS);
        
        gl.glColor3f(1, 0, 1);
        gl.glTexCoord2f(0, 0);
        gl.glVertex2f(0, 0);
        gl.glTexCoord2f(1, 0);
        gl.glVertex2f(1, 0);
        gl.glTexCoord2f(1, 1);
        gl.glVertex2f(1, 1);
        gl.glTexCoord2f(0, 1);
        gl.glVertex2f(0, 1);
        
        gl.glEnd();
    }
    
    @Override
    public void setImage(BufferedImage image)
    {
        this.image = image;
        updated.set(true);
    }

    public boolean isUpdated()
    {
        return updated.get();
    }
    
    @Override
    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
}
