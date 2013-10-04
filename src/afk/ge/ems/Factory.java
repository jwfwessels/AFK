package afk.ge.ems;

/**
 * Interface for creating entities for the entity management system.
 * 
 * @author Daniel
 */
public interface Factory<R extends FactoryRequest>
{
    /**
     * Creates an entity from the given factory request
     * @param request the factory request i.e. blueprints for creating the entity
     * @return the created entity
     * @throws FactoryException if there was any exception with creating the entity
     */
    public Entity create(R request) throws FactoryException;
}
