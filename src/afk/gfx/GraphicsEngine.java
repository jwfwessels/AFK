package afk.gfx;

import afk.gfx.athens.Athens;

/**
 *
 * @author Daniel
 */
public abstract  class GraphicsEngine
{
    private static GraphicsEngine instance;
    
    /**
     * Get the singleton instance of the graphics engine.
     * @return The graphics engine.
     */
    public static GraphicsEngine getInstance()
    {
        // TODO: check this code with 226 textbook!
        synchronized (GraphicsEngine.class)
        {
            if (instance == null)
            {
                synchronized (GraphicsEngine.class)
                {
                    // TODO: detect best opengl version
                    instance = new Athens();
                }
            }
        }
        return instance;
    }
    
    public abstract void loadResource(int type, String resource);
    
    public abstract void unloadResource(int type, String resource);
    
    public abstract void unloadEverything();
    
    public abstract GfxEntity createEntity(int type, String resource);
}
