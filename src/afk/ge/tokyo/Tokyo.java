/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.EntityFactory;
import afk.ge.GameEngine;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.gfx.ResourceNotLoadedException;
import afk.london.London;
import afk.london.Robot;
import afk.london.SampleBot;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    ArrayList<AbstractEntity> entities;
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
        entities = EntityFactory.getEntityList();
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

                try {

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
                    projectileGfxEntity.setScale(0.1f, 0.1f, 0.1f);
                    projectileGfxEntity.setPosition(5, 10, 5);

                    TankEntity tank;//new TankEntity(tankGfxEntity);
                    tank = (TankEntity) TankFactory.createEntity(tankGfxEntity);
                    ProjectileEntity bullet = (ProjectileEntity) EntityFactory.createEntity(projectileGfxEntity);//new ProjectileEntity(projectileGfxEntity);
//                  addEntity(tank);
                    tank.setProjectile(bullet);
//                  addEntity(bullet);

                } catch (ResourceNotLoadedException ex) {
                    //System.err.println("Failed to load resource: " + ex.getMessage());
                    throw new RuntimeException(ex);
                }

                loaded.set(true);

            }
        });
    }

    @Override
    public void addEntity(AbstractEntity entity)
    {
        entities.add(entity);
    }

    @Override
    public void run()
    {
        //System.out.println("run()");
        loadResources();

        loadBots();

        while (!loaded.get()) { /* spin */ }

        gameLoop();
    }

    @Override
    protected void gameLoop()
    {
//        System.out.println("gameLoop()");
        //        double lastRender = System.nanoTime();
        double currentTime = System.nanoTime();
        float accumulator = 0.0f;
        int i = 0;
        while (running) {
//            System.out.println("loop: " + i);
            double newTime = System.nanoTime();
            double frameTime = newTime - currentTime;
            if (frameTime > MAX_FRAMETIME) {
                frameTime = MAX_FRAMETIME;	  //max frame
            }
            currentTime = newTime;

            accumulator += frameTime;

            while (accumulator >= DELTA) {
//                previousState = currentState;
                boolean[] flags = getInputs();
                //System.out.println("falg[0] " + flags[0]);
                updateGame(flags); //integrate (currentState ,t ,td)
                t += DELTA;
                accumulator -= DELTA;
            }

            float alpha = accumulator / DELTA;

            //State state = currentState * alpha = previousState* (1.0-alpha);
            render(alpha);//render (state)

//            System.out.println("render: " + i);
//            i++;

//            double interpolation = Math.min(1.0, (newTime - currentTime) / dt);
        }
    }

    @Override
    protected void updateGame(boolean[] flags)
    {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update(t, DELTA, flags);
        }
//        entities.get(0).update(t, DELTA, flags);
    }

    @Override
    protected void render(float alpha)
    {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).render(alpha);
        }
        //there is no method available in GraphicsEngine 
        //that can be invoked to cause a display() sequence to run.
        gfxEngine.redisplay();
    }

    private boolean[] getInputs()
    {
//        boolean[] flags = new boolean[]
//        {
//            
//            gfxEngine.isKeyDown(VK_UP),
//            gfxEngine.isKeyDown(VK_DOWN),
//            gfxEngine.isKeyDown(VK_LEFT),
//            gfxEngine.isKeyDown(VK_RIGHT)
//        };

        ArrayList<Robot> bots = London.getRobots();
        bots.get(0).run();
        boolean[] temp = bots.get(0).getActionFlags();
        boolean[] flags2 = new boolean[temp.length];
        System.arraycopy(temp, 0, flags2, 0, temp.length);
        bots.get(0).clearFlags();
        return flags2;
    }

    private void loadBots()
    {

        Robot bot = new SampleBot();
        London.registerBot(bot);
    }
}
