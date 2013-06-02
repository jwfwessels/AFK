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
    boolean running = false;
    double GAME_SPEED = 30;
    double t = 0.0;
    public static final double NANOS_PER_SECOND = 1000000000;
    final double dt = NANOS_PER_SECOND / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    double TARGET_FPS = 60;
    double MIN_FPS = 25;
    double MIN_FRAMETIME = NANOS_PER_SECOND / TARGET_FPS;
    double MAX_FRAMETIME = NANOS_PER_SECOND / MIN_FPS;

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
        gameLoop();
    }

    @Override
    protected void gameLoop()
    {

        //        double lastRender = System.nanoTime();
        double currentTime = System.nanoTime();
        double accumulator = 0.0;

        while (running)
        {
            double newTime = System.nanoTime();
            double frameTime = newTime - currentTime;
            if (frameTime > MAX_FRAMETIME)
            {
                frameTime = MAX_FRAMETIME;	  //max frame
            }
            currentTime = newTime;

            accumulator += frameTime;

            while (accumulator >= dt)
            {
                //previousState = currentState
                updateGame(); //integrate (currentState ,t ,td)
                t += dt;
                accumulator -= dt;
            }

            double alpha = accumulator / dt;

            //State state = currentState * alpha = previousState* (1.0-alpha);

            render();//render (state)



//            double interpolation = Math.min(1.0, (newTime - currentTime) / dt);
        }
    }

    @Override
    protected void updateGame()
    {
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).update(t, dt);
        }
    }

    @Override
    protected void render()
    {
        //there is no method available in GraphicsEngine 
        //that can be invoked to cause a display() sequence to run.
        //gfxEngine.update()
    }
}
