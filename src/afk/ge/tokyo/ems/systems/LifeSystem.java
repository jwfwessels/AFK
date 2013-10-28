/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
