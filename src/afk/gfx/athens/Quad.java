package afk.gfx.athens;


import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL2;

public class Quad extends Mesh
{

    private FloatBuffer vertices;
    private IntBuffer indices;
    
    public Quad(GL2 gl, float width, float height, float yoffset)
    {
        super(gl);

        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(-width/2, yoffset, height/2);

                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(width/2, yoffset, height/2);

                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(width/2, yoffset, -height/2);
                
                gl.glNormal3f(0.0f, 1.0f, 0.0f);
                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(-width/2, yoffset, -height/2);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
}
