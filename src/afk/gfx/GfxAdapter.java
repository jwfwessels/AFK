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

/**
 * An abstract adapter class for receiving graphics events. The methods in this
 * class are empty. This class exists as convenience for creating listener objects.
 * <br/>
 * Extend this class to create a graphics event listener and override the
 * methods for the events of interest. (If you implement the GfxListener
 * interface, you have to define all of the methods in it. This abstract class
 * defines null methods for them all, so you can only have to define methods for
 * events you care about.)
 * <br/>
 * Create a listener object using the extended class and then register it with a
 * graphics engine using the engines's addGfxListener method. When a graphics
 * event occurs, the relevant method in the listener object is invoked, and the
 * information is passed to it.
 * @author Daniel
 */
public class GfxAdapter implements GfxListener
{

    @Override
    public void mouseClicked(int x, int y, int button)
    {}

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y)
    {}

    @Override
    public void mousePressed(int x, int y, int button)
    {}

    @Override
    public void mouseReleased(int x, int y, int button)
    {}

    @Override
    public void keyPressed(int keyCode)
    {}

    @Override
    public void keyReleased(int keyCode)
    {}

    @Override
    public void update(float delta)
    {}
    
}
