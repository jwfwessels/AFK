/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
import afk.bot.london.London;
import afk.gfx.GfxUtils;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class Tokyo extends GameEngine
{

    EntityManager entityManager;
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
        System.out.println("MAX_FRAMETIME = " + MAX_FRAMETIME);
        System.out.println("DELTA = " + DELTA);

        this.gfxEngine = gfxEngine;

        entityManager = new EntityManager(botEngine, gfxEngine);
        System.out.println("gfx" + gfxEngine.getFPS());
    }

    @Override
    public void run()
    {
//        System.out.println("" + javax.swing.SwingUtilities.isEventDispatchThread());
        entityManager.loadResources();
        while (!entityManager.loaded.get())
        { /* spin */ }

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
