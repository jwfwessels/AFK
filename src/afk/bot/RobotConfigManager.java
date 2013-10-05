package afk.bot;

import java.util.UUID;

/**
 *
 * @author Daniel
 */
public interface RobotConfigManager
{
    public String getProperty(UUID id, String name);
    public void setProperty(UUID id, String name, String value);
}
