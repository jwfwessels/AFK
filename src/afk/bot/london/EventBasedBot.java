package afk.bot.london;

/**
 *
 * @author Jw
 */
public abstract class EventBasedBot extends AbstractRobot
{

    private boolean first = true;
    private float sonarWarningDistance = 3.0f;
    private long time;

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
            hitObject();
        }
        if (events.gotHit)
        {
            gotHit();
        }
        if (!events.visibleBots.isEmpty())
        {
            robotVisible();
        }
        for (int d = 0; d < events.sonar.distance.length; d++)
        {
            if (events.sonar.distance[d] < sonarWarningDistance)
            {
                sonarWarning();
                break;
            }
        }

        runCommands();
        
        time++;
    }

    /**
     * Sets the sonar warning distance for sonar events. Sonar warning events
     * are handled by the sonarWarning(int) method.
     *
     * @param sonarWarningDistance the new sonar warning distance.
     */
    public final void setSonarWarningDistance(float sonarWarningDistance)
    {
        this.sonarWarningDistance = sonarWarningDistance;
    }

    /**
     * Get the number of ticks since the start of the game.
     * @return the number of ticks since the start of the game.
     */
    public long getTime()
    {
        return time;
    }

    /**
     * System method. Runs all commands set by user.
     */
    public abstract void runCommands();

    /**
     * Called at the start of the game. User must give the robot's first commands here.
     */
    public abstract void start();

    /**
     * Called when the robot hits an object (e.g. a wall or another bot).
     */
    public abstract void hitObject();

    /**
     * Called when the robot gets hit by another robot's projectile.
     */
    public abstract void gotHit();

    /**
     * Called when the robot successfully landed a shot on another robot.
     */
    public abstract void didHit();

    /**
     * Called when one of the sonars reads a measurment below the given threshold.
     * The threshold can be set through setSonarWarningDistance(float)
     */
    public abstract void sonarWarning();

    /**
     * Called when a robot is visible.
     */
    public abstract void robotVisible();
}
