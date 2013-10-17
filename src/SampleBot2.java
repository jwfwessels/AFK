
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class SampleBot2 extends TankRobot {


    public SampleBot2() {
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
        moveForward(120);
    }
}