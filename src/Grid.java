import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Grid extends Mesh
{
    
    public Grid(GL2 gl, float width, float height,
            int xGrid, int yGrid, float yOffset)
    {
        this(gl,width,height,xGrid,yGrid,yOffset,false);
    }
    
    public Grid(GL2 gl, float width, float height,
            int xGrid, int yGrid, float yOffset, boolean flipNormal)
    {
        super(gl);
        
        final float QUAD_WIDTH = width/xGrid;
        final float QUAD_LENGTH = height/yGrid;
        
        final int NUM_QUADS = (xGrid-1)*(yGrid-1);
        final int NUM_VERTICES = xGrid*yGrid;
        
        float[] vertices = new float[NUM_VERTICES*DIMESIONS];
        float[] normals = new float[NUM_VERTICES*DIMESIONS];
        
        float x, z;
        int k = 0;
        for (int i = 0; i < xGrid; i++)
        {
            for (int j = 0; j < yGrid; j++)
            {
                x = i*QUAD_WIDTH;
                z = j*QUAD_LENGTH;
                
                normals[k] = (0);
                vertices[k++] = (x-width/2);
                
                normals[k] = (flipNormal ? -1.0f : 1.0f);
                vertices[k++] = (yOffset);
                
                normals[k] = (0);
                vertices[k++] = (z-height/2);
                
            }
        }
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            gl.glBegin(GL2.GL_TRIANGLES);
            {
                int a, b, c, tmp;
                for (int i = 0; i < xGrid-1; i++)
                {
                    for (int j = 0; j < yGrid-1; j++)
                    {
                        a = i+j*xGrid;
                        b = i+1+j*xGrid;
                        c = i+1+(j+1)*xGrid;

                        if (flipNormal)
                        {
                            tmp = c;
                            c = b;
                            c = tmp;
                        }

                        gl.glNormal3f(normals[a], normals[b], normals[c]);
                        gl.glVertex3f(vertices[a], vertices[b], vertices[c]);

                        a = i+j*xGrid;
                        b = i+1+(j+1)*xGrid;
                        c = i+(j+1)*xGrid;


                        if (flipNormal)
                        {
                            tmp = c;
                            c = b;
                            c = tmp;
                        }

                        gl.glNormal3f(normals[a], normals[b], normals[c]);
                        gl.glVertex3f(vertices[a], vertices[b], vertices[c]);
                    }
                }
            }
            gl.glEnd();
        }
    }
}
