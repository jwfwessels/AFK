package afk.frontend.swing.config;

import afk.bot.RobotEngine;
import afk.ge.ems.Engine;
import static afk.ge.tokyo.Tokyo.DELTA;
import static afk.ge.tokyo.Tokyo.LOGIC_DELTA;
import static afk.ge.tokyo.Tokyo.MAX_FRAMETIME;
import static afk.ge.tokyo.Tokyo.NANOS_PER_SECOND;
import afk.ge.tokyo.ems.factories.GenericFactory;
import afk.ge.tokyo.ems.factories.RobotFactory;
import afk.ge.tokyo.ems.systems.PaintSystem;
import afk.ge.tokyo.ems.systems.RenderSystem;
import afk.gfx.GraphicsEngine;

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
    
    public ConfigEngine(GraphicsEngine gfxEngine, RobotEngine botEngine)
    {
        engine = new Engine();
        genericFactory = new GenericFactory();
        robotFactory = new RobotFactory(botEngine.getConfigManager(), genericFactory);

        engine.addLogicSystem(new PaintSystem());

        engine.addSystem(new RenderSystem(gfxEngine));
    }

    @Override
    public void run()
    {
        double currentTime = System.nanoTime();
        double accumulator = 0.0f;
        double logicAccumulator = 0.0f;
        while (running)
        {
            double newTime = System.nanoTime();
            double frameTime = (newTime - currentTime) / NANOS_PER_SECOND;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;
            }
            currentTime = newTime;

            logicAccumulator += frameTime;
            accumulator += frameTime;

            //any function called in this block should run at the fixed DELTA rate
            while (accumulator >= DELTA)
            {
                engine.update(t, DELTA);
                accumulator -= DELTA;
            }

            //any function called in this block run at the current speedMultiplier speed
            while (logicAccumulator >= LOGIC_DELTA)
            {
                engine.updateLogic(t, DELTA);
                t += DELTA;
                logicAccumulator -= LOGIC_DELTA;
            }
        }
    }
    
    
}
