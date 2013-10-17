package afk.bot;

/**
 * Interface describing a robot that has the ability to aim a turret and barrel.
 * @author Jw
 */
public interface Aimable
{

    /**
     * Rotates the robot's turret anti clockwise.
     * @param amount the amount (number of ticks) to rotate the turret.
     */
    public void aimAntiClockwise(int amount);

    /**
     * Rotates the robot's turret clockwise.
     * @param amount the amount (number of ticks) to rotate the turret.
     */
    public void aimClockwise(int amount);

    /**
     * Aims the robot's barrel down.
     * @param amount the amount (number of ticks) to rotate the barrel.
     */
    public void aimDown(int amount);

    /**
     * Aims the robot's barrel up.
     * @param amount the amount (number of ticks) to rotate the barrel.
     */
    public void aimUp(int amount);
    
}
