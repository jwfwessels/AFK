package afk.ge.tokyo.ems;

/**
 *
 * @author daniel
 */
public interface EntityListener
{
    public void componentAdded(Entity entity, Class componentClass);
    public void componentRemoved(Entity entity, Class componentClass);
}
