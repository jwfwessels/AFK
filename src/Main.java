
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
    private Resource tankMesh, tankShader;
    private Resource floorMesh, floorShader;
    private GfxEntity tankGfxEntity, floorGfxEntity;
    private Entity tankEntity;

    public Main()
    {
        renderer = GraphicsEngine.getInstance(WIDTH, HEIGHT, TITLE, false);
        engine = new Tokyo(renderer);
    }

    public void start()
    {
        tankMesh = renderer.loadResource(Resource.WAVEFRONT_MESH, "tank");
        tankShader = renderer.loadResource(Resource.SHADER, "monkey");

        floorMesh = renderer.loadResource(Resource.PRIMITIVE_MESH, "quad");
        floorShader = renderer.loadResource(Resource.SHADER, "floor");

        renderer.dispatchLoadQueue(new Runnable()
        {
            @Override
            public void run()
            {

                try
                {

                    floorGfxEntity = renderer.createEntity();
                    renderer.attachResource(floorGfxEntity, floorMesh);
                    renderer.attachResource(floorGfxEntity, floorShader);
                    floorGfxEntity.setScale(50, 50, 50);


                    tankGfxEntity = renderer.createEntity();
                    renderer.attachResource(tankGfxEntity, tankMesh);
                    renderer.attachResource(tankGfxEntity, tankShader);
                } catch (ResourceNotLoadedException ex)
                {
                    //System.err.println("Failed to load resource: " + ex.getMessage());
                    throw new RuntimeException(ex);
                }

                tankEntity = new Entity(tankGfxEntity);
                engine.addEntity(tankEntity);
                new Thread(engine).start();
            }
        });
    }

    public static void main(String[] args)
    {

        Main main = new Main();

        main.start();

    }
}
