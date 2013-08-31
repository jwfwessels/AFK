package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.HeightmapNode;
import afk.ge.tokyo.ems.nodes.MovementNode;
import java.util.List;
import static afk.ge.tokyo.HeightmapLoader.*;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class HeightmapSystem implements ISystem
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
        List<MovementNode> nodes = engine.getNodeList(MovementNode.class);
        HeightmapNode hnode = engine.getNodeList(HeightmapNode.class).get(0);

        for (MovementNode node : nodes)
        {
            float x = node.state.pos.getX();
            float z = node.state.pos.getZ();
            float y = getHeight(x, z, hnode.heightmap);
            System.out.println("y: " + y);
            node.state.pos = new Vec3(x, y, z);
        }
    }

    @Override
    public void destroy()
    {
    }
}
