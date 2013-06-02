
import afk.gfx.*;
import afk.tokyo.Entity;
import afk.tokyo.GameEngine;
import afk.tokyo.Tokyo;
import static java.awt.event.KeyEvent.*;

public class Main implements GfxInputListener, Updatable
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
    private static boolean[] flags = new boolean[]
    {
        false, false, false, false
    };

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

//                TODO: this is just for now, there must be a more elegant solution but this might work for now...
                renderer.addGfxEventListener(Main.this);
//                renderer.addUpdatable(Main.this);


                tankEntity = new Entity(tankGfxEntity);
                engine.addEntity(tankEntity);
                new Thread(engine).start();
//                engine.run();
            }
        });
    }

    public static void main(String[] args)
    {

        Main main = new Main();

        main.start();

    }

    @Override
    public void update(float delta)
    {
        float angle = -(float) Math.toRadians(tankGfxEntity.yRot);
        float sin = (float) Math.sin(angle);
        float cos = (float) Math.cos(angle);
//        float tdelta = TANK_VELOCITY * delta;
        
        for (int i = 0; i < flags.length; i++)
        {
//            flags[i] = false;
        }
        
        if (renderer.isKeyDown(VK_UP))
        {
//            flags[0] = true;
//            System.out.println("flags[0]" + flags[0]);
//            tankGfxEntity.xMove += -(tdelta * sin);
//            tankGfxEntity.zMove += (tdelta * cos);
        } else if (renderer.isKeyDown(VK_DOWN))
        {
            flags[1] = true;
//            tankGfxEntity.xMove -= -(tdelta * sin);
//            tankGfxEntity.zMove -= (tdelta * cos);
        }
        if (renderer.isKeyDown(VK_LEFT))
        {
//            tankGfxEntity.yRot += tdelta * TANK_ANGULAR_VELOCITY;
        } else if (renderer.isKeyDown(VK_RIGHT))
        {
//            tankGfxEntity.yRot -= tdelta * TANK_ANGULAR_VELOCITY;
        }

    }

    @Override
    public void mouseClicked(int x, int y, int button)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mousePressed(int x, int y, int button)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mouseReleased(int x, int y, int button)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(int keyCode)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(int keyCode)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
