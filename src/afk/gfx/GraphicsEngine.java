package afk.gfx;

import afk.ge.tokyo.ems.components.ImageComponent;
import afk.ge.BBox;
import afk.ge.tokyo.ems.components.Renderable;
import com.hackoeur.jglm.Vec3;
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
    
    public static final int NUM_KEYS = 1024;
    
    /**
     * Reads the status of a key on the keyboard.
     * @param keyCode the key to read.
     * @return true if the key is currently pressed, false otherwise.
     */
    public boolean isKeyDown(int keyCode);
    
    public static final int NUM_MOUSE_BUTTONS = 20;
    
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
     * Gets the graphics engine's viewport width.
     * @return the width of the engine's viewport;
     */
    public int getWidth();
    
    /**
     * Gets the graphics engine's viewport height.
     * @return the height of the engine's viewport;
     */
    public int getHeight();
    
    
    /**
     * register swing component for frame rate updates.
     * @param comp the component to register for Updates.
     */
    public void setFPSComponent(Component comp);
    
    public void prime();
    public GfxEntity getGfxEntity(Renderable renderable);
    public GfxHUD getGfxHUD(ImageComponent image);
    public void post();

    /**
     * Gets the graphics engine's current camera object.
     * @return the current camera object.
     */
    public AbstractCamera getCamera();
    
    /**
     * Sets the graphics engine's current camera object;
     * @param camera the camera object.
     */
    public void setCamera(AbstractCamera camera);
    
    public GfxEntity getDebugEntity(BBox bbox);
    public GfxEntity getDebugEntity(Vec3[] line);
}
