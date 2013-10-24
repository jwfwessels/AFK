package afk.rome;

import static afk.gfx.Resource.HEIGHTMAP_MESH;
import afk.gfx.athens.AthensTerrain;
import afk.gfx.athens.Mesh;
import com.hackoeur.jglm.Vec3;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.media.opengl.GL2;

/**
 *
 * @author daniel
 */
public class EditableAthensTerrain extends AthensTerrain
{

    public EditableAthensTerrain(String name)
    {
        super(name);
        sides = new EditableSides(name + "$sides");
    }
    
    @Override
    public void load(GL2 gl)
            throws IOException
    {
        this.heightmap = ImageIO.read(
                new File("textures/heightmaps/" + name + ".png"));

        xGrid = heightmap.getWidth();
        yGrid = heightmap.getHeight();
        
        vertices = new float[xGrid][yGrid][3];
        normals = new float[xGrid][yGrid][3];

        topVerts = new float[xGrid][3];
        botVerts = new float[xGrid][3];
        leftVerts = new float[yGrid][3];
        rightVerts = new float[yGrid][3];

        calculateHeightmap();

        sides.load(gl);

        loaded.set(true);
    }

    public BufferedImage getHeightmap()
    {
        return heightmap;
    }
    
    public void update()
    {
        calculateHeightmap();
    }

    @Override
    public void draw(GL2 gl)
    {
        drawTerrain(gl);
    }
    
    private class EditableSides extends Mesh
    {

        public EditableSides(String name)
        {
            super(HEIGHTMAP_MESH, name);
        }

        @Override
        public void load(GL2 gl)
                throws IOException
        {
            this.loaded.set(true);
        }

        @Override
        public void draw(GL2 gl)
        {
            drawSides(gl);
        }
    }
    
}
