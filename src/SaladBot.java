
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * @author Ruben
 */
public class SaladBot extends TankRobot
{
    private SaladState _state;
    
    public SaladBot()
    {
        super();
        _state = new HuntState();
    }

    @Override
    public void init()
    {
        super.init();
        setName("Salad");
    }

    @Override
    public void start()
    {
        _state.act();
    }

    @Override
    public void hitObject()
    {
        _state.onHitObject();
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        _state.robotVisible(visibleBots);
    }

    @Override
    public void idle()
    {
        _state.onIdle();
    }
    
    public abstract class SaladState
    {
        public abstract void act();
        
        public void setState(SaladState newState)
        {
            _state = newState;
            _state.act();
        }
        
        public abstract void onIdle();
        
        public void onHitObject()
        {
            clearActions();
            
            moveBackward(5);
            turnClockwise(10);
        }
        
        public abstract void robotVisible(List<VisibleRobot> visibleBots);
    }
    
    public class HuntState extends SaladState
    {
        @Override
        public void act()
        {
            turnClockwise(5);
            moveForward(10);
        }

        @Override
        public void onIdle()
        {
            act();
        }

        @Override
        public void robotVisible(List<VisibleRobot> visibleBots)
        {
            VisibleRobot prey = visibleBots.get(0);
            
            setState(new KillState(prey));
        }
    }
    
    public class KillState extends SaladState
    {
        private VisibleRobot _prey;
        
        public KillState(VisibleRobot prey)
        {
            _prey = prey;
        }
        
        @Override
        public void act()
        {
        }

        @Override
        public void onIdle()
        {
            setState(new HuntState());
        }

        @Override
        public void robotVisible(List<VisibleRobot> visibleBots)
        {
            boolean preyIsStillVisible = false;
            
            for (VisibleRobot v : visibleBots)
            {
                if (v.targetUUID.equals(_prey.targetUUID))
                {
                    preyIsStillVisible = true;
                    break;
                }
            }
            
            if (!preyIsStillVisible)
            {
                setState(new HuntState());
            }
        }
    }
}
