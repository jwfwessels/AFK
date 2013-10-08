package afk.ge.ems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author daniel
 */
public class Entity
{
    // keeps track of how many entities there are, for debug purposes
    private static int count = 0;
    public static final String DEFAULT_NAME = "entity";
    
    private String name; // for debug purposes
    
    private Collection<EntityListener> listeners = new ArrayList<EntityListener>();
    protected Collection<Entity> dependents = new ArrayList<Entity>();
    private Map<Class, Object> components = new HashMap<Class, Object>();
    
    private Pool source;

    public Entity()
    {
        this(DEFAULT_NAME, null);
    }

    public Entity(Pool source)
    {
        this(DEFAULT_NAME, source);
    }
    
    public Entity(String name)
    {
        this(name, null);
    }
    
    public Entity(String name, Pool source)
    {
        this.source = source;
        this.name = name+"_"+(count++);
    }
    
    protected void returnToSource()
    {
        if (source != null)
        {
            source.returnEntity(this);
        }
    }
    
    public void addEntityListener(EntityListener l)
    {
        listeners.add(l);
    }
    
    public void removeEntityListener(EntityListener l)
    {
        listeners.remove(l);
    }
    
    public void add(Object component)
    {
        Class componentClass = component.getClass();
        components.put(componentClass, component);
        
        for (EntityListener l : listeners)
            l.componentAdded(this, componentClass);
    }
    
    public void addToDependents(Object component)
    {
        add(component);
        for (Entity dep : dependents)
        {
            dep.addToDependents(component);
        }
    }
    
    public void remove(Class componentClass)
    {
        components.remove(componentClass);
        
        for (EntityListener l : listeners)
            l.componentRemoved(this, componentClass);
    }
    
    public void removeFromDependents(Class componentClass)
    {
        remove(componentClass);
        for (Entity dep : dependents)
        {
            dep.removeFromDependents(componentClass);
        }
    }
    
    public void addDependent(Entity entity)
    {
        dependents.add(entity);
    }
    
    public Collection<Entity> getDependents()
    {
        return dependents;
    }
    
    public void removeDependent(Entity entity)
    {
        dependents.remove(entity);
    }
    
    public <T> T get(Class<T> componentClass)
    {
        return (T)components.get(componentClass);
    }
    
    public boolean has(Class componentClass)
    {
        return components.containsKey(componentClass);
    }
    
    public Object[] getAll()
    {
        return components.values().toArray();
    }

    @Override
    public String toString()
    {
        return name;
    }
}
