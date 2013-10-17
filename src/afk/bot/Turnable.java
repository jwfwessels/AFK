package afk.bot;

/**
 * Interface describing a robot that has the ability to turn left and right,
 * @author Daniel
 */
public interface Turnable
{
    /**
     * Turn the robot clockwise.
     * @param amount the amount (i.e. number of ticks) to turn clockwise.
     */
    public void turnClockwise(int amount);
    /**
     * Turn the robot anti clockwise.
     * @param amount the amount (i.e. number of ticks) to turn anti clockwise.
     */
    public void turnAntiClockwise(int amount);
}
