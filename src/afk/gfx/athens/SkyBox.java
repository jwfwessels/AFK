package afk.gfx.athens;

import javax.media.opengl.GL2;

public class SkyBox extends Mesh
{

    public SkyBox(GL2 gl)
    {
        super(gl);
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                // front face
                gl.glVertex3f(-1.0f, 1.0f, 1.0f);
                gl.glVertex3f(1.0f, 1.0f, 1.0f);
                gl.glVertex3f(1.0f, -1.0f, 1.0f);
                gl.glVertex3f(-1.0f, -1.0f, 1.0f);
                
                // back face
                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glVertex3f(1.0f, 1.0f, -1.0f);
                
                // left face
                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
                gl.glVertex3f(-1.0f, 1.0f, 1.0f);
                gl.glVertex3f(-1.0f, -1.0f, 1.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
                
                // right face
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glVertex3f(1.0f, -1.0f, 1.0f);
                gl.glVertex3f(1.0f, 1.0f, 1.0f);
                gl.glVertex3f(1.0f, 1.0f, -1.0f);
                
                // top face
                gl.glVertex3f(-1.0f, 1.0f, -1.0f);
                gl.glVertex3f(1.0f, 1.0f, -1.0f);
                gl.glVertex3f(1.0f, 1.0f, 1.0f);
                gl.glVertex3f(-1.0f, 1.0f, 1.0f);
                
                // bottom face
                gl.glVertex3f(-1.0f, -1.0f, 1.0f);
                gl.glVertex3f(1.0f, -1.0f, 1.0f);
                gl.glVertex3f(1.0f, -1.0f, -1.0f);
                gl.glVertex3f(-1.0f, -1.0f, -1.0f);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
}
