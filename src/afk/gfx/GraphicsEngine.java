package afk.gfx;

import afk.gfx.athens.Athens;

/**
 *
 * @author Daniel
 */
public abstract class GraphicsEngine
{
    private static GraphicsEngine instance;
    
    /**
     * Get the singleton instance of the graphics engine.
     * @return The graphics engine.
     */
    public static GraphicsEngine getInstance(int width, int height, String title)
    {
        // TODO: check this code with 226 textbook!
        synchronized (GraphicsEngine.class)
        {
            if (instance == null)
            {
                synchronized (GraphicsEngine.class)
                {
                    // TODO: detect best opengl version
                    instance = new Athens(width, height, title);
                }
            }
        }
        return instance;
    }
    
    // TODO: maybe make more generic to allow more flexibility in states
    public abstract void enterLoadingState();
    public abstract void enterDrawingState();
    
    public abstract Resource loadResource(int type, String resource);
    
    public abstract void unloadResource(Resource resource);
    
    public abstract void unloadEverything();
    
    public abstract GfxEntity createEntity();
    
    public abstract void deleteEntity(GfxEntity entity);
    
    public abstract void attachResource(GfxEntity entity, Resource resource);
}
