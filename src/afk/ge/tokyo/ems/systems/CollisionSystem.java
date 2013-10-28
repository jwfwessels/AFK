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

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Velocity;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class CollisionSystem implements ISystem
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
        List<CollisionNode> nodes = engine.getNodeList(CollisionNode.class);
        
        for (CollisionNode nodeA : nodes)
        {
            // stop collision detection between static objects
            if (!nodeA.entity.hasComponent(Velocity.class)) continue;
            
            BBox boxA = new BBox(nodeA.state, nodeA.bbox);
            for (CollisionNode nodeB : nodes)
            {
                if (nodeA == nodeB) continue;
                
                BBox boxB = new BBox(nodeB.state, nodeB.bbox);
                
                // FIXME: bots check against each other twice
                if (boxA.isBoxInBox(boxB))
                {
                    nodeA.state.set(nodeA.state.prevPos, nodeA.state.prevRot, nodeA.state.prevScale);
                    nodeB.state.set(nodeB.state.prevPos, nodeB.state.prevRot, nodeB.state.prevScale);
                    
                    Controller controller = nodeA.entity.getComponent(Controller.class);
                    if (controller != null)
                    {
                        controller.events.hitWall = true;
                    }
                    controller = nodeB.entity.getComponent(Controller.class);
                    if (controller != null)
                    {
                        controller.events.hitWall = true;
                    }
                }
            }
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
