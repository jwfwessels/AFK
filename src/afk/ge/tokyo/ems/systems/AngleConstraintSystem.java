package afk.ge.tokyo.ems.systems;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.ISystem;
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
