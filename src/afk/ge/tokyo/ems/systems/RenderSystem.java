package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Lifetime;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.nodes.HUDNode;
import afk.ge.tokyo.ems.nodes.NoClipCameraNode;
import afk.ge.tokyo.ems.nodes.RenderNode;
import afk.gfx.AbstractCamera;
import afk.gfx.GfxEntity;
import afk.gfx.GfxHUD;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.awt.image.BufferedImage;
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
        AbstractCamera gfxCamera = gfxEngine.getCamera();
        Camera camera = engine.getGlobal(Camera.class);
        if (camera != null)
        {
            gfxCamera.at = camera.at;
            gfxCamera.eye = camera.eye;
            gfxCamera.up = camera.up;
        }
        
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

        List<HUDNode> hnodes = engine.getNodeList(HUDNode.class);
        for (HUDNode hnode : hnodes)
        {
            BufferedImage img = hnode.image.getImage();

            if (img == null)
            {
                continue;
            }

            GfxHUD hud = gfxEngine.getGfxHUD(hnode.image);

            Renderable r = hnode.entity.get(Renderable.class);
            if (r == null)
            {
                hud.setPosition((int) hnode.state.pos.getX(),
                        (int) hnode.state.pos.getY());
            } else
            {
                GfxEntity gfx = gfxEngine.getGfxEntity(r);

                Vec4 p = gfxCamera.projection.multiply(
                        gfxCamera.view.multiply(
                        gfx.getWorldMatrix().multiply(
                        Vec3.VEC3_ZERO.toPoint())));

                float depth = p.getZ() / p.getW();
                if (depth < -1 || depth > 1)
                {
                    // FIXME: need some interface for aborting rendering of objects
                    hud.setPosition(-9999, -9999);
                    continue;
                }

                int x = (int) ((p.getX() / p.getW() + 1.0f) * 0.5f * gfxEngine.getWidth());
                int y = (int) ((1.0f - (p.getY() / p.getW() + 1.0f) * 0.5f) * gfxEngine.getHeight());

                hud.setPosition(x, y);
            }

            if (hnode.image.isUpdated())
            {
                hud.setImage(img);
                hnode.image.setUpdated(false);
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
