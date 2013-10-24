
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class SampleBot extends TankRobot {

    public SampleBot() {
        super();
    }

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        idle();
    }

    @Override
    public void gotHit()
    {
        idle();
    }

    @Override
    public void didHit()
    {
        idle();
    }

    @Override
    public void sonarWarning(float[] distance)
    {
        idle();
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        idle();
    }

    @Override
    public void idle()
    {
        turnAntiClockwise(60);
    }
}