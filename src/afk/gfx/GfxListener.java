/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
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

/**
 * The listener interface for receiving graphics events (input events, updates
 * etc).
 * The class that is interested in processing a graphics event either implements
 * this interface (and all the methods it contains) or extends the abstract
 * GfxEventAdapter class (overriding only the methods of interest).
 * <br/>
 * The listener object created from that class is then registered with a
 * graphics engine using the engine's addGfxListener method. When a graphics
 * event occurs, the relevant method in the listener object is then invoked, and
 * the information is passed to it.
 * @author Daniel
 */
public interface GfxListener
{
    public void mouseClicked(int x, int y, int button);
    
    public void mouseMoved(int oldx, int oldy, int x, int y);
    
    public void mousePressed(int x, int y, int button);
    
    public void mouseReleased(int x, int y, int button);
    
    public void keyPressed(int keyCode);
    
    public void keyReleased(int keyCode);
    
    /**
     * Called whenever a graphics update occurs. This is useful if the graphics
     * engine is set to autodraw and you want to use the graphics engine's
     * update loop to do your own updates.
     * @param delta The number of seconds since the last update.
     */
    public void update(float delta);
    
}
