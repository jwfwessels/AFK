package afk.gfx;

import java.awt.Component;

/**
 * Abstract interface to a Graphics Engine.
 * @author Daniel
 */
public interface GraphicsEngine
{
    
    /**
     * Adds the specified graphics event listener to receive events from this
     * graphics engine.
     * @param listener the graphics event listener. 
     */
    public void addGfxEventListener(GfxListener listener);
    
    /**
     * Removes the specified graphics event listener so that it no longer
     * receives graphics events from this graphics engine.
     * @param listener 
     */
    public void removeGfxEventListener(GfxListener listener);
    
    /**
     * Get the AWT component (if any) that contains the visuals of this graphics
     * engine. If the underlying implementation does not use AWT then null
     * should be returned.
     * @return The AWT Component of this graphic engine. null if no such
     * component exists.
     */
    public Component getAWTComponent();
    
    /**
     * Force the graphics engine to redraw the scene. If autodraw is false then
     * this is the only way to cause a display update.
     */
    public void redisplay();
    
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
    public void dispatchLoadQueue(Runnable callback);
    
    /**
     * Adds the specified resource to the load queue.
     * @param type the type of resource to load (see afk.gfx.Resource for a list
     * of types).
     * @param resource The name of the resource to load.
     * @return a Resource object representing the resource to be loaded.
     */
    public Resource loadResource(int type, String resource);
    
    /**
     * Adds the specified resource to the unload queue.
     * @param resource the Resource object to unload.
     */
    public void unloadResource(Resource resource);
    
    /**
     * Adds all loaded resources into the unload queue. This method also clears
     * the load queue. A subsequent call to dispatchLoadQueue will thereby
     * result in there being no loaded resources.
     */
    public void unloadEverything();
    
    /**
     * Creates a GfxEntity.
     * @param behaviour The behaviour of the entity. See afx.gfx.GfxEntity for a
     * list of available behaviours.
     * @return The created GfxEntity.
     */
    public GfxEntity createEntity(int behaviour);
    
    /**
     * Gets the scene's root entity.
     * @return the scene's root entity.
     */
    public GfxEntity getRootEntity();
    
    /**
     * Reads the status of a key on the keyboard.
     * @param keyCode the key to read.
     * @return true if the key is currently pressed, false otherwise.
     */
    public boolean isKeyDown(int keyCode);
    
    /**
     * Reads the status of a button on the mouse.
     * @param button the button to read.
     * @return true if the button is currently pressed, false otherwise.
     */
    public boolean isMouseDown(int button);
    
    /**
     * Read the X position of the mouse cursor relative to the graphics engine's
     * display area.
     * @return the X component of the mouse cursor position.
     */
    public int getMouseX();
    
    /**
     * Read the Y position of the mouse cursor relative to the graphics engine's
     * display area.
     * @return the Y component of the mouse cursor position.
     */
    public int getMouseY();
    
    /**
     * Reads the current frame rate.
     * @return the current frame rate.
     */
    public float getFPS();
    
    
        /**
     * register swing component for frame rate updates.
     * @param comp the component to register for Updates.
     */
    public void setFPSComponent(Component comp);
    
}
