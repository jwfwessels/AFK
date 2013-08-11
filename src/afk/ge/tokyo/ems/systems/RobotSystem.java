/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.bot.RobotEngine;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.ControllerNode;
import java.util.Arrays;
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
        botEngine.execute();
        
        // FIXME: take this out once db system is complete
        List<ControllerNode> nodes = engine.getNodeList(ControllerNode.class);
	for (ControllerNode node : nodes)
	{
            node.controller.inputFlags = botEngine.getFlags(node.controller.id);
	}
    }

    @Override
    public void destroy()
    {}
    
}
