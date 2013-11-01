/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
import afk.ge.ems.Utils;
import static afk.ge.tokyo.FlagSources.*;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Display;
import afk.ge.tokyo.ems.components.HUD;
import afk.ge.tokyo.ems.components.Lifetime;
import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.TextLabel;
import afk.ge.tokyo.ems.factories.TextLabelFactory;
import afk.ge.tokyo.ems.factories.TextLabelFactoryRequest;
import afk.ge.tokyo.ems.nodes.HUDNode;
import afk.ge.tokyo.ems.nodes.HUDTagNode;
import afk.ge.tokyo.ems.nodes.RenderNode;
import afk.gfx.AbstractCamera;
import afk.gfx.GfxEntity;
import afk.gfx.GfxHUD;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;

/**
 *
 * @author daniel
 */
public class RenderSystem implements ISystem
{

    private Engine engine;
    private GraphicsEngine gfxEngine;
    private Entity fpsLabel = null;
    private TextLabelFactory labelFactory = new TextLabelFactory();
    private TextLabelFactoryRequest fpsLabelRequest
            = new TextLabelFactoryRequest("0", 10, 10, null, null);
    
    private float fpsUpdateInterval = 0.5f;
    private float sinceLastFPS = fpsUpdateInterval;
    private boolean tildePressed = false;

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
        doRenderables();
        doHUD(display);
        doHUDTags(gfxCamera);

        gfxEngine.post();
        gfxEngine.redisplay();
        
        if (!tildePressed)
        {
            if (engine.getFlag(KEYBOARD, KeyEvent.VK_BACK_QUOTE))
            {
                if (fpsLabel == null)
                {
                    fpsLabel = labelFactory.create(fpsLabelRequest);
                    sinceLastFPS = fpsUpdateInterval;
                    engine.addEntity(fpsLabel);
                } else
                {
                    engine.removeEntity(fpsLabel);
                    fpsLabel = null;
                }
                tildePressed = true;
            } else
            {
                tildePressed = false;
            }
        } else
        {
            tildePressed = engine.getFlag(KEYBOARD, KeyEvent.VK_BACK_QUOTE);
        }
        
        if (fpsLabel != null)
        {
            sinceLastFPS += dt;
            if (sinceLastFPS >= fpsUpdateInterval)
            {
                TextLabel label = fpsLabel.getComponent(TextLabel.class);
                float fps = gfxEngine.getFPS();
                label.setText(String.format("%.2f FPS", fps));
                sinceLastFPS = 0;
            }
        }
    }

    @Override
    public void destroy()
    {
    }

    private void doHUD(Display display)
    {
        List<HUDNode> nodes = engine.getNodeList(HUDNode.class);
        for (HUDNode node : nodes)
        {
            BufferedImage img = node.image.getImage();

            if (img == null)
            {
                continue;
            }
            
            GfxHUD hud = gfxEngine.getGfxHUD(node.image);
            
            Point p = getTopLeft(node.hud, img.getWidth(), img.getHeight(),
                    display);
            hud.setPosition(p.x, p.y);

            if (node.image.isUpdated())
            {
                hud.setImage(img);
                node.image.setUpdated(false);
            }
        }
    }
    
    private void doHUDTags(AbstractCamera gfxCamera)
    {
        List<HUDTagNode> nodes = engine.getNodeList(HUDTagNode.class);
        for (HUDTagNode node : nodes)
        {
            BufferedImage img = node.image.getImage();

            if (img == null)
            {
                continue;
            }

            // TODO: pre-multiply ther matrices for performance
            Mat4 world = Utils.getMatrix(node.state);

            Vec4 p = gfxCamera.projection.multiply(
                    gfxCamera.view.multiply(
                    world.multiply(
                    new Vec3(0, node.tag.worldY, 0).toPoint())));

            Vec4 center = gfxCamera.projection.multiply(
                    gfxCamera.view.multiply(
                    world.multiply(
                    Vec3.VEC3_ZERO.toPoint())));

            float depth = p.getZ() / p.getW();
            if (depth < -1 || depth > 1)
            {
                continue;
            }

            GfxHUD hud = gfxEngine.getGfxHUD(node.image);

            Point q = toScreen(p);
            Point r = toScreen(center);
            int yDiff = Math.abs(q.y - r.y);
            if (yDiff < node.tag.minY)
            {
                q.y = r.y - node.tag.minY * (node.tag.worldY < 0 ? -1 : 1);
            }
            q.x += (int) node.tag.xOffset;
            if (node.tag.centerX)
            {
                q.x -= node.image.getImage().getWidth() / 2.0f;
            }
            if (node.tag.centerY)
            {
                q.y -= node.image.getImage().getHeight() / 2.0f;
            }

            hud.setPosition(q.x, q.y);

            if (node.image.isUpdated())
            {
                hud.setImage(img);
                node.image.setUpdated(false);
            }
        }
    }
    
    private Point getTopLeft(HUD hud, int w, int h, Display display)
    {
        Point p = new Point();
        if (hud.bottom != null)
        {
            p.y = (int)(display.screenHeight-h-hud.bottom);
        }
        if (hud.top != null)
        {
            p.y = hud.top;
        }
        if (hud.right != null)
        {
            p.x = (int)(display.screenWidth-w-hud.right);
        }
        if (hud.left != null)
        {
            p.x = hud.left;
        }
        return p;
    }

    private Point toScreen(Vec4 ndc)
    {
        int x = (int) ((ndc.getX() / ndc.getW() + 1.0f) * 0.5f * gfxEngine.getWidth());
        int y = (int) ((1.0f - (ndc.getY() / ndc.getW() + 1.0f) * 0.5f) * gfxEngine.getHeight());
        return new Point(x, y);
    }

    private void doRenderables()
    {
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
    }
}
