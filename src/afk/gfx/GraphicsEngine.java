package afk.gfx;

import afk.ge.BBox;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.Renderable;
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
    
    public void prime();
    public GfxEntity getGfxEntity(Renderable renderable);
    public void post();
    
    public void primeDebug();
    public GfxEntity getDebugEntity(BBox bbox);
    public void postDebug();
}
