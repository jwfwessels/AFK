package afk.gfx.athens;

import com.hackoeur.jglm.Vec3;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

/**
 * Athens implementation of the terrain interface.
 *
 * @author Daniel
 */
public class AthensTerrain extends Mesh
{

    private BufferedImage heightmap;
    private int xGrid, yGrid;
    private float[][][] vertices;
    private float[][][] normals;
    private float[][] topVerts;
    private float[][] botVerts;
    private float[][] leftVerts;
    private float[][] rightVerts;

    private float samplei(int u, int v)
    {
        if (u < 0)
        {
            u = 0;
        }
        if (v < 0)
        {
            v = 0;
        }
        if (u >= heightmap.getWidth())
        {
            u = heightmap.getWidth() - 1;
        }
        if (v >= heightmap.getHeight())
        {
            v = heightmap.getHeight() - 1;
        }
        return (float) (new Color(heightmap.getRGB(u, v)).getRed()) / 256.0f;
    }

    private float samplef(float u, float v)
    {
        if (u < 0)
        {
            u = 0;
        }
        if (u > 1)
        {
            u = 1;
        }
        if (v < 0)
        {
            v = 0;
        }
        if (v > 1)
        {
            v = 1;
        }

        u = u * heightmap.getWidth() - 0.5f;
        v = v * heightmap.getHeight() - 0.5f;
        int x = (int) (u);
        int y = (int) (v);
        float u_ratio = u - x;
        float v_ratio = v - y;
        float u_opposite = 1 - u_ratio;
        float v_opposite = 1 - v_ratio;

        return (samplei(x, y) * u_opposite + samplei(x + 1, y) * u_ratio) * v_opposite
                + (samplei(x, y + 1) * u_opposite + samplei(x + 1, y + 1) * u_ratio) * v_ratio;
    }

    private float h(int i, int j)
    {
        if (heightmap == null)
        {
            return 0.0f;
        }
        return samplef((float) i / (float) (xGrid - 1), (float) j / (float) (yGrid - 1));
    }

    /**
     * Constructs an Athens Terrain object.
     *
     * @param name the name of the heightmap.
     */
    public AthensTerrain(String name)
    {
        super(HEIGHTMAP_MESH, name);
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        super.load(gl);

        this.heightmap = ImageIO.read(
                new File("textures/heightmaps/" + name + ".png"));

        xGrid = heightmap.getWidth();
        yGrid = heightmap.getHeight();

        final float QUAD_WIDTH = 1.0f / (float) (xGrid - 1);
        final float QUAD_LENGTH = 1.0f / (float) (yGrid - 1);

        vertices = new float[xGrid][yGrid][3];
        normals = new float[xGrid][yGrid][3];

        topVerts = new float[xGrid][3];
        botVerts = new float[xGrid][3];
        leftVerts = new float[yGrid][3];
        rightVerts = new float[yGrid][3];

        float x, y, z;
        Vec3 normal;
        for (int i = 0; i < xGrid; i++)
        {
            for (int j = 0; j < yGrid; j++)
            {
                x = i * QUAD_WIDTH;
                y = h(i, j);
                z = j * QUAD_LENGTH;

                float Hx = h(i < xGrid - 1 ? i + 1 : i, j) - h(i > 0 ? i - 1 : i, j);
                if (i == 0 || i == xGrid - 1)
                {
                    Hx *= 2;
                }
                Hx /= QUAD_WIDTH;

                float Hz = h(i, j < yGrid - 1 ? j + 1 : j) - h(i, j > 0 ? j - 1 : j);
                if (j == 0 || j == yGrid - 1)
                {
                    Hz *= 2;
                }
                Hz /= QUAD_LENGTH;

                normal = new Vec3(-Hx, 1.0f, -Hz).getUnitVector();

                vertices[i][j][0] = (x - 0.5f);
                vertices[i][j][1] = (y - 0.5f);
                vertices[i][j][2] = (z - 0.5f);

                if (i == 0)
                {
                    leftVerts[j] = vertices[i][j];
                } else if (i == xGrid - 1)
                {
                    rightVerts[j] = vertices[i][j];
                }
                if (j == 0)
                {
                    topVerts[i] = vertices[i][j];
                } else if (j == yGrid - 1)
                {
                    botVerts[i] = vertices[i][j];
                }

                normals[i][j][0] = normal.getX();
                normals[i][j][1] = normal.getY();
                normals[i][j][2] = normal.getZ();
            }
        }

        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            for (int i = 0; i < xGrid - 1; i++)
            {
                for (int j = 0; j < yGrid - 1; j++)
                {
                    gl.glBegin(GL2.GL_QUADS);
                    {
                        gl.glNormal3f(normals[i][j][0], normals[i][j][1], normals[i][j][2]);
                        gl.glTexCoord2f((float) i / (float) (xGrid - 1), (float) j / (float) (yGrid - 1));
                        gl.glVertex3f(vertices[i][j][0], vertices[i][j][1], vertices[i][j][2]);

                        gl.glNormal3f(normals[i][j + 1][0], normals[i][j + 1][1], normals[i][j + 1][2]);
                        gl.glTexCoord2f((float) i / (float) (xGrid - 1), (float) (j + 1) / (float) (yGrid - 1));
                        gl.glVertex3f(vertices[i][j + 1][0], vertices[i][j + 1][1], vertices[i][j + 1][2]);

                        gl.glNormal3f(normals[i + 1][j + 1][0], normals[i + 1][j + 1][1], normals[i + 1][j + 1][2]);
                        gl.glTexCoord2f((float) (i + 1) / (float) (xGrid - 1), (float) (j + 1) / (float) (yGrid - 1));
                        gl.glVertex3f(vertices[i + 1][j + 1][0], vertices[i + 1][j + 1][1], vertices[i + 1][j + 1][2]);

                        gl.glNormal3f(normals[i + 1][j][0], normals[i + 1][j][1], normals[i + 1][j][2]);
                        gl.glTexCoord2f((float) (i + 1) / (float) (xGrid - 1), (float) j / (float) (yGrid - 1));
                        gl.glVertex3f(vertices[i + 1][j][0], vertices[i + 1][j][1], vertices[i + 1][j][2]);
                    }
                    gl.glEnd();
                }
            }
            for (int i = 0; i < xGrid - 1; i++)
            {
                gl.glBegin(GL2.GL_QUADS);
                {
                    gl.glNormal3f(-1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(leftVerts[i][0], leftVerts[i][1], leftVerts[i][2]);
                    
                    gl.glNormal3f(-1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(leftVerts[i][0], -0.5f, leftVerts[i][2]);
                    
                    gl.glNormal3f(-1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(leftVerts[i+1][0], -0.5f, leftVerts[i+1][2]);
                    
                    gl.glNormal3f(-1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(leftVerts[i+1][0], leftVerts[i+1][1], leftVerts[i+1][2]);
                }
                gl.glEnd();
                gl.glBegin(GL2.GL_QUADS);
                {
                    gl.glNormal3f(1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(rightVerts[i][0], rightVerts[i][1], rightVerts[i][2]);
                    
                    gl.glNormal3f(1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(rightVerts[i+1][0], rightVerts[i+1][1], rightVerts[i+1][2]);
                    
                    gl.glNormal3f(1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(rightVerts[i+1][0], -0.5f, rightVerts[i+1][2]);
                    
                    gl.glNormal3f(1, 0, 0);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(rightVerts[i][0], -0.5f, rightVerts[i][2]);
                }
                gl.glEnd();
            }
            for (int i = 0; i < yGrid - 1; i++)
            {
                gl.glBegin(GL2.GL_QUADS);
                {
                    gl.glNormal3f(0, 0, 1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(botVerts[i][0], botVerts[i][1], botVerts[i][2]);
                    
                    gl.glNormal3f(0, 0, 1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(botVerts[i][0], -0.5f, botVerts[i][2]);
                    
                    gl.glNormal3f(0, 0, 1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(botVerts[i+1][0], -0.5f, botVerts[i+1][2]);
                    
                    gl.glNormal3f(0, 0, 1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(botVerts[i+1][0], botVerts[i+1][1], botVerts[i+1][2]);
                }
                gl.glEnd();
                gl.glBegin(GL2.GL_QUADS);
                {
                    gl.glNormal3f(0, 0, -1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(topVerts[i][0], topVerts[i][1], topVerts[i][2]);
                    
                    gl.glNormal3f(0, 0, -1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(topVerts[i+1][0], topVerts[i+1][1], topVerts[i+1][2]);
                    
                    gl.glNormal3f(0, 0, -1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(topVerts[i+1][0], -0.5f, topVerts[i+1][2]);
                    
                    gl.glNormal3f(0, 0, -1);
                    gl.glTexCoord2f(0, 0);
                    gl.glVertex3f(topVerts[i][0], -0.5f, topVerts[i][2]);
                }
                gl.glEnd();
            }
        }
        gl.glEndList();

        loaded.set(true);
    }
}
