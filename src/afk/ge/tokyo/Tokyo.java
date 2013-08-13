/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
import afk.bot.london.London;
import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.systems.CollisionSystem;
import afk.ge.tokyo.ems.systems.LifeSystem;
import afk.ge.tokyo.ems.systems.LifetimeSystem;
import afk.ge.tokyo.ems.systems.MovementSystem;
import afk.ge.tokyo.ems.systems.ParticleSystem;
import afk.ge.tokyo.ems.systems.ProjectileSystem;
import afk.ge.tokyo.ems.systems.RenderSystem;
import afk.ge.tokyo.ems.systems.RobotSystem;
import afk.ge.tokyo.ems.systems.TankControllerSystem;
import afk.ge.tokyo.ems.systems.VisionSystem;
import afk.gfx.GfxUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    EntityManager entityManager;
    Engine engine;
    boolean running = true;
    private AtomicBoolean gameInProgress = new AtomicBoolean(false);
    public static final float BOARD_SIZE = 50;
    final static float GAME_SPEED = 60;
    float t = 0.0f;
    final static float DELTA = 1.0f / GAME_SPEED;
    final static double NANOS_PER_SECOND = (double) GfxUtils.NANOS_PER_SECOND;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    final static double TARGET_FPS = 60;
    final static double MIN_FPS = 25;
    final static double MIN_FRAMETIME = 1.0f / TARGET_FPS;
    final static double MAX_FRAMETIME = 1.0f / MIN_FPS;

    public Tokyo(GraphicsEngine gfxEngine, London botEngine)
    {
        engine = new Engine();

        System.out.println("MAX_FRAMETIME = " + MAX_FRAMETIME);
        System.out.println("DELTA = " + DELTA);

        this.gfxEngine = gfxEngine;

        entityManager = new EntityManager(botEngine, engine);
        System.out.println("gfx" + gfxEngine.getFPS());
        
        ///possible move somewhere else later///
        engine.addSystem(new RobotSystem(botEngine)); // FIXME: remove passing of bot engine once db is done
        engine.addSystem(new TankControllerSystem(entityManager));
        engine.addSystem(new MovementSystem());
        engine.addSystem(new ProjectileSystem(entityManager));
        engine.addSystem(new LifeSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new ParticleSystem(entityManager));
        engine.addSystem(new LifetimeSystem(entityManager));
        engine.addSystem(new VisionSystem());
        engine.addSystem(new RenderSystem(gfxEngine));
        ///
    }

    @Override
    public void run()
    {
        gameLoop();
    }

    @Override
    public void startGame()
    {
//        System.out.println("startGame! " + javax.swing.SwingUtilities.isEventDispatchThread());
        gameInProgress.set(true);

    }

    @Override
    protected void gameLoop()
    {
        while (!gameInProgress.get())
        { /* spin! */

        }
        loadBots();
        double currentTime = System.nanoTime();
        double accumulator = 0.0f;
        int i = 0;
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

            int x = 0;
            while (accumulator >= DELTA)
            {
                updateGame();
                t += DELTA;
                accumulator -= DELTA;
                x++;
            }
            double alpha = accumulator / DELTA;
            render(alpha);
        }
    }

    @Override
    protected void updateGame()
    {
        entityManager.updateEntities(t, DELTA);
        engine.update(t, DELTA);
    }

    @Override
    protected void render(double alpha)
    {
        entityManager.renderEntities(alpha);

        gfxEngine.redisplay();
    }

    private boolean loadBots()
    {
        entityManager.createBots();
        System.out.println("Botsloaded");
        return true;
    }
}
