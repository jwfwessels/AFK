/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.GameEngine;
import afk.gfx.GraphicsEngine;
import afk.london.London;
import afk.london.Robot;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.DefaultListModel;

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
    final static float DELTA = 1.0f / GAME_SPEED;
    final static double NANOS_PER_SECOND = (double) GraphicsEngine.NANOS_PER_SECOND;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    final static double TARGET_FPS = 60;
    final static double MIN_FPS = 25;
    final static double MIN_FRAMETIME = 1.0f / TARGET_FPS;
    final static double MAX_FRAMETIME = 1.0f / MIN_FPS;
    public static final float BOARD_SIZE = 50;
    private HashMap<String, String> botMap;
    private DefaultListModel<String> lsSelectedModel;

    public Tokyo(GraphicsEngine gfxEngine, DefaultListModel<String> lsSelectedModel, HashMap<String, String> botMap)
    {
        System.out.println("MAX_FRAMETIME = " + MAX_FRAMETIME);
        System.out.println("DELTA = " + DELTA);

        this.gfxEngine = gfxEngine;
        this.botEngine = new London();

        entityManager = new EntityManager(botEngine, gfxEngine);
        System.out.println("gfx" + gfxEngine.getFPS());
        this.lsSelectedModel = lsSelectedModel;
        this.botMap = botMap;

        //this doesnt work with the new GUI refactor
        //uncomment if your doing testing and dont need the gui. use TestMove() to set parameters
        /*System.out.println("Testing Enabled, GUI disabled. line 69 Tokyo");
         TestMove();*/
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
        ArrayList<String> bots = getParticipatingBots();
        for (int i = 0; i < bots.size(); i++)
        {
            String path = bots.get(i);
            Robot loadedBot = botEngine.loadBot(path);
            if (loadedBot == null)
            {
//                JOptionPane.showMessageDialog(jFrame, botEngine.getBotLoadingError(), "Error Loading Bots", JOptionPane.ERROR_MESSAGE);        
                System.out.println(botEngine.getBotLoadingError() + "Error Loading Bots");
                return false;
            }
            botEngine.registerBot(loadedBot);
            entityManager.createTank(SPAWN_POINTS[i], BOT_COLOURS[i]);
        }
        System.out.println("Botsloaded");
        return true;
    }

    public ArrayList<String> getParticipatingBots()
    {
        ArrayList<String> bots = new ArrayList<String>();
        for (int x = 0; x < lsSelectedModel.size(); x++)
        {
            bots.add(botMap.get(lsSelectedModel.getElementAt(x)));
        }
        return bots;
    }
    private AtomicBoolean gameInProgress = new AtomicBoolean(false);
    private static final Vec3[] BOT_COLOURS =
    {
        new Vec3(1, 0, 0),
        new Vec3(0, 0, 1),
        new Vec3(0, 1, 0),
        new Vec3(1, 1, 0),
        new Vec3(1, 0, 1),
        new Vec3(0, 1, 1),
        new Vec3(0.6f, 0.6f, 0.6f),
    };
    private static Vec3[] SPAWN_POINTS =
    {
        new Vec3(-20, 0, -20),
        new Vec3(20, 0, 20),
        new Vec3(-20, 0, 20),
        new Vec3(20, 0, -20),
        new Vec3(-20, 0, 0),
        new Vec3(0, 0, -20),
        new Vec3(20, 0, 0)
    };
//    private void TestMove()
//    {
////        String botPath1 = "./build/classes/SampleBot.class";
//        String botPath1 = "./build/classes/RandomBot.class";
//        String botName1 = "SampleBot";
////        String botPath2 = "./build/classes/SampleBot2.class";
//        String botPath2 = "./build/classes/CircleBot.class";
//        String botName2 = "SampleBot2";
//        lsSelectedModel.addElement(botName1);
//        lsSelectedModel.addElement(botName2);
//        botMap.put(botName1, botPath1);
//        botMap.put(botName2, botPath2);
//        SPAWN_POINTS = new Vec3[]
//        {
//            new Vec3(-20, 0, -20),
//            new Vec3(20, 0, 20),
//            new Vec3(-3, 0, 10),
//            new Vec3(20, 0, -20),
//            new Vec3(-20, 0, 0),
//            new Vec3(0, 0, -20),
//            new Vec3(-20, 0, 0)
//        };
//        startGame();
////        jTPane.setSelectedComponent(pnlArena);
//    }
}
