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

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.ems.Utils;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.SonarNode;
import static afk.gfx.GfxUtils.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class SonarSystem implements ISystem
{

    private Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }
    public static final Vec3[] POS_AXIS =
    {
        X_AXIS, Y_AXIS, Z_AXIS
    };
    public static final Vec3[] NEG_AXIS =
    {
        X_AXIS.getNegated(),
        Y_AXIS.getNegated(),
        Z_AXIS.getNegated()
    };

    @Override
    public void update(float t, float dt)
    {
        List<SonarNode> nodes = engine.getNodeList(SonarNode.class);
        List<CollisionNode> cnodes = engine.getNodeList(CollisionNode.class);

        for (SonarNode node : nodes)
        {
            Mat4 m = Utils.getMatrix(node.state);
            Vec3 pos = node.state.pos.add(m.multiply(node.bbox.offset.toDirection()).getXYZ());
            Mat4 rot = Utils.getRotationMatrix(node.state);
            Vec3[] posAxis = new Vec3[3];
            Vec3[] negAxis = new Vec3[3];
            for (int i = 0; i < 3; i++)
            {
                posAxis[i] = rot.multiply(POS_AXIS[i].toDirection()).getXYZ();
                negAxis[i] = rot.multiply(NEG_AXIS[i].toDirection()).getXYZ();
            }
            float[] mins = new float[3];
            float[] maxs = new float[3];
            for (int i = 0; i < 3; i++)
            {
                mins[i] = maxs[i] = Float.POSITIVE_INFINITY;
            }
            for (CollisionNode cnode : cnodes)
            {
                // stop sonar from picking up itself
                Controller controller = cnode.entity.getComponent(Controller.class);
                if (controller == node.controller)
                {
                    continue;
                }

                BBox bbox = new BBox(cnode.state, cnode.bbox);

                for (int i = 0; i < 3; i++)
                {
                    if (node.sonar.min.get(i) == 0)
                    {
                        mins[i] = Float.NaN;
                    } else
                    {
                        float dist = bbox.getEntrancePointDistance(
                                pos.subtract(m.multiply(NEG_AXIS[i].scale(node.bbox.extent.get(i)).toDirection()).getXYZ()),
                                negAxis[i]);
                        if (dist > node.sonar.min.get(i))
                        {
                            dist = Float.POSITIVE_INFINITY;
                        }
                        if (dist < mins[i])
                        {
                            mins[i] = dist;
                        }
                    }
                    if (node.sonar.max.get(i) == 0)
                    {
                        maxs[i] = Float.NaN;
                    } else
                    {
                        float dist = bbox.getEntrancePointDistance(
                                pos.add(m.multiply(POS_AXIS[i].scale(node.bbox.extent.get(i)).toDirection()).getXYZ()),
                                posAxis[i]);
                        if (dist > node.sonar.max.get(i))
                        {
                            dist = Float.POSITIVE_INFINITY;
                        }
                        if (dist < maxs[i])
                        {
                            maxs[i] = dist;
                        }
                    }
                }
            }
            node.controller.events.sonar.distance[0] = maxs[0];
            node.controller.events.sonar.distance[1] = maxs[1];
            node.controller.events.sonar.distance[2] = maxs[2];
            node.controller.events.sonar.distance[3] = mins[0];
            node.controller.events.sonar.distance[4] = mins[1];
            node.controller.events.sonar.distance[5] = mins[2];
        }
    }

    @Override
    public void destroy()
    {
    }
}
