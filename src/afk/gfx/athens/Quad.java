package afk.gfx.athens;

import java.io.IOException;
import javax.media.opengl.GL2;

public class Quad extends Mesh
{
    public static final String NAME = "quad";
    
    public Quad()
    {
        super(PRIMITIVE_MESH, NAME);
    }
    
    @Override
    public void load(GL2 gl)
            throws IOException
    {
        super.load(gl);
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(-0.5f, 0, 0.5f);

                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(0.5f, 0, 0.5f);

                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(0.5f, 0, -0.5f);
                
                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(-0.5f, 0, -0.5f);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
}
