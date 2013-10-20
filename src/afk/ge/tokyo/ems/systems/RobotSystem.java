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
    private Engine engine;
    private RobotEngine botEngine;

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
        
        for (ControllerNode node : nodes)
	{
            // pass events to the robot
            botEngine.setEvents(node.controller.id, node.controller.events);
            
            // clear events for next tick
            node.controller.events = new RobotEvent();
        
            // ask the bot engine nicely
            botEngine.execute(node.controller.id);

            // get input flags from the robot
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
