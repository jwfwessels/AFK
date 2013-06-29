/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.gfx.ResourceNotLoadedException;
import afk.london.London;
import afk.london.Robot;
import afk.london.SampleBot;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    ArrayList<Entity> entities;
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
        entities = new ArrayList<Entity>();
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

                    GfxEntity floorGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
                    gfxEngine.attachResource(floorGfxEntity, floorMesh);
                    gfxEngine.attachResource(floorGfxEntity, floorShader);
                    floorGfxEntity.setScale(50, 50, 50);

                    GfxEntity tankGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
                    gfxEngine.attachResource(tankGfxEntity, tankMesh);
                    gfxEngine.attachResource(tankGfxEntity, tankShader);
                    tankGfxEntity.colour = new Vec3(0.0f,0.5f,1.0f);
                    
                    addEntity(new Entity(tankGfxEntity));
                    
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
    public void addEntity(Entity entity)
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
        while (running)
        {
//            System.out.println("loop: " + i);
            double newTime = System.nanoTime();
            double frameTime = newTime - currentTime;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;	  //max frame
            }
            currentTime = newTime;

            accumulator += frameTime;

            while (accumulator >= DELTA)
            {
                //previousState = currentState
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
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).update(t, DELTA, flags);
        }
    }

    @Override
    protected void render(float alpha)
    {
        for (int i = 0; i < entities.size(); i++)
        {
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
        boolean [] flags2 = bots.get(0).getActionFlags();
        bots.get(0).clearFlags();
        return flags2;
    }

    private void loadBots()
    {

        Robot bot = new SampleBot();
        London.registerBot(bot);
    }
}
