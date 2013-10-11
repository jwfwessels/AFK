package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Mouse;
import afk.ge.tokyo.ems.nodes.NoClipCameraNode;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class NoClipCameraSystem implements ISystem
{
    private Engine engine;
    
    private float prevX = 0, prevY = 0;

    @Override
    public boolean init(Engine engine)
    {
        this.engine = engine;
        return true;
    }

    @Override
    public void update(float t, float dt)
    {
        List<NoClipCameraNode> nodes = engine.getNodeList(NoClipCameraNode.class);
        Mouse mouse = engine.getGlobal(Mouse.class);
        
        float dx = prevX - mouse.x;
        float dy = prevY - mouse.y;
        prevX = mouse.x;
        prevY = mouse.y;
        
        for (NoClipCameraNode node : nodes)
        {
            Vec3 dir = node.camera.at.subtract(node.camera.eye).getUnitVector();
            Vec3 right = dir.cross(node.camera.up).getUnitVector();
            
            if (engine.getFlag("mouse", MouseEvent.BUTTON3))
            {
                final float lateral = dx * node.noclip.sensitivity;
                final float vertical = dy * node.noclip.sensitivity;

                rotate(node.camera, dir, right, lateral, vertical);
            }
            
            float amount = node.noclip.normalSpeed;

            if (engine.getFlag("keyboard", KeyEvent.VK_SHIFT))
            {
                amount *= node.noclip.sprintSpeed;
            }

            if (engine.getFlag("keyboard", KeyEvent.VK_W))
            {
                moveForward(node.camera, dir, amount);
            } else if (engine.getFlag("keyboard", KeyEvent.VK_S))
            {
                moveForward(node.camera, dir, -amount);
            }

            if (engine.getFlag("keyboard", KeyEvent.VK_D))
            {
                moveRight(node.camera, right, amount);
            } else if (engine.getFlag("keyboard", KeyEvent.VK_A))
            {
                moveRight(node.camera, right, -amount);
            }
        }
    }
    
    public void moveForward(Camera camera, Vec3 dir, float amount)
    {
        Vec3 step = dir.multiply(amount);
        
        camera.eye = camera.eye.add(step);
        camera.at = camera.at.add(step);
    }
    
    public void moveRight(Camera camera, Vec3 right, float amount)
    {
        Vec3 step = right.multiply(amount);
        
        camera.eye = camera.eye.add(step);
        camera.at = camera.at.add(step);
    }
    
    public void rotate(Camera camera, Vec3 dir, Vec3 right, float lateral, float vertical)
    {
        Vec4 d = new Vec4(dir.getX(), dir.getY(), dir.getZ(), 0.0f);

        Mat4 rot = new Mat4(1.0f);
        rot = Matrices.rotate(rot, vertical, right);
        rot = Matrices.rotate(rot, lateral, camera.up);

        d = rot.multiply(d);

        camera.at = camera.eye.add(new Vec3(d.getX(),d.getY(),d.getZ()));
    }

    @Override
    public void destroy()
    {
    }
    
}
