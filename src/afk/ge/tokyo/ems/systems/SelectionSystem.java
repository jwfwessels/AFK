package afk.ge.tokyo.ems.systems;

import afk.ge.BBox;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
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
        if (!engine.getFlag("mouse", MouseEvent.BUTTON1))
        {
            return;
        }
        
        Camera camera = engine.getGlobal(Camera.class);
        Mouse mouse = engine.getGlobal(Mouse.class);
        Display display = engine.getGlobal(Display.class);
        
        List<SelectableNode> nodes = engine.getNodeList(SelectableNode.class);

        // TODO: these constants need to come from somewhere else!
        final float fov = 60.0f, near = 0.1f, far = 200.0f;
        
        Mat4 proj = Matrices.perspective(fov, display.screenWidth/display.screenHeight, near, far);
        Mat4 view = Matrices.lookAt(camera.eye, camera.at, camera.up);
        Mat4 cam = proj.multiply(view);
        Mat4 camInv = cam.getInverse();
        
        Vec4 mouseNear4 = camInv.multiply(new Vec4(mouse.nx,mouse.ny,1,1));
        Vec4 mouseFar4 = camInv.multiply(new Vec4(mouse.nx,mouse.ny,-1,1));

        Vec3 mouseNear = mouseNear4.getXYZ().scale(1.0f/mouseNear4.getW());
        Vec3 mouseFar = mouseFar4.getXYZ().scale(1.0f/mouseFar4.getW());

        Entity temp = null;
        float closest = Float.POSITIVE_INFINITY;
        for (SelectableNode node : nodes)
        {
            BBox bbox = new BBox(node.state, node.bbox);
            float dist = bbox.getEntrancePointDistance(mouseNear, mouseFar.subtract(mouseNear).getUnitVector());
            if (dist < closest)
            {
                temp = node.entity;
                dist = closest;
                break;
            }
        }
        engine.addGlobal(new Selection(temp));
    }

    @Override
    public void destroy()
    {
    }
}
