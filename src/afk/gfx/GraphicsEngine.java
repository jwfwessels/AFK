package afk.gfx;

import afk.gfx.athens.Athens;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Daniel
 */
public abstract class GraphicsEngine
{
    // TODO: maybe put this constant in a utils class later if there end up being lots of them
    public static final long NANOS_PER_SECOND = 1000000000l;
    
    protected Collection<GfxInputListener> listeners = new ArrayList<GfxInputListener>();
    protected Collection<Updatable> updatables = new ArrayList<Updatable>();
    
    
    /**
     * Constructs a graphics engine.
     * @return An appropriate implementation of the graphics engine.
     */
    public static GraphicsEngine createInstance(boolean autodraw)
    {
        // TODO: detect best opengl version
        return new Athens(autodraw);
    }
    
    public void addGfxEventListener(GfxInputListener listener)
    {
        listeners.add(listener);
    }
    
    public void removeGfxEventListener(GfxInputListener listener)
    {
        listeners.remove(listener);
    }

    public void addUpdatable(Updatable u)
    {
        updatables.add(u);
    }
    
    public void removeUpdatable(Updatable u)
    {
        updatables.remove(u);
    }
    
    public abstract Component getAWTComponent();
    
    public abstract void redisplay();
    
    public abstract void dispatchLoadQueue(Runnable callback);
    
    public abstract Resource loadResource(int type, String resource);
    
    public abstract void unloadResource(Resource resource);
    
    public abstract void unloadEverything();
    
    public abstract GfxEntity createEntity(int behaviour);
    
    public abstract void deleteEntity(GfxEntity entity);
    
    public abstract void attachResource(GfxEntity entity, Resource resource)
            throws ResourceNotLoadedException;
    
    public abstract boolean isKeyDown(int keyCode);
    
    public abstract boolean isMouseDown(int button);
    
    public abstract int getMouseX();
    
    public abstract int getMouseY();
    
}
