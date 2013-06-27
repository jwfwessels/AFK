package afk.gfx.athens;

import javax.media.opengl.GL2;

public class BillboardQuad extends Mesh
{

    public BillboardQuad(GL2 gl)
    {
        super(gl);
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                gl.glTexCoord2f(0, 0);
                gl.glVertex3f(0, 0.5f, -0.5f);

                gl.glTexCoord2f(0, 1);
                gl.glVertex3f(0, -0.5f, -0.5f);

                gl.glTexCoord2f(1, 1);
                gl.glVertex3f(0, -0.5f, 0.5f);

                gl.glTexCoord2f(1, 0);
                gl.glVertex3f(0, 0.5f, 0.5f);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
}
