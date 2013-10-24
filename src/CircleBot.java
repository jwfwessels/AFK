import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class CircleBot extends TankRobot
{
    private int AVOIDANCE = 50;
    private int IDLE_MOVEMENT = 10;

    boolean antiBot;

    public CircleBot()
    {
        super();

        antiBot = Math.random() > 0.5;
    }

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        if (getActionValue(MOVE_BACK) > 0) moveForward(AVOIDANCE);
        else moveBackward(AVOIDANCE);
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
        if (antiBot) turnAntiClockwise(90);
        else turnClockwise(90);
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        target(visibleBots.get(0), 0.6f);
    }

    @Override
    public void idle()
    {
        moveForward(IDLE_MOVEMENT);
    }
}
