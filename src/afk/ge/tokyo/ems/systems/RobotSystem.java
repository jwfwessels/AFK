/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.bot.RobotEngine;
import afk.bot.london.RobotEvent;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.ControllerNode;
import java.util.List;

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
        
        // pass events to each robot
        for (ControllerNode node : nodes)
	{
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
