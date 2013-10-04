package afk.ge.ems;

/**
 *
 * @author Daniel
 */
public class FactoryException extends Exception
{

    /**
     * Creates a new instance of
     * <code>FactoryException</code> without detail message.
     */
    public FactoryException()
    {
    }

    /**
     * Constructs an instance of
     * <code>FactoryException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public FactoryException(String msg)
    {
        super(msg);
    }

    /**
     * Constructs an instance of
     * <code>FactoryException</code> with the specified cause.
     * 
     * @param cause the cause.
     */
    public FactoryException(Throwable cause)
    {
        super(cause);
    }
    
}
