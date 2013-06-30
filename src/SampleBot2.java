
import afk.london.Robot;



/**
 * Sample class of what coded bot will look like
 * @author Jessica
 *
 */
public class SampleBot2 extends Robot
{

    int movement = 0;
    int rotation = 0;
    boolean turning = true;

    public SampleBot2()
    {
        super();
    }

    @Override
    public void run()
    {
        if (turning)
        {
            turn();
        }
        else
        {
            move();
        }
    }
    
    public void turn()
    {
        if (rotation < 90)
        {
            turnAntiClockwise();
            rotation++;
        }
        else
        {
            rotation = 0;
            turning = false;
        }
    }
    
    public void move()
    {
        if (movement < 1600)
        {
            moveForward();
            movement++;
        }
        else
        {
            movement = 0;
            turning = true;
        }
    }
}
