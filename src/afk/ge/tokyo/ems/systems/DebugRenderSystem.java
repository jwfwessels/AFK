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

import afk.bot.london.Sonar;
import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.ems.Utils;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Display;
import afk.ge.tokyo.ems.components.Mouse;
import afk.ge.tokyo.ems.components.Selection;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.SonarNode;
import afk.gfx.GfxEntity;
import static afk.gfx.GfxEntity.*;
import static afk.gfx.GfxUtils.X_AXIS;
import static afk.gfx.GfxUtils.Y_AXIS;
import static afk.gfx.GfxUtils.Z_AXIS;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.List;

/**
 *
 * @author daniel
 */
public class DebugRenderSystem implements ISystem
{

    Engine engine;
    GraphicsEngine gfxEngine;

    public DebugRenderSystem(GraphicsEngine gfxEngine)
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
        
        Camera camera = engine.getGlobal(Camera.class);
        Mouse mouse = engine.getGlobal(Mouse.class);
        Display display = engine.getGlobal(Display.class);
        Selection selection = engine.getGlobal(Selection.class);
        
        List<CollisionNode> nodes = engine.getNodeList(CollisionNode.class);
        for (CollisionNode node : nodes)
        {
            BBox bbox = new BBox(node.state, node.bbox);

            GfxEntity gfx = gfxEngine.getDebugEntity(bbox);

            gfx.position = bbox.getCenterPoint();
            gfx.rotation = node.state.rot;
            gfx.scale = new Vec3(1);
            gfx.colour = selection.getEntity() == node.entity ? GREEN : MAGENTA;
        }

        List<SonarNode> snodes = engine.getNodeList(SonarNode.class);
        for (SonarNode node : snodes)
        {

            final Vec3[] POS_AXIS =
            {
                X_AXIS, Y_AXIS, Z_AXIS
            };
            final Vec3[] NEG_AXIS =
            {
                X_AXIS.getNegated(),
                Y_AXIS.getNegated(),
                Z_AXIS.getNegated()
            };
            Sonar sonar = node.controller.events.sonar;
            Mat4 m = Utils.getMatrix(node.state);
            Vec3 pos = node.state.pos.add(m.multiply(node.bbox.offset.toDirection()).getXYZ());

            float[] sonarmins =
            {
                sonar.distance[3], sonar.distance[4], sonar.distance[5]
            };
            float[] sonarmaxes =
            {
                sonar.distance[0], sonar.distance[1], sonar.distance[2]
            };

            for (int i = 0; i < 3; i++)
            {
                if (!Float.isNaN(node.sonar.min.get(i)))
                {
                    drawSonar(node, NEG_AXIS[i], sonarmins[i],
                            node.sonar.min.get(i), pos.subtract(m.multiply(NEG_AXIS[i].scale(node.bbox.extent.get(i)).toDirection()).getXYZ()));
                }
                if (!Float.isNaN(node.sonar.max.get(i)))
                {
                    drawSonar(node, POS_AXIS[i], sonarmaxes[i],
                            node.sonar.max.get(i), pos.add(m.multiply(POS_AXIS[i].scale(node.bbox.extent.get(i)).toDirection()).getXYZ()));
                }
            }

            gfxEngine.getDebugEntity(new Vec3[]
            {
                new Vec3(-1000, 0, 0), new Vec3(1000, 0, 0)
            }).colour = new Vec3(0.7f, 0, 0);
            gfxEngine.getDebugEntity(new Vec3[]
            {
                new Vec3(0, -1000, 0), new Vec3(0, 1000, 0)
            }).colour = new Vec3(0, 0.7f, 0);
            gfxEngine.getDebugEntity(new Vec3[]
            {
                new Vec3(0, 0, -1000), new Vec3(0, 0, 1000)
            }).colour = new Vec3(0, 0, 0.7f);
            
            final float fov = 60.0f, near = 0.1f, far = 200.0f;
        
            Mat4 proj = Matrices.perspective(fov, display.screenWidth/display.screenHeight, near, far);
            Mat4 view = Matrices.lookAt(camera.eye, camera.at, camera.up);
            Mat4 cam = proj.multiply(view);
            Mat4 camInv = cam.getInverse();

            Vec4 mouseNear4 = camInv.multiply(new Vec4(mouse.nx,mouse.ny,-1,1));
            Vec4 mouseFar4 = camInv.multiply(new Vec4(mouse.nx,mouse.ny,1,1));
            
            Vec3 mouseNear = mouseNear4.getXYZ().scale(1.0f/mouseNear4.getW());
            Vec3 mouseFar = mouseFar4.getXYZ().scale(1.0f/mouseFar4.getW());
            
            gfxEngine.getDebugEntity(new Vec3[]
            {
                mouseNear, mouseFar
            }).colour = new Vec3(1,0,0);
        }

    }

    private void drawSonar(SonarNode node, Vec3 axis, float sonar, float value, Vec3 pos)
    {
        GfxEntity gfx = gfxEngine.getDebugEntity(new Vec3[]
        {
            Vec3.VEC3_ZERO,
            axis.scale((Float.isInfinite(sonar) ? value : sonar))
        });

        gfx.position = pos;
        gfx.rotation = node.state.rot;
        gfx.scale = new Vec3(1);
        gfx.colour = MAGENTA;
    }

    @Override
    public void destroy()
    {
    }
}
