package afk.gfx.athens;

import static afk.gfx.athens.Texture.imageToBytes;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class TextureCubeMap extends Texture
{
    public static final String[] SUFFIXES = {
            "_positive_x.png",
            "_negative_x.png",
            "_positive_y.png",
            "_negative_y.png",
            "_positive_z.png",
            "_negative_z.png"
    };

    public TextureCubeMap(String name)
    {
        super(TEXTURE_CUBE, name, GL.GL_TEXTURE_CUBE_MAP);
    }

    @Override
    public void load(GL2 gl) throws IOException
    {
        super.load(gl);
        
        ByteBuffer[] data = new ByteBuffer[6];
        int[] w_h = new int[3];
        for (int i = 0; i < 6; i++)
        {
            data[i] = imageToBytes(ImageIO.read(new File("./textures/"+name+SUFFIXES[i])), w_h);
        }
        
        setup(gl, data, w_h[0], w_h[1]);
        
        loaded.set(true);
    }
    
    private void setup(GL2 gl, ByteBuffer[] data, int width, int height)
    {
        gl.glTexParameteri(
                GL.GL_TEXTURE_CUBE_MAP,
                GL.GL_TEXTURE_MAG_FILTER,
                GL.GL_LINEAR);
        gl.glTexParameteri(
                GL.GL_TEXTURE_CUBE_MAP,
                GL.GL_TEXTURE_MIN_FILTER,
                GL.GL_LINEAR);
        gl.glTexParameteri(
                GL.GL_TEXTURE_CUBE_MAP,
                GL2.GL_TEXTURE_WRAP_R,
                GL.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(
                GL.GL_TEXTURE_CUBE_MAP,
                GL.GL_TEXTURE_WRAP_S,
                GL.GL_CLAMP_TO_EDGE);
        gl.glTexParameteri(
                GL.GL_TEXTURE_CUBE_MAP,
                GL.GL_TEXTURE_WRAP_T,
                GL.GL_CLAMP_TO_EDGE);
        
        for (int i = 0; i < 6; i++)
        {
            gl.glTexImage2D(GL.GL_TEXTURE_CUBE_MAP_POSITIVE_X+i, 0, GL.GL_RGB,
                    width, height, 0, GL.GL_RGB,
                    GL.GL_UNSIGNED_BYTE,
                    data == null ? null : data[i]);
        }
    }
}
