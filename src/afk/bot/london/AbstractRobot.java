package afk.bot.london;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import java.util.Arrays;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public abstract class AbstractRobot implements Robot
{
    private boolean[] actionFlags;
    protected RobotEvent events;
    private UUID id;
    private RobotConfigManager config;

    public AbstractRobot(int numActions)
    {
        actionFlags = new boolean[numActions];
        events = new RobotEvent();

        id = UUID.randomUUID();
    }
    
    @Override
    public final void setConfigManager(RobotConfigManager config)
    {
        this.config = config;
    }

    @Override
    public final RobotConfigManager getConfigManager()
    {
        return config;
    }

    @Override
    public void init()
    {
        setName(getClass().getSimpleName());
    }
    
    /**
     * Set the type of this robot. e.g. small tank, large helicopter, etc.
     * @param type 
     */
    protected final void setType(String type)
    {
        setProperty("type", type);
    }
    
    /**
     * Set the name of this robot. e.g. "The Karl Device" or "Mega J"
     * @param name 
     */
    protected final void setName(String name)
    {
        setProperty("name", name);
    }
    
    private void setProperty(String key, String value)
    {
        if (config == null)
        {
            throw new RuntimeException("Error: No config manager!");
        }
        config.setProperty(id, key, value);
    }

    @Override
    public final UUID getId()
    {
        return id;
    }

    @Override
    public final void setFlag(int index, boolean value)
    {
        if (index <= actionFlags.length && index >= 0)
        {
            actionFlags[index] = value;
        }
    }

    @Override
    public void setEvents(RobotEvent events)
    {
        this.events = events;
    }

    @Override
    public final boolean[] getActionFlags()
    {
        return Arrays.copyOf(actionFlags, actionFlags.length);
    }

    @Override
    public final void clearFlags()
    {
        for (int x = 0; x < actionFlags.length; x++)
        {
            actionFlags[x] = false;
        }
    }

    @Override
    public final void feedback(RobotEvent event)
    {
        this.events = event;
    }
    
    private String primitiveToString()
    {
        return getClass().getSimpleName();
    }
    
    private String complexToString()
    {
        String name = config.getProperty(getId(), "name");
        if (name == null || name.trim().isEmpty()) name = primitiveToString();
        return name;
    }

    @Override
    public final String toString()
    {
        return config == null ? primitiveToString() : complexToString();
    }
}
