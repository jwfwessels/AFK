package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
import afk.ge.tokyo.ems.nodes.RenderNode;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import java.util.List;

/**
 *
 * @author daniel
 */
public class RenderSystem implements ISystem
{
    Engine engine;
    GraphicsEngine gfxEngine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<RenderNode> nodes = engine.getNodeList(RenderNode.class);
        for (RenderNode node : nodes)
        {
            GfxEntity gfx = node.renderable.gfx;
            
            gfx.setPosition(node.state.pos);
            gfx.setRotation(node.state.rot);
            gfx.setScale(node.state.scale);
            gfx.colour = node.renderable.colour;
        }
        
        //gfxEngine.redisplay();
    }

    @Override
    public void destroy()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
