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
    private Map<Class, Collection<Object>> events = new HashMap<Class, Collection<Object>>();
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
        this.name = name + "_" + (count++);
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

    public void addComponent(Object component)
    {
        Class componentClass = component.getClass();
        components.put(componentClass, component);

        for (EntityListener l : listeners)
        {
            l.componentAdded(this, componentClass);
        }
    }

    public void deepAddComponent(Object component)
    {
        addComponent(component);
        for (Entity dep : dependents)
        {
            dep.deepAddComponent(component);
        }
    }

    public void removeComponent(Class componentClass)
    {
        components.remove(componentClass);

        for (EntityListener l : listeners)
        {
            l.componentRemoved(this, componentClass);
        }
    }

    public void deepRemoveComponent(Class componentClass)
    {
        removeComponent(componentClass);
        for (Entity dep : dependents)
        {
            dep.deepRemoveComponent(componentClass);
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

    public <T> T getComponent(Class<T> componentClass)
    {
        return (T) components.get(componentClass);
    }

    public boolean hasComponent(Class componentClass)
    {
        return components.containsKey(componentClass);
    }

    public Object[] getAllComponents()
    {
        return components.values().toArray();
    }

    public void addEvent(Object event)
    {
        Class eventClass = event.getClass();
        Collection<Object> eventList = events.get(eventClass);
        if (eventList == null)
        {
            eventList = new ArrayList<Object>();
            events.put(eventClass, eventList);
        }
        eventList.add(event);

        for (EntityListener l : listeners)
        {
            l.eventAdded(event);
        }
    }

    public void removeEvent(Object event)
    {
        Class eventClass = event.getClass();
        Collection<Object> eventList = events.get(eventClass);
        if (eventList == null)
        {
            return;
        }
        if (eventList.remove(event))
        {
            // NOTE: not sure about this for garbage collection issues.
//            if (eventList.isEmpty())
//            {
//                events.remove(eventClass);
//            }

            for (EntityListener l : listeners)
            {
                l.eventRemoved(event);
            }
        }
    }

    @Override
    public String toString()
    {
        return name;
    }
}
