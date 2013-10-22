package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.ems.Utils;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Display;
import afk.ge.tokyo.ems.components.Lifetime;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.nodes.HUDTagNode;
import afk.ge.tokyo.ems.nodes.RenderNode;
import afk.gfx.AbstractCamera;
import afk.gfx.GfxEntity;
import afk.gfx.GfxHUD;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Mat4;
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
        if (!gfxEngine.isReady())
        {
            return;
        }

        AbstractCamera gfxCamera = gfxEngine.getCamera();
        Camera camera = engine.getGlobal(Camera.class);
        gfxCamera.at = camera.at;
        gfxCamera.eye = camera.eye;
        gfxCamera.up = camera.up;

        Display display = engine.getGlobal(Display.class);
        display.screenWidth = gfxEngine.getWidth();
        display.screenHeight = gfxEngine.getHeight();

        gfxEngine.prime();
        List<RenderNode> nodes = engine.getNodeList(RenderNode.class);
        for (RenderNode node : nodes)
        {
            GfxEntity gfx = gfxEngine.getGfxEntity(node.renderable);

            Parent parent = node.entity.getComponent(Parent.class);
            if (parent != null && parent.entity.hasComponent(Renderable.class))
            {
                GfxEntity parentGfx = gfxEngine.getGfxEntity(parent.entity.getComponent(Renderable.class));
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
            gfx.opacity = node.renderable.opacity;
            Lifetime lifetime = node.entity.getComponent(Lifetime.class);
            if (lifetime != null)
            {
                gfx.life = 1.0f - (lifetime.life / lifetime.maxLife);
            } else
            {
                gfx.life = 0.0f;
            }
        }

        List<HUDTagNode> tnodes = engine.getNodeList(HUDTagNode.class);
        for (HUDTagNode tnode : tnodes)
        {
            BufferedImage img = tnode.image.getImage();

            if (img == null)
            {
                continue;
            }

            Mat4 world = Utils.getMatrix(tnode.state);

            Vec4 p = gfxCamera.projection.multiply(
                    gfxCamera.view.multiply(
                    world.multiply(
                    Vec3.VEC3_ZERO.toPoint())));

            float depth = p.getZ() / p.getW();
            if (depth < -1 || depth > 1)
            {
                continue;
            }
            
            GfxHUD hud = gfxEngine.getGfxHUD(tnode.image);

            int x = (int) ((p.getX() / p.getW() + 1.0f) * 0.5f * gfxEngine.getWidth()) + tnode.tag.x;
            int y = (int) ((1.0f - (p.getY() / p.getW() + 1.0f) * 0.5f) * gfxEngine.getHeight()) + tnode.tag.y;
            if (tnode.tag.centerX) x -= tnode.image.getImage().getWidth()/2.0f;
            if (tnode.tag.centerY) y -= tnode.image.getImage().getHeight()/2.0f;

            hud.setPosition(x, y);

            if (tnode.image.isUpdated())
            {
                hud.setImage(img);
                tnode.image.setUpdated(false);
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
