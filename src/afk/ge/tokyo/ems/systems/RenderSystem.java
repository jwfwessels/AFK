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

    public RenderSystem(GraphicsEngine gfxEngine)
    {
        this.gfxEngine = gfxEngine;
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
        gfxEngine.prime();
        List<RenderNode> nodes = engine.getNodeList(RenderNode.class);
        for (RenderNode node : nodes)
        {
            GfxEntity gfx = gfxEngine.getGfxEntity(node.renderable);
            
            gfx.position = node.state.pos;
            gfx.rotation = node.state.rot;
            gfx.scale = node.state.scale;
            gfx.colour = node.renderable.colour;
        }
        
        // TODO: make this happen
//        List<WeaponRenderableNode> weapNodes = ...
//        for (WRNode node : nodes)
//        {
//            GfxEntity gfx = node.renderable.gfx;
//
//            GfxEntity turret = gfx.getSubEntity(node.weapRend.turret);
//            turret.rotY = node.weapon.turretAngle;
//
//            GfxEntity barrel = turret.getSubEntity(node.weapRend.barrel);
//            barrel.rotZ = node.weapon.barrelAngle;
//        }
        
        gfxEngine.post();
        gfxEngine.redisplay();
    }

    @Override
    public void destroy()
    {
    }
    
}
