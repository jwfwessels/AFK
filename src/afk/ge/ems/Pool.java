package afk.ge.ems;

/**
 * Specifies the interface to a Pool of entities.
 * 
 * @author Daniel
 */
public interface Pool
{
    /**
     * Get an entity from this pool. If the pool is empty, this method should
     * allocate a new object.
     * @return a new entity from the pool.
     */
    public Entity getEntity();
    /**
     * Put an entity back into the pool for reuse.
     * @param entity the entity to return to the pool.
     */
    public void returnEntity(Entity entity);
}
