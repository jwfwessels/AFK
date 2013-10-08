package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Paint;
import afk.ge.tokyo.ems.nodes.PaintNode;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class PaintSystem implements ISystem
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
        List<PaintNode> nodes = engine.getNodeList(PaintNode.class);
        
        for (PaintNode node : nodes)
        {
            node.renderable.colour = node.paint.colour;
            node.entity.remove(Paint.class);
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
