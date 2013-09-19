package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
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
