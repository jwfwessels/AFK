package afk.ge.tokyo.ems;

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
    Collection<EntityListener> listeners = new ArrayList<EntityListener>();
    Map<Class, Object> components = new HashMap<Class, Object>();
    
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
    
    public void remove(Class componentClass)
    {
        components.remove(componentClass);
        
        for (EntityListener l : listeners)
            l.componentRemoved(this, componentClass);
    }
    
    public Object get(Class componentClass)
    {
        return components.get(componentClass);
    }
    
    public boolean has(Class componentClass)
    {
        return components.containsKey(componentClass);
    }
}
