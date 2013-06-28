/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.GameEngine;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.gfx.ResourceNotLoadedException;
import afk.london.London;
import afk.london.Robot;
import afk.london.SampleBot;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    EntityManager entityManager;
    boolean running = true;
    final static float GAME_SPEED = 60;
    float t = 0.0f;
    public static final float NANOS_PER_SECOND = 1.0f;
    final static float DELTA = NANOS_PER_SECOND / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    final static double TARGET_FPS = 90;
    final static double MIN_FPS = 25;
    final static double MIN_FRAMETIME = NANOS_PER_SECOND / TARGET_FPS;
    final static double MAX_FRAMETIME = NANOS_PER_SECOND / MIN_FPS;
    private AtomicBoolean loaded = new AtomicBoolean(false);

    public Tokyo(GraphicsEngine gfxEngine)
    {
        this.gfxEngine = gfxEngine;
        entityManager = new EntityManager();
    }

    @Override
    protected void loadResources()
    {
        final Resource tankMesh = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "tank");
        final Resource tankShader = gfxEngine.loadResource(Resource.SHADER, "monkey");

        final Resource floorMesh = gfxEngine.loadResource(Resource.PRIMITIVE_MESH, "quad");
        final Resource floorShader = gfxEngine.loadResource(Resource.SHADER, "floor");

        gfxEngine.dispatchLoadQueue(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {

                    GfxEntity floorGfxEntity = gfxEngine.createEntity();
                    gfxEngine.attachResource(floorGfxEntity, floorMesh);
                    gfxEngine.attachResource(floorGfxEntity, floorShader);
                    floorGfxEntity.setScale(50, 50, 50);

                    GfxEntity tankGfxEntity = gfxEngine.createEntity();
                    gfxEngine.attachResource(tankGfxEntity, tankMesh);
                    gfxEngine.attachResource(tankGfxEntity, tankShader);

                    //dont have a projectile model yet, mini tank will be bullet XD
                    GfxEntity projectileGfxEntity = gfxEngine.createEntity();
                    gfxEngine.attachResource(projectileGfxEntity, tankMesh);
                    gfxEngine.attachResource(projectileGfxEntity, tankShader);
                    projectileGfxEntity.setScale(0.5f, 0.5f, 0.5f);
                    projectileGfxEntity.setPosition(5, 10, 5);

                    TankEntity tank = (TankEntity) entityManager.createTank(tankGfxEntity);
                    ProjectileEntity bullet = (ProjectileEntity) entityManager.createProjectile(projectileGfxEntity);//new ProjectileEntity(projectileGfxEntity);
//                  addEntity(tank);
                    tank.setProjectileGfx(bullet);
//                  addEntity(bullet);

                } catch (ResourceNotLoadedException ex)
                {
                    //System.err.println("Failed to load resource: " + ex.getMessage());
                    throw new RuntimeException(ex);
                }

                loaded.set(true);

            }
        });
    }

    @Override
    public void run()
    {
        loadResources();

        loadBots();

        while (!loaded.get())
        { /* spin */ }

        gameLoop();
    }

    @Override
    protected void gameLoop()
    {
        double currentTime = System.nanoTime();
        float accumulator = 0.0f;
        int i = 0;
        while (running)
        {
            double newTime = System.nanoTime();
            double frameTime = newTime - currentTime;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;
            }
            currentTime = newTime;

            accumulator += frameTime;

            while (accumulator >= DELTA)
            {
                updateGame();
                t += DELTA;
                accumulator -= DELTA;
            }
            float alpha = accumulator / DELTA;
            render(alpha);
        }
    }

    @Override
    protected void updateGame()
    {
        entityManager.updateEntities(t, DELTA);
    }

    @Override
    protected void render(float alpha)
    {
        entityManager.renderEntities(alpha);
        gfxEngine.redisplay();
    }

    private void loadBots()
    {
        //TODO refactor load bots
        Robot bot = new SampleBot();
        London.registerBot(bot);
    }
}
