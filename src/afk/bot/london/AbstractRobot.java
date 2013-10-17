package afk.bot.london;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public abstract class AbstractRobot implements Robot
{

    private static int numBots = 0;
    private int[] actions;
    protected RobotEvent events;
    private UUID id;
    private int botNum;
    private RobotConfigManager config;

    public AbstractRobot(int numActions)
    {
        actions = new int[numActions];
        events = new RobotEvent();

        id = UUID.randomUUID();
        botNum = numBots++;
    }

    protected final void setConfigManager(RobotConfigManager config)
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
     *
     * @param type
     */
    protected final void setType(String type)
    {
        setProperty("type", type);
    }

    /**
     * Set the name of this robot. e.g. "The Karl Device" or "Mega J"
     *
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
    public int getBotNum()
    {
        return botNum;
    }

    protected final void setActionValue(int index, int value)
    {
        if (value < 0)
        {
            throw new RuntimeException("Robot attempted a negative value");
        }
        actions[index] = value;
    }
    
    protected final int getActionValue(int index)
    {
        return actions[index];
    }

    @Override
    public final boolean[] getActions()
    {
        boolean[] flags = new boolean[actions.length];
        for (int i = 0; i < actions.length; i++)
        {
            flags[i] = actions[i] > 0;
        }
        return flags;
    }

    @Override
    public final void clearActions()
    {
        for (int x = 0; x < actions.length; x++)
        {
            actions[x] = 0;
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
        if (name == null || name.trim().isEmpty())
        {
            name = primitiveToString();
        }
        return name;
    }

    @Override
    public final String toString()
    {
        return config == null ? primitiveToString() : complexToString();
    }
}
