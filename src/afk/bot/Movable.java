package afk.bot;

/**
 * Interface describing a robot that has the ability to move forwards and backwards.
 * @author Daniel
 */
public interface Movable
{
    /**
     * Moves the robot forward.
     * @param amount the amount (i.e. number of ticks) to move the robot forward.
     */
    public void moveForward(int amount);
    /**
     * Moves the robot backward.
     * @param amount the amount (i.e. number of ticks) to move the robot bacward.
     */
    public void moveBackward(int amount);
}
