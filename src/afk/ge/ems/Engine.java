package afk.ge.ems;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author daniel
 */
public class Engine implements EntityListener, FlagManager
{

    private Collection<ISystem> logicSystems = new ArrayList<ISystem>();
    private Collection<ISystem> systems = new ArrayList<ISystem>();
    private Collection<Entity> entities = new ArrayList<Entity>();
    private Collection<Entity> toAdd = new ArrayList<Entity>();
    private Collection<Entity> toRemove = new ArrayList<Entity>();
    private Collection<ISystem> systemsToAdd = new ArrayList<ISystem>();
    private Collection<ISystem> logicSystemsToAdd = new ArrayList<ISystem>();
    private Collection<ISystem> systemsToRemove = new ArrayList<ISystem>();
    private Collection<EntityComponent> componentsAdded = new ArrayList<EntityComponent>();
    private Collection<EntityComponent> componentsRemoved = new ArrayList<EntityComponent>();
    private Map<Class<? extends Event>, List<Event>> events = new HashMap<Class<? extends Event>, List<Event>>();
    private Collection<Event> eventsAdded = new ArrayList<Event>();
    private Collection<Event> eventsRemoved = new ArrayList<Event>();
    private Map<Class, Object> globals = new HashMap<Class, Object>();
    private Map<Class, Family> families = new HashMap<Class, Family>();
    private Map<Flag, Boolean> flags = new HashMap<Flag, Boolean>();
    private AtomicBoolean updating = new AtomicBoolean(false);

    private class EntityComponent
    {

        Entity entity;
        Class component;

        public EntityComponent(Entity entity, Class component)
        {
            this.entity = entity;
            this.component = component;
        }
    }

    public void addEntity(Entity entity)
    {
        if (updating.get())
        {
            toAdd.add(entity);
            return;
        }
        entities.add(entity);
        entity.addEntityListener(this);
        for (Family family : families.values())
        {
            family.newEntity(entity);
        }

        for (Entity dep : entity.dependents)
        {
            addEntity(dep);
        }
    }

    public void removeEntity(Entity entity)
    {
        if (updating.get())
        {
            toRemove.add(entity);
            return;
        }
        entity.removeEntityListener(this);

        for (Family family : families.values())
        {
            family.removeEntity(entity);
        }

        entities.remove(entity);
        entity.returnToSource();

        for (Entity dep : entity.dependents)
        {
            removeEntity(dep);
        }
    }

    public void addGlobal(Object global)
    {
        globals.put(global.getClass(), global);
    }

    public void removeGlobal(Class globalClass)
    {
        globals.remove(globalClass);
    }

    public <T> T getGlobal(Class<T> globalClass)
    {
        return (T) globals.get(globalClass);
    }

    @Override
    public void componentAdded(Entity entity, Class componentClass)
    {
        if (updating.get())
        {
            componentsAdded.add(new EntityComponent(entity, componentClass));
        } else
        {
            for (Family family : families.values())
            {
                family.componentAddedToEntity(entity, componentClass);
            }
        }
    }

    @Override
    public void componentRemoved(Entity entity, Class componentClass)
    {
        if (updating.get())
        {
            componentsRemoved.add(new EntityComponent(entity, componentClass));
        } else
        {
            for (Family family : families.values())
            {
                family.componentRemovedFromEntity(entity, componentClass);
            }
        }
    }

    @Override
    public void eventAdded(Event event)
    {
        if (updating.get())
        {
            eventsAdded.add(event);
        } else
        {
            Class eventClass = event.getClass();
            List<Event> eventList = events.get(eventClass);
            if (eventList == null)
            {
                eventList = new ArrayList<Event>();
                events.put(eventClass, eventList);
            }
            eventList.add(event);
        }
    }

    @Override
    public void eventRemoved(Event event)
    {
        if (updating.get())
        {
            eventsRemoved.add(event);
        } else
        {
            Collection<Event> eventList = events.get(event.getClass());
            eventList.remove(event);
        }
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

    public <E extends Event> List<E> getEventList(Class<E> eventClass)
    {
        List<E> eventList = (List<E>) events.get(eventClass);
        if (eventList == null)
        {
            eventList = new ArrayList<E>();
            events.put(eventClass, (List<Event>) eventList);
        }
        return eventList;
    }

    public void addLogicSystem(ISystem system)
    {
        if (updating.get())
        {
            logicSystemsToAdd.add(system);
        } else
        {
            if (system.init(this))
            {
                logicSystems.add(system);
            }
        }
    }

    public void addSystem(ISystem system)
    {
        if (updating.get())
        {
            systemsToAdd.add(system);
        } else
        {
            if (system.init(this))
            {
                systems.add(system);
            }
        }
    }

    public void updateLogic(float t, float dt)
    {
        updating.set(true);
        for (ISystem s : logicSystems)
        {
            s.update(t, dt);
        }
        updating.set(false);

        postUpdate();
    }

    public void update(float t, float dt)
    {
        updating.set(true);
        for (ISystem s : systems)
        {
            s.update(t, dt);
        }
        updating.set(false);

        postUpdate();
    }

    private void postUpdate()
    {
        for (Entity e : toRemove)
        {
            removeEntity(e);
        }
        toRemove.clear();
        for (Entity e : toAdd)
        {
            addEntity(e);
        }
        toAdd.clear();

        for (ISystem s : systemsToAdd)
        {
            addSystem(s);
        }
        systemsToAdd.clear();
        for (ISystem s : logicSystemsToAdd)
        {
            addLogicSystem(s);
        }
        logicSystemsToAdd.clear();

        for (ISystem s : systemsToRemove)
        {
            removeSystem(s);
        }
        systemsToRemove.clear();

        for (EntityComponent ec : componentsAdded)
        {
            componentAdded(ec.entity, ec.component);
        }
        componentsAdded.clear();

        for (EntityComponent ec : componentsRemoved)
        {
            componentRemoved(ec.entity, ec.component);
        }
        componentsRemoved.clear();

        for (Event event : eventsAdded)
        {
            eventAdded(event);
        }
        eventsAdded.clear();

        for (Event event : eventsRemoved)
        {
            eventRemoved(event);
        }
        eventsRemoved.clear();
    }

    public void removeSystem(ISystem system)
    {
        if (updating.get())
        {
            systemsToRemove.add(system);
        } else
        {
            if (logicSystems.remove(system))
            {
                system.destroy();
            } else if (systems.remove(system))
            {
                system.destroy();
            }
        }
    }

    public void shutDown()
    {
        while (updating.get())
        { /* spin */ }

        for (ISystem s : logicSystems)
        {
            s.destroy();
        }
        for (ISystem s : systems)
        {
            s.destroy();
        }

        logicSystems.clear();
        systems.clear();
    }

    class Flag
    {

        private Object source;
        private int symbol;

        public Flag(Object source, int symbol)
        {
            this.source = source;
            this.symbol = symbol;
        }

        public Object getSource()
        {
            return source;
        }

        public int getSymbol()
        {
            return symbol;
        }

        @Override
        public boolean equals(Object obj)
        {
            if (obj instanceof Flag)
            {
                Flag other = (Flag) obj;
                return other.symbol == symbol
                        && source == null ? other.source == null : source.equals(other.source);
            }
            return false;
        }

        @Override
        public int hashCode()
        {
            int hash = 7;
            hash = 41 * hash + this.symbol;
            hash = 41 * hash + (this.source != null ? this.source.hashCode() : 0);
            return hash;
        }
    }

    @Override
    public boolean getFlag(Object source, int symbol)
    {
        return flags.get(new Flag(source, symbol)) == Boolean.TRUE;
    }

    @Override
    public void setFlag(Object source, int symbol, boolean value)
    {
        flags.put(new Flag(source, symbol), value);
    }
}
