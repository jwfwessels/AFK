package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.nodes.LifeNode;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class LifeSystem implements ISystem
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
        List<LifeNode> nodes = engine.getNodeList(LifeNode.class);
        for (LifeNode node : nodes)
        {
            if (node.life.hp >= node.life.maxHp)
                node.life.hp = node.life.maxHp;
            else if (node.life.hp <= 0)
            {
                Renderable renderable = node.entity.get(Renderable.class);
                
                if (renderable != null)
                {
                    // TODO: spawn explosion
                }
                
                engine.removeEntity(node.entity);
            }
            
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
