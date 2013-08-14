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
            node.controller.inputFlags = botEngine.getFlags(node.controller.id);
	}
    }

    @Override
    public void destroy()
    {}
    
}
