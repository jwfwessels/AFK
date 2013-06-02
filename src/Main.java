
import afk.gfx.*;
import afk.tokyo.Entity;
import afk.tokyo.GameEngine;
import afk.tokyo.Tokyo;

public class Main
{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 768;
    public static final String TITLE = "AFK Arena";
    public static final float TANK_ANGULAR_VELOCITY = 30.0f;
    public static final int TANK_VELOCITY = 5;
    private GraphicsEngine renderer;
    private GameEngine engine;

    public Main()
    {
        renderer = GraphicsEngine.getInstance(WIDTH, HEIGHT, TITLE, false);
        engine = new Tokyo(renderer);
    }

    public void start()
    {
        new Thread(engine).start();
    }

    public static void main(String[] args)
    {

        Main main = new Main();

        main.start();

    }
}
