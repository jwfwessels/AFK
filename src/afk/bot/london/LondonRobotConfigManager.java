package afk.bot.london;

import afk.bot.RobotConfigManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public class LondonRobotConfigManager implements RobotConfigManager
{
    private Map<UUID, Map<String, String>> config = new HashMap<UUID, Map<String, String>>();
    // flag that states if bots are still in initialisation phase
    private boolean init = true;

    @Override
    public String getProperty(UUID id, String name)
    {
        return getProperties(id).get(name);
    }

    @Override
    public void setProperty(UUID id, String name, String value)
    {
        if (!init)
        {
            throw new RuntimeException("Property set outside of init phase");
        }
        
        getProperties(id).put(name, value);
    }
    
    private Map<String, String> getProperties(UUID id)
    {
        Map<String, String> properties = config.get(id);
        if (properties == null)
        {
            properties = new HashMap<String, String>();
            config.put(id, properties);
        }
        return properties;
    }
    
    @Override
    public void initComplete()
    {
        init = false;
    }

    @Override
    public boolean isInitComplete()
    {
        return init;
    }
    
}
