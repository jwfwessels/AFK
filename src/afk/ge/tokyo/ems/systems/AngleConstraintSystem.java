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
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.AngleConstraintNode;
import java.util.List;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Jw
 */
public class AngleConstraintSystem implements ISystem
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
        List<AngleConstraintNode> nodes = engine.getNodeList(AngleConstraintNode.class);
        for (AngleConstraintNode node : nodes)
        {
            float[] newVec = new float[4];
            for (int i = 0; i < 4; i++)
            {
                float v = node.state.rot.get(i);
                float min = node.constraint.min.get(i);
                float max = node.constraint.max.get(i);
                if (v < min)
                    newVec[i] = min;
                else if (v > max)
                    newVec[i] = max;
                else
                    newVec[i] = v;
            }
            node.state.rot = new Vec4(newVec[0],newVec[1],newVec[2],newVec[3]);
        }
    }

    @Override
    public void destroy()
    {
    }
}
