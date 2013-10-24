package afk.ge.tokyo.ems.systems;

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
import static afk.ge.tokyo.FlagSources.*;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Display;
import afk.ge.tokyo.ems.components.Mouse;
import afk.ge.tokyo.ems.components.Selection;
import afk.ge.tokyo.ems.nodes.SelectableNode;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class SelectionSystem implements ISystem
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
        if (!engine.getFlag(MOUSE, MouseEvent.BUTTON1))
        {
            return;
        }

        Camera camera = engine.getGlobal(Camera.class);
        Mouse mouse = engine.getGlobal(Mouse.class);
        Display display = engine.getGlobal(Display.class);

        List<SelectableNode> nodes = engine.getNodeList(SelectableNode.class);

        Vec3[] mousePoints = mouseToWorld(display, camera, mouse);

        Entity temp = null;
        float closest = Float.POSITIVE_INFINITY;
        for (SelectableNode node : nodes)
        {
            BBox bbox = new BBox(node.state, node.bbox);
            float dist = bbox.getEntrancePointDistance(mousePoints[0], mousePoints[1].subtract(mousePoints[0]).getUnitVector());
            if (dist < closest)
            {
                temp = node.entity;
                closest = dist;
                break;
            }
        }
        engine.addGlobal(new Selection(temp));
    }

    @Override
    public void destroy()
    {
    }

    public static Vec3[] mouseToWorld(Display display, Camera camera, Mouse mouse)
    {
        Vec3[] mousePoints = new Vec3[2];
        
        // TODO: these constants need to come from somewhere else!
        final float fov = 60.0f, near = 0.1f, far = 200.0f;

        Mat4 proj = Matrices.perspective(fov, display.screenWidth / display.screenHeight, near, far);
        Mat4 view = Matrices.lookAt(camera.eye, camera.at, camera.up);
        Mat4 cam = proj.multiply(view);
        Mat4 camInv = cam.getInverse();
        
        Vec4 mouseNear4 = camInv.multiply(new Vec4(mouse.nx, mouse.ny, 1, 1));
        Vec4 mouseFar4 = camInv.multiply(new Vec4(mouse.nx, mouse.ny, -1, 1));
        
        mousePoints[0] = mouseNear4.getXYZ().scale(1.0f / mouseNear4.getW());
        mousePoints[1] = mouseFar4.getXYZ().scale(1.0f / mouseFar4.getW());
        
        return mousePoints;
    }
}
