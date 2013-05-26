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
    double TIME_BETWEEN_UPDATES = 1000000000 / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    double NUM_RENDERS = 60;
    double TIME_BETWEEN_RENDERS = 1000000000 / NUM_RENDERS;

    @Override
    protected void loadResources()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void gameLoop()
    {
        double lastUpdate = System.nanoTime();
        double lastRender = System.nanoTime();
        while (running) {
            double currTime = System.nanoTime();
            if (currTime-lastUpdate > TIME_BETWEEN_UPDATES) {
                updateGame();
                lastUpdate = currTime;
            }
            double interpolation = Math.min(1.0, (currTime - lastUpdate)/TIME_BETWEEN_UPDATES);
            
            
        }
    }

    @Override
    protected void updateGame()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void render(float interp)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
