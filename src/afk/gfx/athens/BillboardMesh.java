/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx.athens;

import javax.media.opengl.GL2;

/**
 *
 * @author Daniel
 */
public class BillboardMesh extends Mesh
{

    public BillboardMesh(GL2 gl)
    {
        super(gl);
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_QUADS);
            {
                float z = 0.99f;
                gl.glVertex3f(-0.5f, 0.5f, z);

                gl.glVertex3f(0.5f, 0.5f, z);

                gl.glVertex3f(0.5f, -0.5f, z);

                gl.glVertex3f(-0.5f, -0.5f, z);
            }
            gl.glEnd();
        }
        gl.glEndList();
    }
    
}
