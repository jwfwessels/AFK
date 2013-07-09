package afk.gfx.athens;


import com.hackoeur.jglm.Vec3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

/**
 * This class represents a Mesh loaded from a Wavefront OBJ file.
 *
 * @author daniel
 */
public class WavefrontMesh extends Mesh
{

    public WavefrontMesh(String name)
    {
        super(WAVEFRONT_MESH, name);
    }

    @Override
    public void load(GL2 gl)
            throws IOException
    {
        super.load(gl);
        
        String fileName = "meshes/"+name+".obj";

        ArrayList<float[]> pos = new ArrayList<float[]>(); // Read in vertices
        ArrayList<float[]> tex = new ArrayList<float[]>(); // Read in texcoords
        ArrayList<float[]> norm = new ArrayList<float[]>(); // Read in vertex normals. NOTE: these do not match 1:1 to vertices.
        ArrayList<int[][]> inds = new ArrayList<int[][]>(); // Vertex indices

        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try
        {
            String line;
            String[] list;
            while ((line = br.readLine()) != null)
            {
                if (line.startsWith("v "))
                {
                    list = line.split(" ");
		    // read X Y Z into vertex array
                    pos.add(new float[]{
                        Float.parseFloat(list[1]),
                        Float.parseFloat(list[2]),
                        Float.parseFloat(list[3])
                    });
                } else if (line.startsWith("vt "))
                {
                    list = line.split(" ");
		    // Read X Y Z into normal array
                    tex.add(new float[]{
                        Float.parseFloat(list[1]),
                        Float.parseFloat(list[2])
                    });
                } else if (line.startsWith("vn "))
                {
                    list = line.split(" ");
		    // Read X Y Z into normal array
                    Vec3 normal = new Vec3(
                        Float.parseFloat(list[1]),
                        Float.parseFloat(list[2]),
                        Float.parseFloat(list[3])
                    ).getUnitVector();
                    norm.add(new float[]{
                        normal.getX(),
                        normal.getY(),
                        normal.getZ()
                    });
                } else if (line.startsWith("f "))
                {
                    list = line.split(" ");
                    
                    int[][] primitive = new int[list.length-1][];
                    
                    for (int i = 1; i < list.length; i++)
                    {
                        primitive[i-1] = parseVertex(list[i].trim());
                    }
                    
                    inds.add(primitive);
                }
            }
        }
        finally
        {
            br.close();
        }
        
        gl.glNewList(handle, GL2.GL_COMPILE);
        {
            for (int[][] v :inds)
            {
                gl.glBegin(v.length == 3 ? GL.GL_TRIANGLES : GL2.GL_QUADS);
                {
                    for (int i = 0; i < v.length; i++)
                    {
                        float[] p;
                        if (v[i][1] != -1) // if there is a texcoord
                        {
                            p = tex.get(v[i][1]-1);
                            gl.glTexCoord2f(p[0], p[1]);
                        }
                        if (v[i][2] != -1) // if there is a normal
                        {
                            p = norm.get(v[i][2]-1);
                            gl.glNormal3f(p[0], p[1], p[2]);
                        }
                        p = pos.get(v[i][0]-1);
                        gl.glVertex3f(p[0], p[1], p[2]); // emit vertex and all attributes
                    }
                }
                gl.glEnd();
            }
        }
        gl.glEndList();
    }
    
    /**
     * Parses a wavefront vertex (i.e. "face point") by extracting the relevant indices.
     * @param string
     * @return 
     */
    public static int[] parseVertex(String string)
    {
        int v, vt = -1, vn = -1;
        
        int a = string.indexOf('/');
        int b = string.lastIndexOf('/');
        if (b == a) b = -1; // if there is no second "/"
        if (a == -1) //                 "v"
            v = Integer.parseInt(string);
        else //                         "v/..."
        {
            v = Integer.parseInt(string.substring(0, a));
            if (b == -1) //             "v/vt"
                vt = Integer.parseInt(string.substring(a+1));
            else if (b == a+1) //       "v//vn"
                vn = Integer.parseInt(string.substring(b+1));
            else //                     "v/vt/vn"
            {
                vt = Integer.parseInt(string.substring(a+1,b));
                vn = Integer.parseInt(string.substring(b+1));
            }
        }
        
        return new int[]{v, vt, vn};
    }
}
