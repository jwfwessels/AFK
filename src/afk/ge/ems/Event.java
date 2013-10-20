package afk.ge.ems;

/**
 *
 * @author daniel
 */
public abstract class Event
{
    private Entity entity;

    public final Entity getEntity()
    {
        return entity;
    }
    
    protected final void setEntity(Entity entity)
    {
        this.entity = entity;
    }
}
