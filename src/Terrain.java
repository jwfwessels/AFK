import com.hackoeur.jglm.Vec3;
import com.jogamp.common.nio.Buffers;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Terrain extends Mesh
{
    private BufferedImage heightmap;
    private int xGrid, yGrid;
    
    private float samplei(int u, int v)
    {
        if (u < 0) u = 0;
        if (v < 0) v = 0;
        if (u >= heightmap.getWidth()) u = heightmap.getWidth()-1;
        if (v >= heightmap.getHeight()) v = heightmap.getHeight()-1;
        return (float)(new Color(heightmap.getRGB(u, v)).getRed())/256.0f;
    }
    
    private float samplef(float u, float v)
    {
        if (u < 0) u = 0;
        if (u > 1) u = 1;
        if (v < 0) v = 0;
        if (v > 1) v = 1;
        
        u = u * heightmap.getWidth() - 0.5f;
        v = v * heightmap.getHeight() - 0.5f;
        int x = (int)(u);
        int y = (int)(v);
        float u_ratio = u - x;
        float v_ratio = v - y;
        float u_opposite = 1 - u_ratio;
        float v_opposite = 1 - v_ratio;
        
        return (samplei(x,y)   * u_opposite  + samplei(x+1,y)   * u_ratio) * v_opposite + 
                        (samplei(x,y+1) * u_opposite  + samplei(x+1,y+1) * u_ratio) * v_ratio;
    }
    
    private float h(int i, int j)
    {
        if (heightmap == null) return 0.0f;
        return samplef((float)i/(float)xGrid,(float)j/(float)yGrid);
    }
    
    public Terrain(GL2 gl, float width, float length, float height,
            int xGrid, int yGrid, BufferedImage heightmap)
    {
        super(gl);
        
        this.xGrid = xGrid;
        this.yGrid = yGrid;
        this.heightmap = heightmap;
        
        final float QUAD_WIDTH = width/xGrid;
        final float QUAD_LENGTH = length/yGrid;
        
        final int NUM_QUADS = (xGrid-1)*(yGrid-1);
        final int NUM_VERTICES = xGrid*yGrid;
        
        float[][][] vertices = new float[xGrid][yGrid][3];
        float[][][] normals = new float[xGrid][yGrid][3];
        
        int k = 0;
        float x, y, z;
        Vec3 normal;
        for (int i = 0; i < xGrid; i++)
        {
            for (int j = 0; j < yGrid; j++)
            {
                x = i*QUAD_WIDTH;
                y = height * h(i,j);
                z = j*QUAD_LENGTH;
                
                float Hx = h(i<xGrid-1 ? i+1 : i, j) - h(i>0 ? i-1 : i, j);
                if (i == 0 || i == xGrid-1)
                    Hx *= 2;
                Hx /= QUAD_WIDTH;

                float Hz = h(i, j<yGrid-1 ? j+1 : j) - h(i, j>0 ?  j-1 : j);
                if (j == 0 || j == yGrid-1)
                    Hz *= 2;
                Hz /= QUAD_LENGTH;

                normal = new Vec3(-Hx*height, 1.0f, -Hz*height).getUnitVector();
                
                vertices[i][j][0] = (x-width/2);
                vertices[i][j][1] = (y-height/2);
                vertices[i][j][2] = (z-length/2);
                
                normals[i][j][0] = normal.getX();
                normals[i][j][1] = normal.getX();
                normals[i][j][2] = normal.getX();
            }
        }
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            for (int i = 0; i < xGrid-1; i++)
            {
                for (int j = 0; j < yGrid-1; j++)
                {
                    gl.glBegin(GL2.GL_QUADS);
                    {
                        gl.glNormal3f(normals[i][j][0], normals[i][j][1], normals[i][j][2]);
                        gl.glVertex3f(vertices[i][j][0], vertices[i][j][1], vertices[i][j][2]);
                        
                        gl.glNormal3f(normals[i][j+1][0], normals[i][j+1][1], normals[i][j+1][2]);
                        gl.glVertex3f(vertices[i][j+1][0], vertices[i][j+1][1], vertices[i][j+1][2]);
                        
                        gl.glNormal3f(normals[i+1][j+1][0], normals[i+1][j+1][1], normals[i+1][j+1][2]);
                        gl.glVertex3f(vertices[i+1][j+1][0], vertices[i+1][j+1][1], vertices[i+1][j+1][2]);
                        
                        gl.glNormal3f(normals[i+1][j][0], normals[i+1][j][1], normals[i+1][j][2]);
                        gl.glVertex3f(vertices[i+1][j][0], vertices[i+1][j][1], vertices[i+1][j][2]);
                    }
                    gl.glEnd();
                }
            }
        }
        gl.glEndList();
    }
}
