package afk.ge.tokyo.ems.systems;

import afk.bot.london.Sonar;
import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.ems.Utils;
import afk.ge.tokyo.ems.nodes.CollisionNode;
import afk.ge.tokyo.ems.nodes.SonarNode;
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

            final Vec3[] POS_AXIS =
            {
                X_AXIS, Y_AXIS, Z_AXIS
            };
            final Vec3[] NEG_AXIS =
            {
                X_AXIS.getNegated(),
                Y_AXIS.getNegated(),
                Z_AXIS.getNegated()
            };
            Sonar sonar = node.controller.events.sonar;
            Mat4 m = Utils.getMatrix(node.state);
            Vec3 pos = node.state.pos.add(m.multiply(node.bbox.offset.toDirection()).getXYZ());

            float[] sonarmins =
            {
                sonar.distance[3], sonar.distance[4], sonar.distance[5]
            };
            float[] sonarmaxes =
            {
                sonar.distance[0], sonar.distance[1], sonar.distance[2]
            };

            for (int i = 0; i < 3; i++)
            {
                if (!Float.isNaN(node.sonar.min.get(i)))
                {
                    drawSonar(node, NEG_AXIS[i], sonarmins[i],
                            node.sonar.min.get(i), pos.subtract(m.multiply(NEG_AXIS[i].scale(node.bbox.extent.get(i)).toDirection()).getXYZ()));
                }
                if (!Float.isNaN(node.sonar.max.get(i)))
                {
                    drawSonar(node, POS_AXIS[i], sonarmaxes[i],
                            node.sonar.max.get(i), pos.add(m.multiply(POS_AXIS[i].scale(node.bbox.extent.get(i)).toDirection()).getXYZ()));
                }
            }

            gfxEngine.getDebugEntity(new Vec3[]
            {
                new Vec3(-1000, 0, 0), new Vec3(1000, 0, 0)
            }).colour = new Vec3(0.7f, 0, 0);
            gfxEngine.getDebugEntity(new Vec3[]
            {
                new Vec3(0, -1000, 0), new Vec3(0, 1000, 0)
            }).colour = new Vec3(0, 0.7f, 0);
            gfxEngine.getDebugEntity(new Vec3[]
            {
                new Vec3(0, 0, -1000), new Vec3(0, 0, 1000)
            }).colour = new Vec3(0, 0, 0.7f);
        }

    }

    private void drawSonar(SonarNode node, Vec3 axis, float sonar, float value, Vec3 pos)
    {
        GfxEntity gfx = gfxEngine.getDebugEntity(new Vec3[]
        {
            Vec3.VEC3_ZERO,
            axis.scale((Float.isInfinite(sonar) ? value : sonar))
        });

        gfx.position = pos;
        gfx.rotation = node.state.rot;
        gfx.scale = new Vec3(1);
        gfx.colour = GfxEntity.MAGENTA;
    }

    @Override
    public void destroy()
    {
    }
}
