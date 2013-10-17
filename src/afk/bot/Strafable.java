package afk.bot;

/**
 * Interface defining a robot that has the ability to strafe (i.e. move left and right without turning).
 * @author Daniel
 */
public interface Strafable
{
    /**
     * Move the robot left.
     * @param amount the amount (i.e. number of ticks) to strafe left.
     */
    public void strafeLeft(int amount);
    /**
     * Move the robot right.
     * @param amount the amount (i.e. number of ticks) to strafe right.
     */
    public void strafeRight(int amount);
}
