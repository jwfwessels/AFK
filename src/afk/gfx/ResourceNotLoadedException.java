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
 * This exception is typically throws when a graphical resource is used before
 * it has been loaded. This can occur if the resource has been added to the load
 * queue but the load queue has not yet been dispatched, or if the resource was
 * loaded but has subsequently been unloaded since then.
 * @author Daniel
 */
public class ResourceNotLoadedException extends Exception
{

    private Resource resource;
    
    /**
     * Creates a new instance of
     * <code>ResourceNotLoadedException</code> without detail message.
     */
    public ResourceNotLoadedException()
    {
    }

    /**
     * Constructs an instance of
     * <code>ResourceNotLoadedException</code> with the specified detail
     * message.
     *
     * @param msg the detail message.
     */
    public ResourceNotLoadedException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>ResourceNotLoadedException</code> with the specified
     * associated resource.
     * @param resource 
     */
    public ResourceNotLoadedException(Resource resource)
    {
        super(resource.getName());
        
        this.resource = resource;
    }

    /**
     * Constructs an instance of
     * <code>ResourceNotLoadedException</code> with the specified detail
     * message and associated resource.
     * @param resource
     * @param msg 
     */
    public ResourceNotLoadedException(Resource resource, String msg)
    {
        super(msg + " (" + resource.getName() + ")");
        
        this.resource = resource;
    }

    /**
     * Gets the resource that this exception refers to.
     * @return the resource.
     */
    public Resource getResource()
    {
        return resource;
    }
    
}
