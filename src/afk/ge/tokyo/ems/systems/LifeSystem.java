package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.events.DamageEvent;
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
            List<DamageEvent> damageEvents = node.entity.getEventList(DamageEvent.class);
            if (damageEvents.isEmpty()) continue;
            
            Controller[] attackers = new Controller[damageEvents.size()];
            
            for (DamageEvent damage : damageEvents)
            {
                node.life.hp -= damage.getAmount();
                if (node.life.hp <= 0)
                {
                    int random = (int)(Math.random()*attackers.length);
                    attackers[random].score++;
                    engine.removeEntity(node.entity);
                    break;
                }
            }
            damageEvents.clear();
            
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
