/*
 * Copyright (c) 2013 Triforce
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
 package afk.ge.tokyo.test;

import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import static afk.ge.tokyo.Tokyo.DELTA;
import static afk.ge.tokyo.Tokyo.MAX_FRAMETIME;
import static afk.ge.tokyo.Tokyo.NANOS_PER_SECOND;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.Mouse;
import afk.ge.tokyo.ems.components.NoClipCamera;
import afk.ge.tokyo.ems.factories.GenericFactory;
import afk.ge.tokyo.ems.systems.DebugRenderSystem;
import afk.ge.tokyo.ems.systems.InputSystem;
import afk.ge.tokyo.ems.systems.NoClipCameraSystem;
import afk.ge.tokyo.ems.systems.RenderSystem;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Daniel
 */
public class RayBoxEngine extends Engine implements Runnable
{
    private GenericFactory genericFactory;
    private boolean running;
    private float t = 0.0f;
    private Entity oldEntity = null;
    private Entity currentEntity = null;

    public RayBoxEngine(GraphicsEngine gfxEngine)
    {
        genericFactory = new GenericFactory();

        addSystem(new InputSystem(gfxEngine));
        addSystem(new NoClipCameraSystem());
        addSystem(new RenderSystem(gfxEngine));
        addSystem(new DebugRenderSystem(gfxEngine));
        
        addGlobal(new Mouse());
        
        // TODO: put this somewhere else?
        Entity entity = new Entity();
        Camera camera = new Camera(
                new Vec3(20f, 20f, 20f),
                new Vec3(0f, 0f, 0f),
                new Vec3(0f, 1f, 0f));
        entity.addComponent(camera);
        entity.addComponent(new NoClipCamera(1, 5, 0.2f));
        addEntity(entity);
        addGlobal(camera);
    }

    public void start()
    {
        running = true;
        new Thread(this).start();
    }

    public void stop()
    {
        //engine.shutDown();
        running = false;
    }

    @Override
    public void run()
    {
        double currentTime = System.nanoTime();
        double accumulator = 0.0f;
        while (running)
        {
            double newTime = System.nanoTime();
            double frameTime = (newTime - currentTime) / NANOS_PER_SECOND;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;
            }
            currentTime = newTime;

            accumulator += frameTime;

            //any function called in this block should run at the fixed DELTA rate
            while (accumulator >= DELTA)
            {
                update(t, DELTA);
                accumulator -= DELTA;
            }
        }
    }
}
