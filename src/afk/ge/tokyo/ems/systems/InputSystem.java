package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Mouse;
import afk.gfx.GraphicsEngine;

/**
 *
 * @author Daniel
 */
public class InputSystem implements ISystem
{
    private Engine engine;
    private GraphicsEngine gfxEngine;

    public InputSystem(GraphicsEngine gfxEngine)
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
        for (int i = 0; i < GraphicsEngine.NUM_KEYS; i++)
        {
            engine.setFlag("keyboard", i, gfxEngine.isKeyDown(i));
        }
        for (int i = 0; i < GraphicsEngine.NUM_MOUSE_BUTTONS; i++)
        {
            engine.setFlag("mouse", i, gfxEngine.isMouseDown(i));
        }
        
        Mouse mouse = engine.getGlobal(Mouse.class);
        
        mouse.x = (float)gfxEngine.getMouseX();
        mouse.y = (float)gfxEngine.getMouseY();
        mouse.nx = (mouse.x/gfxEngine.getWidth())*2.0f-1.0f;
        mouse.ny = (mouse.y/gfxEngine.getHeight())*-2.0f+1.0f;
    }

    @Override
    public void destroy()
    {
    }
    
}
