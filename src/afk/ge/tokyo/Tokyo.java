/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
import afk.london.London;
import afk.london.Robot;
import afk.london.SampleBot;
import afk.london.SampleBot2;
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
    public void run()
    {
        if (entityManager.loadResources())
        {
            loaded.set(true);
        }

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
        Robot bot2 = new SampleBot2();
        London.registerBot(bot2);
    }
}
