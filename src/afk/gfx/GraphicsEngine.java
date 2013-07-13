package afk.gfx;

import afk.gfx.athens.Athens;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Abstract interface to a Graphics Engine.
 * @author Daniel
 */
public abstract class GraphicsEngine
{
    // TODO: maybe put this constant in a utils class later if there end up being lots of them
    public static final long NANOS_PER_SECOND = 1000000000l;
    
    protected Collection<GfxListener> listeners = new ArrayList<GfxListener>();
    
    /**
     * Constructs an appropriate implementation of GraphicsEngine based on the
     * underlying system's capabilities. The method returns null if there are
     * no useful implementations of GraphicsEngine.
     * @return an instance of GraphicsEngine, null if this is not possible.
     */
    public static GraphicsEngine createInstance(boolean autodraw)
    {
        // TODO: detect best OpenGL/direct3D version
        return new Athens(autodraw);
    }
    
    /**
     * Adds the specified graphics event listener to receive events from this
     * graphics engine.
     * @param listener the graphics event listener. 
     */
    public void addGfxEventListener(GfxListener listener)
    {
        listeners.add(listener);
    }
    
    /**
     * Removes the specified graphics event listener so that it no longer
     * receives graphics events from this graphics engine.
     * @param listener 
     */
    public void removeGfxEventListener(GfxListener listener)
    {
        listeners.remove(listener);
    }
    
    /**
     * Get the AWT component (if any) that contains the visuals of this graphics
     * engine. If the underlying implementation does not use AWT then null
     * should be returned.
     * @return The AWT Component of this graphic engine. null if no such
     * component exists.
     */
    public abstract Component getAWTComponent();
    
    /**
     * Force the graphics engine to redraw the scene. If autodraw is false then
     * this is the only way to cause a display update.
     */
    public abstract void redisplay();
    
    /**
     * Causes all resources in the load queue to be loaded into memory, and all
     * resources in the unload queue to be unloaded from memory. The actual
     * loading and unloading occurs in the next update cycle, so a call to
     * redisplay may be necessary. Once all resources have been loaded/unloaded
     * the callback is executed.
     * <br/>
     * <em>Note:</em> The callback is executed in the graphics engine's update
     * thread, so if you wish to do any sort of fancy stuff please make a new
     * thread. Thank you.
     * @param callback the callback to be executed once the loading/unloading
     * is complete.
     */
    public abstract void dispatchLoadQueue(Runnable callback);
    
    /**
     * Adds the specified resource to the load queue.
     * @param type the type of resource to load (see afk.gfx.Resource for a list
     * of types).
     * @param resource The name of the resource to load.
     * @return a Resource object representing the resource to be loaded.
     */
    public abstract Resource loadResource(int type, String resource);
    
    /**
     * Adds the specified resource to the unload queue.
     * @param resource the Resource object to unload.
     */
    public abstract void unloadResource(Resource resource);
    
    /**
     * Adds all loaded resources into the unload queue. This method also clears
     * the load queue. A subsequent call to dispatchLoadQueue will thereby
     * result in there being no loaded resources.
     */
    public abstract void unloadEverything();
    
    /**
     * Creates a GfxEntity.
     * @param behaviour The behaviour of the entity. See afx.gfx.GfxEntity for a
     * list of available behaviours.
     * @return The created GfxEntity.
     */
    public abstract GfxEntity createEntity(int behaviour);
    
    /**
     * Gets the scene's root entity.
     * @return the scene's root entity.
     */
    public abstract GfxEntity getRootEntity();
    
    /**
     * Reads the status of a key on the keyboard.
     * @param keyCode the key to read.
     * @return true if the key is currently pressed, false otherwise.
     */
    public abstract boolean isKeyDown(int keyCode);
    
    /**
     * Reads the status of a button on the mouse.
     * @param button the button to read.
     * @return true if the button is currently pressed, false otherwise.
     */
    public abstract boolean isMouseDown(int button);
    
    /**
     * Read the X position of the mouse cursor relative to the graphics engine's
     * display area.
     * @return the X component of the mouse cursor position.
     */
    public abstract int getMouseX();
    
    /**
     * Read the Y position of the mouse cursor relative to the graphics engine's
     * display area.
     * @return the Y component of the mouse cursor position.
     */
    public abstract int getMouseY();
    
}
