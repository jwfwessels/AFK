package afk.bot.london;

/**
 *
 * @author Jw
 */
public abstract class EventBasedBot extends AbstractRobot
{
    
    boolean first = true;

    public EventBasedBot(int numActions)
    {
        super(numActions);
    }

    @Override
    public final void run()
    {
        if (first)
        {
            start();
            first = false;
        }
        
        if (events.didHit)
        {
            didHit();
        }
        if (events.hitWall)
        {
            hitWall();
        }
        if (events.gotHit)
        {
            gotHit();
        }
        if (!events.visibleBots.isEmpty())
        {
            robotVisible();
        }
        
        
    }
    
    public abstract void start();

    public abstract void hitWall();

    public abstract void gotHit();

    public abstract void didHit();
    
    public abstract void robotVisible();
}
