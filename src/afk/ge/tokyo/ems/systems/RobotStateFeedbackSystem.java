package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.nodes.RobotNode;
import java.util.List;
import afk.ge.ems.Utils;
import com.hackoeur.jglm.Vec3;
import static com.hackoeur.jglm.support.FastMath.*;

/**
 *
 * @author Daniel
 */
public class RobotStateFeedbackSystem implements ISystem
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
        List<RobotNode> nodes = engine.getNodeList(RobotNode.class);
        for (RobotNode node : nodes)
        {
            float heading = node.state.rot.getY() % 360.0f;
            if (heading < 0) heading+= 360;
            node.controller.events.heading = heading;
            
            Vec3 forward = Utils.getForward(node.state);
            node.controller.events.pitch = (float)toDegrees(atan2(forward.getY(), forward.getLength()));
            
        }
    }

    @Override
    public void destroy()
    {
    }
    
}
