/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.bot.RobotEngine;
import afk.bot.london.RobotEvent;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.ControllerNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Jw
 */
public class RobotSystem implements ISystem
{
    Engine engine;
    RobotEngine botEngine;

    public RobotSystem(RobotEngine botEngine)
    {
        this.botEngine = botEngine;
    }

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<ControllerNode> nodes = engine.getNodeList(ControllerNode.class);

//        Map<UUID, List<UUID>> hits = new HashMap<UUID, List<UUID>>();
//        for (ControllerNode node : nodes) {
//        	if (node.controller.events.gotHit.size() > 0) {
//        		for (UUID source: node.controller.events.gotHit) {
//            		List<UUID> sourceHits = hits.get(source);
//            		if (sourceHits == null) {
//            			sourceHits = new ArrayList<UUID>();
//            		}
//            		sourceHits.add(node.controller.id);
//        		}
//        	}
//        }
        
        // pass events to each robot
        for (ControllerNode node : nodes)
        {
//        	if (hits.containsKey(node.controller.id)) {
//        		List<UUID> sourceHits = hits.get(node.controller.id);
//        		if (sourceHits != null) {
//            		node.controller.events.didHit = sourceHits;
//        		}
//        	}
            botEngine.setEvents(node.controller.id, node.controller.events);
            
            // clear events for next tick
            node.controller.events = new RobotEvent();
        }
        
        botEngine.execute();
        
        // get input flags from each robot
		for (ControllerNode node : nodes)
		{
            Object source = node.controller.id;
            boolean[] flags = botEngine.getFlags(node.controller.id);
            for (int i = 0; i < flags.length; i++)
            {
                engine.setFlag(source, i, flags[i]);
            }
		}
    }

    @Override
    public void destroy()
    {}
    
}
