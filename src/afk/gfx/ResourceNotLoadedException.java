package afk.gfx;

/**
 *
 * @author daniel
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
     * 
     * @param resource 
     */
    public ResourceNotLoadedException(Resource resource)
    {
        super(resource.getName());
        
        this.resource = resource;
    }

    /**
     * 
     * @param resource
     * @param msg 
     */
    public ResourceNotLoadedException(Resource resource, String msg)
    {
        super(msg + " (" + resource.getName() + ")");
        
        this.resource = resource;
    }

    /**
     * 
     * @return 
     */
    public Resource getResource()
    {
        return resource;
    }
    
}
