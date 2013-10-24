package afk.rome;

import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import static afk.ge.tokyo.Tokyo.BOARD_SIZE;
import static afk.ge.tokyo.Tokyo.DELTA;
import static afk.ge.tokyo.Tokyo.MAX_FRAMETIME;
import static afk.ge.tokyo.Tokyo.NANOS_PER_SECOND;
import afk.ge.tokyo.ems.components.Camera;
import afk.ge.tokyo.ems.components.NoClipCamera;
import afk.ge.tokyo.ems.components.SpinnyCamera;
import afk.ge.tokyo.ems.factories.GenericFactory;
import afk.ge.tokyo.ems.factories.HeightmapFactory;
import afk.ge.tokyo.ems.factories.HeightmapFactoryRequest;
import afk.ge.tokyo.ems.systems.InputSystem;
import afk.ge.tokyo.ems.systems.MovementSystem;
import afk.ge.tokyo.ems.systems.NoClipCameraSystem;
import afk.ge.tokyo.ems.systems.PaintSystem;
import afk.ge.tokyo.ems.systems.RenderSystem;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class RomeEngine implements Runnable
{
    private Engine engine;
    private GenericFactory genericFactory;
    private boolean running;
    private float t = 0.0f;

    public RomeEngine(GraphicsEngine gfxEngine)
    {
        engine = new Engine();
        genericFactory = new GenericFactory();

        engine.addSystem(new NoClipCameraSystem());
        engine.addSystem(new InputSystem(gfxEngine));
        engine.addSystem(new RenderSystem(gfxEngine));
        
        Entity cameraEntity = new Entity();
        Camera camera = new Camera(
                new Vec3(BOARD_SIZE, 60, 0), // eye
                new Vec3(0f, -10f, 0f), // at
                new Vec3(0f, 1f, 0f)); // up
        cameraEntity.addComponent(camera);
        cameraEntity.addComponent(new NoClipCamera(1, 5, 0.2f));
        engine.addEntity(cameraEntity);
        engine.addGlobal(camera);
        
        
        
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
