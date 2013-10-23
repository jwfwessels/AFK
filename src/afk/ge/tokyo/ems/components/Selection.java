package afk.ge.tokyo.ems.components;

import afk.ge.ems.Entity;

/**
 *
 * @author daniel
 */
public class Selection
{
    private Entity entity = null;

    public Selection()
    {
    }

    public Selection(Entity entity)
    {
        this.entity = entity;
    }

    public Entity getEntity()
    {
        return entity;
    }
    
}
