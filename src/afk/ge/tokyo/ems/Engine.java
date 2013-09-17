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
public class Engine implements EntityListener, FlagManager
{

    private Collection<ISystem> systems = new ArrayList<ISystem>();
    private Collection<Entity> entities = new ArrayList<Entity>();
    private Collection<Entity> toAdd = new ArrayList<Entity>();
    private Collection<Entity> toRemove = new ArrayList<Entity>();
    private Map<Class, Collection<Object>> nodeLists = new HashMap<Class, Collection<Object>>();
    private Map<Class, Family> families = new HashMap<Class, Family>();
    private Map<Flag, Boolean> flags = new HashMap<Flag, Boolean>();
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
        if (updating)
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

        for (Entity dep : entity.dependents)
        {
            removeEntity(dep);
        }
    }

    @Override
    public void componentAdded(Entity entity, Class componentClass)
    {
        for (Family family : families.values())
        {
            family.componentAddedToEntity(entity, componentClass);
        }
    }

    @Override
    public void componentRemoved(Entity entity, Class componentClass)
    {
        for (Family family : families.values())
        {
            family.componentRemovedFromEntity(entity, componentClass);
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

    public void addSystem(ISystem system)
    {
        if (system.init(this))
        {
            systems.add(system);
        }
    }

    public void update(float t, float dt)
    {
        updating = true;
        for (ISystem s : systems)
        {
            s.update(t, dt);
        }
        updating = false;

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
    }

    public void removeSystem(ISystem system)
    {
        if (systems.remove(system))
        {
            system.destroy();
        }
    }

    public void shutDown()
    {
        while (updating)
        { /* spin */ }

        for (ISystem s : systems)
        {
            s.destroy();
        }

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
