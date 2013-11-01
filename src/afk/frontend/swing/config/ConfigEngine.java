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
 package afk.frontend.swing.config;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import static afk.ge.tokyo.Tokyo.DELTA;
import static afk.ge.tokyo.Tokyo.MAX_FRAMETIME;
import static afk.ge.tokyo.Tokyo.NANOS_PER_SECOND;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.SpinnyCamera;
import afk.ge.tokyo.ems.factories.GenericFactory;
import afk.ge.tokyo.ems.factories.RobotFactory;
import afk.ge.tokyo.ems.factories.RobotFactoryRequest;
import afk.ge.tokyo.ems.systems.MovementSystem;
import afk.ge.tokyo.ems.systems.PaintSystem;
import afk.ge.tokyo.ems.systems.RenderSystem;
import afk.ge.tokyo.ems.systems.SpinnyCameraSystem;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Vec3;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Daniel
 */
public class ConfigEngine implements Runnable
{

    private Engine engine;
    private RobotFactory robotFactory;
    private GenericFactory genericFactory;
    private boolean running;
    private float t = 0.0f;
    private Entity oldEntity = null;
    private Entity currentEntity = null;
    private AtomicBoolean changed = new AtomicBoolean(false);

    public ConfigEngine(GraphicsEngine gfxEngine, RobotConfigManager configManager)
    {
        engine = new Engine();
        genericFactory = new GenericFactory();
        robotFactory = new RobotFactory(configManager, genericFactory);

        engine.addSystem(new SpinnyCameraSystem());
        engine.addSystem(new PaintSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new RenderSystem(gfxEngine));
        
        Entity entity = new Entity();
        Camera camera = new Camera(
                new Vec3(5, 5, 5),
                new Vec3(0f, 0f, 0f),
                new Vec3(0f, 1f, 0f));
        entity.addComponent(camera);
        entity.addComponent(new SpinnyCamera(45, 20, 30, 5, Vec3.VEC3_ZERO));
        engine.addEntity(entity);
        engine.addGlobal(camera);
    }

    public void setDisplayedRobot(Robot robot)
    {
        // wait until any previous change has been dealt with
        // this shouldn't happen under the given curcumstances
        // put this here "just in case"
        while (changed.get())
        {
            /* spin */
        }

        oldEntity = currentEntity;
        currentEntity = robotFactory.create(new RobotFactoryRequest(robot, Vec3.VEC3_ZERO, new Vec3(1)));

        changed.set(true);
    }

    public void start()
    {
        running = true;
        new Thread(this).start();
    }

    public void stop()
    {
        running = false;
    }

    @Override
    public void run()
    {
        double currentTime = System.nanoTime();
        double accumulator = 0.0f;
        while (running)
        {
            if (changed.getAndSet(false))
            {
                if (oldEntity != null)
                {
                    engine.removeEntity(oldEntity);
                }
                engine.addEntity(currentEntity);
            }

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
                engine.update(t, DELTA);
                accumulator -= DELTA;
            }
        }
        engine.shutDown();
    }
}
