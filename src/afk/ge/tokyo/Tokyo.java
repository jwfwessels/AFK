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
    public static final float NANOS_PER_SECOND = 1.0f;
    final static float DELTA = NANOS_PER_SECOND / GAME_SPEED;
    //get NUM_RENDERS from GraphicsEngine average fps..?, currently hard coded
    final static double TARGET_FPS = 90;
    final static double MIN_FPS = 25;
    final static double MIN_FRAMETIME = NANOS_PER_SECOND / TARGET_FPS;
    final static double MAX_FRAMETIME = NANOS_PER_SECOND / MIN_FPS;

    public Tokyo(GraphicsEngine gfxEngine, DefaultListModel<String> lsSelectedModel, HashMap<String, String> botMap)
    {
        this.gfxEngine = gfxEngine;
        this.botEngine = new London();

        entityManager = new EntityManager(botEngine, gfxEngine);
        this.lsSelectedModel = lsSelectedModel;
        this.botMap = botMap;
//        RootWindow rootWindow = new RootWindow();
//        rootWindow.start();
//        constructGUI();

        //uncomment if your doing testing and dont need the gui. use TestMove() to set parameters
        /*System.out.println("Testing Enabled, GUI disabled. line 69 Tokyo");
         TestMove();*/
    }

    @Override
    public void run()
    {
        System.out.println("test0 " + javax.swing.SwingUtilities.isEventDispatchThread());
        entityManager.loadResources();
        System.out.println("test1 " + javax.swing.SwingUtilities.isEventDispatchThread());
        while (!entityManager.loaded.get())
        { /* spin */ }

        gameLoop();
    }

    @Override
    protected void gameLoop()
    {
        System.out.println("loop " + javax.swing.SwingUtilities.isEventDispatchThread());
        while (!gameInProgress.get())
        { /* spin! */

        }
        System.out.println("running " + javax.swing.SwingUtilities.isEventDispatchThread());
        loadBots();
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
        ArrayList<String> bots = getParticipatingBots();
        for (int i = 0; i < bots.size(); i++)
        {
            String path = bots.get(i);
            Robot loadedBot = botEngine.loadBot(path);
            botEngine.registerBot(loadedBot);
            entityManager.createTank(SPAWN_POINTS[i], BOT_COLOURS[i]);
        }
        System.out.println("Botsloaded");
    }

    @Override
    public void startGame()
    {
        System.out.println("startGame! " + javax.swing.SwingUtilities.isEventDispatchThread());
        gameInProgress.set(true);

    }
    private AtomicBoolean gameInProgress = new AtomicBoolean(false);
    private static final Vec3[] BOT_COLOURS =
    {
        new Vec3(1, 0, 0),
        new Vec3(0, 0, 1),
        new Vec3(0, 1, 0),
        new Vec3(1, 1, 0),
        new Vec3(1, 0, 1),
        new Vec3(1, 0, 1),
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
        new Vec3(-20, 0, 0)
    };
    public static final float BOARD_SIZE = 50;
//    private JFrame jFrame;
//    private JTabbedPane jTPane;
    private HashMap<String, String> botMap = new HashMap<String, String>();
//    private JPanel pnlBotSelection = new JPanel();
//    private JPanel pnlArena = new JPanel();
//    private JFileChooser fileChooser = new JFileChooser(".");
//    private JList<String> lstAvailableBots = new JList();
//    private JList<String> lstSelectedBots = new JList();
//    private DefaultListModel<String> lsAvailableModel = new DefaultListModel();
    private DefaultListModel<String> lsSelectedModel = new DefaultListModel();


    public ArrayList<String> getParticipatingBots()
    {
        ArrayList<String> bots = new ArrayList<String>();
        for (int x = 0; x < lsSelectedModel.size(); x++)
        {
            bots.add(botMap.get(lsSelectedModel.getElementAt(x)));
        }
        return bots;
    }

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
