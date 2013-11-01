/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.ge.tokyo.ems.systems;

import afk.ge.ems.Engine;
import afk.ge.ems.ISystem;
import afk.ge.tokyo.ems.components.Mouse;
import afk.gfx.GraphicsEngine;
import static afk.ge.tokyo.FlagSources.*;

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
            engine.setFlag(KEYBOARD, i, gfxEngine.isKeyDown(i));
        }
        for (int i = 0; i < GraphicsEngine.NUM_MOUSE_BUTTONS; i++)
        {
            engine.setFlag(MOUSE, i, gfxEngine.isMouseDown(i));
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
