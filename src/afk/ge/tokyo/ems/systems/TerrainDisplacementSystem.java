package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.HeightmapNode;
import java.util.List;
import static afk.ge.tokyo.HeightmapLoader.*;
import afk.ge.tokyo.ems.nodes.TerrainDisplacementNode;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class TerrainDisplacementSystem implements ISystem
{

    Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<TerrainDisplacementNode> nodes = engine.getNodeList(TerrainDisplacementNode.class);
        HeightmapNode hnode = engine.getNodeList(HeightmapNode.class).get(0);

        for (TerrainDisplacementNode node : nodes)
        {
            float x = node.state.pos.getX();
            float z = node.state.pos.getZ();
            float y = getHeight(x, z, hnode.heightmap) + node.displacement.height;
            node.state.pos = new Vec3(x, y, z);
        }
    }

    @Override
    public void destroy()
    {
    }
}
