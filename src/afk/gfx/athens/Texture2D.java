package afk.gfx.athens;

import com.jogamp.common.nio.Buffers;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;


public class Texture2D extends Texture
{
    private int width, height;

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
    
    private void setup(GL2 gl, ByteBuffer data, int width, int height)
    {
        this.width = width;
        this.height = height;
        gl.glTexImage2D(GL.GL_TEXTURE_2D, 0, GL.GL_RGB,
                width, height, 0, GL.GL_RGB,
                GL.GL_UNSIGNED_BYTE,
                data);
    }
    
    public void generateMipmap(GL2 gl)
    {
        gl.glGenerateMipmap(GL.GL_TEXTURE_2D);
    }
    
    protected Texture2D(String name, int width, int height, GL2 gl)
    {
        super(TEXTURE_2D, name+"?width="+width+"&height="+height, GL.GL_TEXTURE_2D);
        try
        {
            super.load(gl);
        } catch (IOException ex)
        {
            ex.printStackTrace(System.err);
        }
        ByteBuffer data = Buffers.newDirectByteBuffer(width*height*3);
        data.limit(data.capacity());
        setup(gl, data, width, height);
        loaded.set(true);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
