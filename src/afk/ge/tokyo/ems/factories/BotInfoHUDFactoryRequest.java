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
    public Integer top;
    public Integer right;
    public Integer bottom;
    public Integer left;

    public BotInfoHUDFactoryRequest(Entity bot, int x, int y)
    {
        this(bot, y, null, null, x);
    }

    public BotInfoHUDFactoryRequest(Entity bot, Integer top, Integer right,
            Integer bottom, Integer left)
    {
        this.bot = bot;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
    }
    
}
