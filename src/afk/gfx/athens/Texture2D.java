package afk.gfx.athens;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class Texture2D extends Texture
{

    public Texture2D(String name)
    {
        super(TEXTURE_2D, name, GL.GL_TEXTURE_2D);
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        super.load(gl);
        
        int[] w_h = new int[2];
        ByteBuffer data = imageToBytes(ImageIO.read(new File("./textures/"+name+".png")), w_h);
        
        setup(gl, data, w_h[0], w_h[1]);
        
        setParameters(gl, Texture.texParamsDefault);
        
        loaded.set(true);
    }
    
    protected static void setup(GL2 gl, ByteBuffer data, int width, int height)
    {
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB,
                width, height, 0, GL.GL_RGB,
                GL.GL_UNSIGNED_BYTE,
                data);
    }
    
    public void generateMipmap(GL2 gl)
    {
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
    }
    
}
