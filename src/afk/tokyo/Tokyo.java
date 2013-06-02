/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GraphicsEngine;
import java.util.ArrayList;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    ArrayList<Entity> entities;
    boolean running = true;
    final static float GAME_SPEED = 30;
    float t = 0.0f;
    public static final float NANOS_PER_SECOND = 1000000000;
    final static float DELTA = NANOS_PER_SECOND / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    final static float TARGET_FPS = 60;
    final static float MIN_FPS = 25;
    final static float MIN_FRAMETIME = NANOS_PER_SECOND / TARGET_FPS;
    final static float MAX_FRAMETIME = NANOS_PER_SECOND / MIN_FPS;

    public Tokyo(GraphicsEngine gfxEngine)
    {
        this.gfxEngine = gfxEngine;
        entities = new ArrayList<Entity>();
    }

    @Override
    protected void loadResources()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addEntity(Entity tankEntity)
    {
        entities.add(tankEntity);
    }

    @Override
    public void run()
    {
        System.out.println("run()");
        gameLoop();
    }

    @Override
    protected void gameLoop()
    {
//        System.out.println("gameLoop()");
        //        double lastRender = System.nanoTime();
        float currentTime = System.nanoTime();
        float accumulator = 0.0f;
        int i = 0;
        while (running)
        {
//            System.out.println("loop: " + i);
            float newTime = System.nanoTime();
            float frameTime = newTime - currentTime;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;	  //max frame
            }
            currentTime = newTime;

            accumulator += frameTime;

            while (accumulator >= DELTA)
            {
                //previousState = currentState
                updateGame(); //integrate (currentState ,t ,td)
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
    protected void updateGame()
    {
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).update(t, DELTA);
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
}
