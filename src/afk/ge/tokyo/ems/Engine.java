package afk.ge.tokyo.ems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daniel
 */
public class Engine implements EntityListener
{
    Collection<ISystem> systems = new ArrayList<ISystem>();
    Collection<Entity> entities = new ArrayList<Entity>();
    Collection<Entity> toAdd = new ArrayList<Entity>();
    Collection<Entity> toRemove = new ArrayList<Entity>();
    Map<Class, Collection<Object>> nodeLists = new HashMap<Class, Collection<Object>>();
    Map<Class, Family> families = new HashMap<Class, Family>();
    
    boolean updating = false;
    
    public void addEntity(Entity entity)
    {
        if (updating)
        {
            toAdd.add(entity);
            return;
        }
        entities.add(entity);
        entity.addEntityListener(this);
        for (Family family : families.values())
            family.newEntity(entity);
    }
    
    public void removeEntity(Entity entity)
    {
        if (updating)
        {
            toRemove.add(entity);
            return;
        }
        entity.removeEntityListener(this);
        
        for (Family family : families.values())
            family.removeEntity(entity);
        
        entities.remove(entity);
    }

    @Override
    public void componentAdded(Entity entity, Class componentClass)
    {
        for (Family family : families.values())
            family.componentAddedToEntity(entity, componentClass);
    }

    @Override
    public void componentRemoved(Entity entity, Class componentClass)
    {
        for (Family family : families.values())
            family.componentRemovedFromEntity(entity, componentClass);
    }
    
    public <N extends Node> List<N> getNodeList(Class<N> nodeClass)
    {
        Family family = families.get(nodeClass);
        if (family == null)
        {
            family = new Family(nodeClass);
            for (Entity entity : entities)
            {
                family.newEntity(entity);
            }
            families.put(nodeClass, family);
        }
        return family.getNodeList();
    }
    
    public void addSystem(ISystem system)
    {
        if (system.start(this))
        {
            systems.add(system);
        }
    }
    
    public void update(double delta)
    {
        updating = true;
        for (ISystem s : systems)
        {
            s.update(delta);
        }
        updating = false;
        
        for (Entity e : toAdd)
            addEntity(e);
        toAdd.clear();
        for (Entity e : toRemove)
            removeEntity(e);
        toRemove.clear();
    }
    
    public void removeSystem(ISystem system)
    {
        if (systems.remove(system))
            system.end();
    }
    
    public void shutDown()
    {
        while (updating) { /* spin */ }
        
        for (ISystem s : systems)
            s.end();
        
        systems.clear();
    }
}
