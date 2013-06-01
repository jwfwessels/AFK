/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import java.util.ArrayList;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    ArrayList entities;
    boolean running = false;
    double GAME_SPEED = 30;
    final double dt = 1000000000 / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    double TARGET_FPS = 60;
    double MIN_FPS = 15;
    double MIN_FRAMETIME = 1000000000 / TARGET_FPS;
    double MAX_FRAMETIME = 1000000000 / MIN_FPS;

    @Override
    protected void loadResources()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void gameLoop()
    {
        double t = 0.0;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void render()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
