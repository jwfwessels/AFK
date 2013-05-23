import afk.gfx.*;
import com.hackoeur.jglm.Vec3;

public class Main {
    public static final int WIDTH = 1280;
    public static final int HEIGHT = 768;
    public static final String TITLE = "AFK Arena";
   
    public static void main( String [] args ) {
        
        GraphicsEngine engine = GraphicsEngine.getInstance(WIDTH, HEIGHT, TITLE);
        
        // NOTE: The following doesn't have any effect yet.
        // all of the functionality is still under the "influence" of Athens itself
        // decoupling and generalisation and all that stuff is still in progress.
        
        // Thus the following code is merely conceptual
        Resource tankMesh = engine.loadResource(Resource.WAVEFRONT_MESH, "tank");
        Resource tankShader = engine.loadResource(Resource.SHADER, "monkey");
        
        Resource floorMesh = engine.loadResource(Resource.PRIMITIVE_MESH, "quad");
        Resource floorShader = engine.loadResource(Resource.SHADER, "floor");
        
        GfxEntity tankEntity = engine.createEntity();
        engine.attachResource(tankEntity, tankMesh);
        engine.attachResource(tankEntity, tankShader);
        
        GfxEntity floorEntity = engine.createEntity();
        engine.attachResource(floorEntity, floorMesh);
        engine.attachResource(floorEntity, floorShader);
        
        floorEntity.scale = new Vec3(50,50,50);
        
        engine.enterDrawingState();
        
        // TODO: should create an event listener class for handling generic sort of events and such
        // then we can handle that tank movement in this class
        
    }
    
}
