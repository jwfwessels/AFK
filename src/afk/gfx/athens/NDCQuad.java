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
                gl.glVertex3f(-1.0f, 1.0f, z);

                gl.glVertex3f(1.0f, 1.0f, z);

                gl.glVertex3f(1.0f, -1.0f, z);

                gl.glVertex3f(-1.0f, -1.0f, z);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
}
