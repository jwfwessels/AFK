/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.bot.RobotEngine;
import afk.game.GameCoordinator;
import afk.game.GameListener;
import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.ems.FactoryException;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Paint;
import afk.ge.tokyo.ems.components.Renderable;
import afk.ge.tokyo.ems.components.Spawn;
import afk.ge.tokyo.ems.components.State;
import afk.ge.tokyo.ems.factories.GenericFactory;
import afk.ge.tokyo.ems.factories.GenericFactoryRequest;
import afk.ge.tokyo.ems.factories.HeightmapFactory;
import afk.ge.tokyo.ems.factories.HeightmapFactoryRequest;
import afk.ge.tokyo.ems.factories.ObstacleFactory;
import afk.ge.tokyo.ems.factories.ObstacleFactoryRequest;
import afk.ge.tokyo.ems.systems.AngleConstraintSystem;
import afk.ge.tokyo.ems.systems.CollisionSystem;
import afk.ge.tokyo.ems.systems.DebugRenderSystem;
import afk.ge.tokyo.ems.systems.SnapToTerrainSystem;
import afk.ge.tokyo.ems.systems.DebugSystem;
import afk.ge.tokyo.ems.systems.GameStateSystem;
import afk.ge.tokyo.ems.systems.LifeSystem;
import afk.ge.tokyo.ems.systems.LifetimeSystem;
import afk.ge.tokyo.ems.systems.MovementSystem;
import afk.ge.tokyo.ems.systems.PaintSystem;
import afk.ge.tokyo.ems.systems.ParticleSystem;
import afk.ge.tokyo.ems.systems.ProjectileSystem;
import afk.ge.tokyo.ems.systems.RenderSystem;
import afk.ge.tokyo.ems.systems.RobotSystem;
import afk.ge.tokyo.ems.systems.SpawnSystem;
import afk.ge.tokyo.ems.systems.TankBarrelFeedbackSystem;
import afk.ge.tokyo.ems.systems.TankBarrelSystem;
import afk.ge.tokyo.ems.systems.TankTracksSystem;
import afk.ge.tokyo.ems.systems.TankTurretFeedbackSystem;
import afk.ge.tokyo.ems.systems.TankTurretSystem;
import afk.ge.tokyo.ems.systems.VisionSystem;
import afk.gfx.GfxUtils;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author Jw
 */
public class Tokyo implements GameEngine, Runnable
{

    private Engine engine;
    private GraphicsEngine gfxEngine;
    private boolean running = true;
    private boolean paused = false;
    private boolean gameOver = false;
    public static final float BOARD_SIZE = 100;
    public final static float GAME_SPEED = 30;
    private float t = 0.0f;
    public final static float DELTA = 1.0f / GAME_SPEED;
    public static float LOGIC_DELTA = DELTA;
    private float speedMultiplier = 1;
    public final static double NANOS_PER_SECOND = (double) GfxUtils.NANOS_PER_SECOND;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    public final static double TARGET_FPS = 60;
    public final static double MIN_FPS = 25;
    public final static double MIN_FRAMETIME = 1.0f / TARGET_FPS;
    public final static double MAX_FRAMETIME = 1.0f / MIN_FPS;
//test()
    private long lastUpdate;
    private float time = 0.0f;
    private float lastFPS = 0.0f;
    private float fps = 0.0f;
//

    public Tokyo(GraphicsEngine gfxEngine, RobotEngine botEngine, GameCoordinator gm)
    {
        engine = new Engine();

        System.out.println("MAX_FRAMETIME = " + MAX_FRAMETIME);
        System.out.println("DELTA = " + DELTA);

        this.gfxEngine = gfxEngine;

        System.out.println("gfx" + gfxEngine.getFPS());

        ///possible move somewhere else later///
        engine.addLogicSystem(new SpawnSystem());
        engine.addLogicSystem(new PaintSystem());
        engine.addLogicSystem(new RobotSystem(botEngine)); // FIXME: remove passing of bot engine once db is done
        engine.addLogicSystem(new TankTracksSystem());
        engine.addLogicSystem(new TankTurretSystem());
        engine.addLogicSystem(new TankBarrelSystem());
        engine.addLogicSystem(new MovementSystem());
        engine.addLogicSystem(new SnapToTerrainSystem());
        engine.addLogicSystem(new AngleConstraintSystem());
        engine.addLogicSystem(new TankTurretFeedbackSystem());
        engine.addLogicSystem(new TankBarrelFeedbackSystem());
        engine.addLogicSystem(new ProjectileSystem());
        engine.addLogicSystem(new LifeSystem());
        engine.addLogicSystem(new CollisionSystem());
        engine.addLogicSystem(new ParticleSystem());
        engine.addLogicSystem(new LifetimeSystem());
        engine.addLogicSystem(new VisionSystem());
        engine.addLogicSystem(new GameStateSystem(gm));

        engine.addSystem(new RenderSystem(gfxEngine));

        // TODO: if (DEBUG)  ...
        DebugRenderSystem wireFramer = new DebugRenderSystem(gfxEngine);
        engine.addSystem(new DebugSystem(botEngine, wireFramer));
        ///
    }
    
    // TODO: make these customisable/generic/not hard-coded/just somewhere else!
    public static final int SPAWNVALUE = (int) (BOARD_SIZE * 0.45);
    public static final Vec3[] BOT_COLOURS =
    {
        new Vec3(1, 0, 0),
        new Vec3(0, 0, 1),
        new Vec3(0, 1, 0),
        new Vec3(1, 1, 0),
        new Vec3(1, 0, 1),
        new Vec3(0, 1, 1),
        new Vec3(0.95f, 0.95f, 0.95f),
        new Vec3(0.2f, 0.2f, 0.2f)
    };
    public static final Vec3[] SPAWN_POINTS =
    {
        new Vec3(-SPAWNVALUE, 0, -SPAWNVALUE),
        new Vec3(SPAWNVALUE, 0, SPAWNVALUE),
        new Vec3(-SPAWNVALUE, 0, SPAWNVALUE),
        new Vec3(SPAWNVALUE, 0, -SPAWNVALUE),
        new Vec3(0, 0, -SPAWNVALUE),
        new Vec3(0, 0, SPAWNVALUE),
        new Vec3(-SPAWNVALUE, 0, 0),
        new Vec3(SPAWNVALUE, 0, 0)
    };

    @Override
    public void startGame(UUID[] participants)
    {
        spawnStuff();
        GenericFactory factory = new GenericFactory();
        try
        {
            GenericFactoryRequest request = GenericFactoryRequest.load("largeTank");
            //entityManager.createObstacles(new Vec3(5, 5, 5));
            for (int i = 0; i < participants.length; i++)
            {
                Entity entity = factory.create(request);
                entity.add(new Spawn(SPAWN_POINTS[i], Vec4.VEC4_ZERO));
                entity.addToDependents(new Paint(BOT_COLOURS[i]));
                entity.addToDependents(new Controller(participants[i]));
                engine.addEntity(entity);
            }

            new Thread(this).start();
        } catch (IOException ex)
        {
            // FIXME: this needs to propagate somewhere else!
            ex.printStackTrace(System.err);
        } catch (FactoryException ex)
        {
            // FIXME: this needs to propagate somewhere else!
            ex.printStackTrace(System.err);
        }
    }

    // TODO: this should be put in a map file
    private void spawnStuff()
    {
        engine.addEntity(new HeightmapFactory().create(new HeightmapFactoryRequest("hm2")));
        ObstacleFactory factory = new ObstacleFactory();

        engine.addEntity(factory.create(new ObstacleFactoryRequest(new Vec3(0, 0, -Tokyo.BOARD_SIZE / 2), new Vec3(Tokyo.BOARD_SIZE, 5, 0.5f), "wall")));
        engine.addEntity(factory.create(new ObstacleFactoryRequest(new Vec3(0, 0, Tokyo.BOARD_SIZE / 2), new Vec3(Tokyo.BOARD_SIZE, 5, 0.5f), "wall")));
        engine.addEntity(factory.create(new ObstacleFactoryRequest(new Vec3(Tokyo.BOARD_SIZE / 2, 0, 0), new Vec3(0.5f, 5, Tokyo.BOARD_SIZE), "wall")));
        engine.addEntity(factory.create(new ObstacleFactoryRequest(new Vec3(-Tokyo.BOARD_SIZE / 2, 0, 0), new Vec3(0.5f, 5, Tokyo.BOARD_SIZE), "wall")));
    }

    @Override
    public void setState(int i, String msg)
    {
        switch (i)
        {
            case 0:
                gameOver = true;
                System.out.println("DRAW");
                break;
            case 1:
                gameOver = true;
                System.out.println("WINNER " + msg);
                break;
            case 2:
                paused = !paused;
                System.out.println("state: " + msg);
                break;
        }
    }

//    @Override
//    public void playPause()
//    {
//        System.out.println("playPause() - " + paused);
//        paused = !paused;
//        System.out.println("paused: " + paused);
//    }
    @Override
    public float getSpeed()
    {
        return speedMultiplier;
    }

    @Override
    public void increaseSpeed()
    {
        speedMultiplier *= 2;
        LOGIC_DELTA = 1 / (GAME_SPEED * speedMultiplier);
    }

    @Override
    public void decreaseSpeed()
    {
        speedMultiplier /= 2;
        LOGIC_DELTA = 1.0f / (GAME_SPEED * speedMultiplier);
    }

    @Override
    public void run()
    {
        double currentTime = System.nanoTime();
        lastUpdate = System.nanoTime();
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
                if (!paused && !gameOver)
                {
                    engine.updateLogic(t, DELTA);
                }
                t += DELTA;
                logicAccumulator -= LOGIC_DELTA;
            }
        }
    }
}
