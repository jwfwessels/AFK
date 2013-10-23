package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.FactoryRequest;

/**
 *
 * @author daniel
 */
public class BotInfoHUDFactoryRequest implements FactoryRequest
{
    protected Entity bot;
    protected int x;
    protected int y;

    public BotInfoHUDFactoryRequest(Entity bot, int x, int y)
    {
        this.bot = bot;
        this.x = x;
        this.y = y;
    }
    
}
