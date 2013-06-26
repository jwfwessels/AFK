package afk.gfx.athens;

import javax.media.opengl.GL2;

public class NDCQuad extends Mesh
{

    public NDCQuad(GL2 gl)
    {
        super(gl);
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                float z = 0.99f;
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(-1.0f, 1.0f, z);

                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(1.0f, 1.0f, z);

                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(1.0f, -1.0f, z);

                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(-1.0f, -1.0f, z);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
}
