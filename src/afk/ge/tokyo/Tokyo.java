/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.bot.RobotEngine;
import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
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
import com.hackoeur.jglm.Vec3;
import java.util.UUID;

/**
 *
 * @author Jw
 */
public class Tokyo implements GameEngine, Runnable
{

    private EntityManager entityManager;
    private Engine engine;
    private GraphicsEngine gfxEngine;
    private boolean running = true;
    public static final float BOARD_SIZE = 50;
    public final static float GAME_SPEED = 60;
    private float t = 0.0f;
    public final static float DELTA = 1.0f / GAME_SPEED;
    public final static double NANOS_PER_SECOND = (double) GfxUtils.NANOS_PER_SECOND;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    public final static double TARGET_FPS = 60;
    public final static double MIN_FPS = 25;
    public final static double MIN_FRAMETIME = 1.0f / TARGET_FPS;
    public final static double MAX_FRAMETIME = 1.0f / MIN_FPS;

    public Tokyo(GraphicsEngine gfxEngine, RobotEngine botEngine)
    {
        engine = new Engine();

        System.out.println("MAX_FRAMETIME = " + MAX_FRAMETIME);
        System.out.println("DELTA = " + DELTA);

        this.gfxEngine = gfxEngine;

        entityManager = new EntityManager(engine);
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
    public void startGame(UUID[] participants)
    {
        entityManager.spawnStuff();
        entityManager.createObstacles(new Vec3(5, 5, 5));
        for (int i = 0; i < participants.length; i++)
        {
            entityManager.createTankEntityNEU(
                    participants[i],
                    EntityManager.SPAWN_POINTS[i],
                    EntityManager.BOT_COLOURS[i]);
        }
        
        new Thread(this).start();
    }

    @Override
    public void run()
    {
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
                engine.update(t, DELTA);
                t += DELTA;
                accumulator -= DELTA;
                x++;
            }
            double alpha = accumulator / DELTA;
            gfxEngine.redisplay();
        }
    }
}
