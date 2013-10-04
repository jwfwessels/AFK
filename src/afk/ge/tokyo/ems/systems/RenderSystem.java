package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Lifetime;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.Renderable;
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

            Parent parent = node.entity.get(Parent.class);
            if (parent != null && parent.entity.has(Renderable.class))
            {
                GfxEntity parentGfx = gfxEngine.getGfxEntity(parent.entity.get(Renderable.class));
                if (parentGfx != gfx.getParent())
                {
                    parentGfx.addChild(gfx);
                }
            } else
            {
                GfxEntity parentGfx = gfx.getParent();
                if (parentGfx != null)
                {
                    parentGfx.removeChild(gfx);
                }
            }

            gfx.position = node.state.pos;
            gfx.rotation = node.state.rot;
            gfx.scale = node.state.scale;
            gfx.colour = node.renderable.colour;
            Lifetime lifetime = node.entity.get(Lifetime.class);
            if (lifetime != null)
            {
                gfx.life = 1.0f-(lifetime.life/lifetime.maxLife);
            } else
            {
                gfx.life = 0.0f;
            }
        }

        gfxEngine.post();
        gfxEngine.redisplay();
    }

    @Override
    public void destroy()
    {
    }
}
