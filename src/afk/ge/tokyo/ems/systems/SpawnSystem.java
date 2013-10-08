package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Spawn;
import afk.ge.tokyo.ems.nodes.SpawnNode;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class SpawnSystem implements ISystem
{
    private Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<SpawnNode> nodes = engine.getNodeList(SpawnNode.class);
        
        for (SpawnNode node : nodes)
        {
            System.out.println("Spawing: " + node.entity.toString());
            node.state.prevPos = node.state.pos = node.spawn.pos;
            node.state.prevRot = node.state.rot = node.spawn.rot;
            node.entity.remove(Spawn.class);
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
