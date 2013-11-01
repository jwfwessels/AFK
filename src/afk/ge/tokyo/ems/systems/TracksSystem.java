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

import static afk.bot.london.TankRobot.*;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.TracksNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.List;

/**
 *
 * @author Jw
 */
public class TracksSystem implements ISystem
{

    Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<TracksNode> nodes = engine.getNodeList(TracksNode.class);
        for (TracksNode node : nodes)
        {
            float angle = -(float) Math.toRadians(node.state.rot.getY());
            float sin = (float) Math.sin(angle);
            float cos = (float) Math.cos(angle);
            if (engine.getFlag(node.controller.id, MOVE_FRONT))
            {
                node.velocity.v = new Vec3(-(node.motor.topSpeed * sin), 0, node.motor.topSpeed * cos);
            } else if (engine.getFlag(node.controller.id, MOVE_BACK))
            {
                node.velocity.v = new Vec3(node.motor.topSpeed * sin, 0, -(node.motor.topSpeed * cos));
            } else
            {
                node.velocity.v = Vec3.VEC3_ZERO;
            }
            if (engine.getFlag(node.controller.id, TURN_CLOCK))
            {
                node.velocity.av = new Vec4(0, -node.motor.angularVelocity, 0, 0);
            } else if (engine.getFlag(node.controller.id, TURN_ANTICLOCK))
            {
                node.velocity.av = new Vec4(0, node.motor.angularVelocity, 0, 0);
            } else
            {
                node.velocity.av = Vec4.VEC4_ZERO;
            }
        }
    }

    @Override
    public void destroy()
    {
    }
}
