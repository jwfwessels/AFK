
import afk.frontend.swing.RootWindow;
import afk.gfx.*;
import afk.ge.GameEngine;
import afk.ge.tokyo.Tokyo;

public class Main
{

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "AFK Arena";
    public static final float TANK_ANGULAR_VELOCITY = 30.0f;
    public static final int TANK_VELOCITY = 5;
    private GraphicsEngine renderer;
    private GameEngine engine;

    public Main()
    {
//        renderer = GraphicsEngine.createInstance(false);
//        engine = new Tokyo(renderer);
        
        
        RootWindow window = new RootWindow();
        window.start();
    }

//    public void start()
//    {
//        new Thread(engine).start();
//    }

    public static void main(String[] args)
    {

        Main main = new Main();

//        main.start();
    }
}
