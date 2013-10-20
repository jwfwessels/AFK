package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Mouse;
import afk.ge.tokyo.ems.nodes.SelectableNode;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec4;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class SelectionSystem implements ISystem
{

    private Entity selected = null;
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

        if (camera == null || mouse == null)
        {
            return;
        }
        
        List<SelectableNode> nodes = engine.getNodeList(SelectableNode.class);

        // TODO: these constants need to come from somewhere else!
        final float fov = 60.0f, near = 0.1f, far = 200.0f;
        
        // TODO: need aspect ratio from somewhere :/
        //Mat4 proj = Matrices.perspective(fov, near, near, far);

        for (SelectableNode node : nodes)
        {
            Vec4 mouseNear = new Vec4(mouse.nx,mouse.ny,0,1);
            Vec4 mouseFar = new Vec4(mouse.nx,mouse.ny,1,1);
        }
    }

    @Override
    public void destroy()
    {
    }

    public Entity getSelected()
    {
        return selected;
    }
}
