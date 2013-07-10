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
