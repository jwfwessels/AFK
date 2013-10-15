package afk.ge.tokyo.ems.systems;

import afk.bot.london.Sonar;
import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.ems.Utils;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.SonarNode;
import static afk.ge.tokyo.ems.systems.SonarSystem.NEG_AXIS;
import static afk.ge.tokyo.ems.systems.SonarSystem.POS_AXIS;
import afk.gfx.GfxEntity;
import static afk.gfx.GfxUtils.X_AXIS;
import static afk.gfx.GfxUtils.Y_AXIS;
import static afk.gfx.GfxUtils.Z_AXIS;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import java.util.List;

/**
 *
 * @author daniel
 */
public class DebugRenderSystem implements ISystem
{

    Engine engine;
    GraphicsEngine gfxEngine;

    public DebugRenderSystem(GraphicsEngine gfxEngine)
    {
        this.gfxEngine = gfxEngine;
    }

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
        for (CollisionNode node : nodes)
        {
            BBox bbox = new BBox(node.state, node.bbox);

            GfxEntity gfx = gfxEngine.getDebugEntity(bbox);

            gfx.position = bbox.getCenterPoint();
            gfx.rotation = node.state.rot;
            gfx.scale = new Vec3(1);
            gfx.colour = GfxEntity.MAGENTA;
        }

        List<SonarNode> snodes = engine.getNodeList(SonarNode.class);
        for (SonarNode node : snodes)
        {
            Mat4 rot = Utils.getRotationMatrix(node.state);
            Vec3[] axis = {
                X_AXIS, Y_AXIS, Z_AXIS,
                X_AXIS.getNegated(),
                Y_AXIS.getNegated(),
                Z_AXIS.getNegated()
            };
            Sonar sonar = node.controller.events.sonar;
            Vec3 pos = node.state.pos;

            float[] sonars =
            {
                sonar.right, sonar.up, sonar.front,
                sonar.left, sonar.down, sonar.back
            };
            
            float[] maxes =
            {
                node.sonar.max.getX(),
                node.sonar.max.getY(),
                node.sonar.max.getZ(),
                node.sonar.min.getX(),
                node.sonar.min.getY(),
                node.sonar.min.getZ(),
            };

            for (int i = 0; i < 6; i++)
            {
                if (!Float.isNaN(sonars[i]))
                {
                    GfxEntity gfx = gfxEngine.getDebugEntity(new Vec3[]
                    {
                        Vec3.VEC3_ZERO,
                        axis[i].scale(Float.isInfinite(sonars[i]) ? maxes[i] : sonars[i])
                    });
                    
                    gfx.position = pos;
                    gfx.rotation = node.state.rot;
                    gfx.scale = new Vec3(1);
                    gfx.colour = GfxEntity.MAGENTA;
                }
            }
        }

    }

    @Override
    public void destroy()
    {
    }
}
