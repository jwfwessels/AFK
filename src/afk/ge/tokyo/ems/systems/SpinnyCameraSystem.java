/*
 * Copyright (c) 2013 Triforce
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
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.SpinnyCameraNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class SpinnyCameraSystem implements ISystem
{

    private Engine engine;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<SpinnyCameraNode> nodes = engine.getNodeList(SpinnyCameraNode.class);

        for (SpinnyCameraNode node : nodes)
        {
            final float angleRad = (float) FastMath.toRadians(node.spinny.angle);
            final float pitchRad = (float) FastMath.toRadians(node.spinny.pitch);

            node.spinny.angle += dt * node.spinny.angularVelocity;
            node.camera.at = node.spinny.target;
            node.camera.up = new Vec3(0, 1, 0);
            float y = (float) FastMath.sin(pitchRad) * node.spinny.distance;
            float r = (float) FastMath.cos(pitchRad) * node.spinny.distance;
            float x = node.spinny.target.getX() + (float) FastMath.sin(angleRad) * r;
            float z = node.spinny.target.getZ() + (float) FastMath.cos(angleRad) * r;
            node.camera.eye = new Vec3(x, y, z);
        }
    }

    @Override
    public void destroy()
    {
    }
}
