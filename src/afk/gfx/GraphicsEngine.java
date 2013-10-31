/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package afk.gfx;

import afk.ge.BBox;
import afk.ge.tokyo.ems.components.HUDImage;
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
     * Indicates if the graphics engine is ready yet.
     * @return true if the graphics engine is initialised, false otherwise
     */
    public boolean isReady();
    
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
     * Prime the graphics engine for drawing objects to the scene.
     * This should be called before getting GfxEntity objects.
     */
    public void prime();
    
    /**
     * Gets a GfxEntity object corresponding to the given renderable object.
     * The object returned can be used to update the attributes of the
     * underlying graphics entity before being drawn to the screen.
     * This function will flag the GfxEntity to be drawn in the next frame.
     * @param renderable the renderable
     * @return the GfxEntity object corresponding to the given renderable.
     */
    public GfxEntity getGfxEntity(Renderable renderable);
    
    /**
     * Similar to getGfxEntity but returns a GfxHUD object.
     * @param image the image component corresponding to the returned GfxHUD.
     * @return the GfxHUD object corresponding to the given image.
     */
    public GfxHUD getGfxHUD(HUDImage image);
    
    /**
     * Should be called after updating all the necessary GfxEntity objects.
     * Causes any GfxEntity objects not requested to be drawn to be cleaned up
     * in the next cycle.
     */
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
    
    /**
     * Similar to getGfxEnity but for drawing BBox wireframes.
     * @param bbox
     * @return 
     */
    public GfxEntity getDebugEntity(BBox bbox);
    
    /**
     * Similar to getGfxEntity but for drawing arbitrary lines.
     * @param line
     * @return 
     */
    public GfxEntity getDebugEntity(Vec3[] line);
    
    /**
     * Set the background colour.
     * @param colour a vector representing the red, green, and blue values
     * to be used as the background colour.
     */
    public void setBackground(Vec3 colour);
}
