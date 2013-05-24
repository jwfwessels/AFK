import afk.gfx.*;
import static java.awt.event.KeyEvent.*;

public class Main implements GfxInputListener, Updatable
{
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 768;
    public static final String TITLE = "AFK Arena";
    public static final float TANK_ANGULAR_VELOCITY = 30.0f;
    public static final int TANK_VELOCITY = 5;
    
    private GraphicsEngine engine;
    
    private Resource tankMesh, tankShader;

    private Resource floorMesh, floorShader;
    
    private GfxEntity tankEntity, floorEntity;
    
    public Main()
    {
        engine = GraphicsEngine.getInstance(WIDTH, HEIGHT, TITLE);
    }
    
    public void start()
    {
        tankMesh = engine.loadResource(Resource.WAVEFRONT_MESH, "tank");
        tankShader = engine.loadResource(Resource.SHADER, "monkey");
        
        floorMesh = engine.loadResource(Resource.PRIMITIVE_MESH, "quad");
        floorShader = engine.loadResource(Resource.SHADER, "floor");
        
        engine.dispatchLoadQueue(new Runnable() {

            @Override
            public void run()
            {
                
                try
                {
                    floorEntity = engine.createEntity();
                    engine.attachResource(floorEntity, floorMesh);
                    engine.attachResource(floorEntity, floorShader);
                    floorEntity.setScale(50,50,50);

                    tankEntity = engine.createEntity();
                    engine.attachResource(tankEntity, tankMesh);
                    engine.attachResource(tankEntity, tankShader);
                }
                catch (ResourceNotLoadedException ex)
                {
                    //System.err.println("Failed to load resource: " + ex.getMessage());
                    throw new RuntimeException(ex);
                }
                
                // TODO: this is just for now, there must be a more elegant solution but this might work for now...
                engine.addGfxEventListener(Main.this);
                engine.addUpdatable(Main.this);
            }
            
        });
    }
   
    public static void main( String [] args )
    {
        
        Main main = new Main();
        
        main.start();
        
    }
    
    @Override
    public void update(float delta)
    {
        float angle = -(float)Math.toRadians(tankEntity.yRot);
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        float tdelta = TANK_VELOCITY*delta;

        if (engine.isKeyDown(VK_UP))
        {
            tankEntity.xMove += -(tdelta*sin);
            tankEntity.zMove += (tdelta*cos);
        }
        else if (engine.isKeyDown(VK_DOWN))
        {
            tankEntity.xMove -= -(tdelta*sin);
            tankEntity.zMove -= (tdelta*cos);
        }
        if (engine.isKeyDown(VK_LEFT))
        {
            tankEntity.yRot += tdelta*TANK_ANGULAR_VELOCITY;
        }
        else if (engine.isKeyDown(VK_RIGHT))
        {
            tankEntity.yRot -= tdelta*TANK_ANGULAR_VELOCITY;
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
