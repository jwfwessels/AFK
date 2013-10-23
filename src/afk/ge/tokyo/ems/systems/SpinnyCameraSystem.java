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
