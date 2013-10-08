/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.systems;

import afk.game.GameListener;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.ControllerNode;
import java.util.List;

/**
 *
 * @author Jw
 */
public class GameStateSystem implements ISystem
{

    Engine engine;
    GameListener listener;

    public GameStateSystem(GameListener l)
    {
        listener = l;
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


        int size = nodes.size();
        if (size <= 1)
        {
            if (size == 1)
            {
                listener.gameStateChange(new String[]
                {
                    "WINNER", 
                    nodes.get(0).controller.id.toString()
                });
            } else
            {
                listener.gameStateChange(new String[]
                {
                    "DRAW", 
                    ""
                });
            }
        }

    }

    @Override
    public void destroy()
    {
    }
}
