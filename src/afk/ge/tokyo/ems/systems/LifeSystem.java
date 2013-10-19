package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.ScoreBoard;
import afk.ge.tokyo.ems.events.DamageEvent;
import afk.ge.tokyo.ems.nodes.LifeNode;
import java.util.List;
import java.util.UUID;

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
        ScoreBoard scoreboard = engine.getGlobal(ScoreBoard.class);
        for (LifeNode node : nodes)
        {
            List<DamageEvent> damageEvents = node.entity.getEventList(DamageEvent.class);
            if (damageEvents.isEmpty()) continue;
            
            Controller[] attackers = new Controller[damageEvents.size()];
            
            int i = 0;
            for (DamageEvent damage : damageEvents)
            {
                node.life.hp -= damage.getAmount();
                attackers[i] = damage.getFrom();
            }
            if (node.life.hp <= 0)
            {
                int random = (int)(Math.random()*attackers.length);
                UUID attacker = attackers[random].id;
                Integer score = scoreboard.scores.get(attacker);
                score += 1;
                scoreboard.scores.put(attacker, score);
                engine.removeEntity(node.entity);
            }
            damageEvents.clear();
            
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
