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
import afk.ge.tokyo.ems.nodes.HeightmapNode;
import afk.ge.tokyo.ems.nodes.MovementNode;
import java.util.List;
import static afk.ge.tokyo.HeightmapLoader.*;
import afk.ge.tokyo.ems.nodes.SnapToTerrainNode;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.hackoeur.jglm.support.FastMath;

/**
 *
 * @author Daniel
 */
public class SnapToTerrainSystem implements ISystem
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
        List<SnapToTerrainNode> nodes = engine.getNodeList(SnapToTerrainNode.class);
        HeightmapNode hnode = engine.getNodeList(HeightmapNode.class).get(0);

        for (SnapToTerrainNode node : nodes)
        {
            float x = node.state.pos.getX();
            float z = node.state.pos.getZ();
            float y = getHeight(x, z, hnode.heightmap);
            node.state.pos = new Vec3(x, y, z);
            
            Vec3 tankNormal = getNormal(x, z, hnode.heightmap).multiply(0.5f);
            Vec3 tankNX = new Vec3(tankNormal.getX(),tankNormal.getY(),0.0f).getUnitVector();
            Vec3 tankNZ = new Vec3(0.0f,tankNormal.getY(),tankNormal.getZ()).getUnitVector();
            
            float xRot = (float)FastMath.toDegrees(Math.asin(tankNZ.getZ()));
            float zRot = -(float)FastMath.toDegrees(Math.asin(tankNX.getX()));
            
            node.state.rot = new Vec4(xRot, node.state.rot.getY(), zRot, 0);
        }
    }

    @Override
    public void destroy()
    {
    }
}
