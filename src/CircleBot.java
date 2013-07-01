
import afk.london.Robot;



/**
 * Sample class of what coded bot will look like
 * @author Jessica
 *
 */
public class CircleBot extends Robot
{

    int movement = 0;
    int rotation = 0;
    boolean turning = true;

    public CircleBot()
    {
        super();
    }

    @Override
    public void run()
    {
        float[] visibles = events.getVisibleBots();
        if (visibles.length > 0)
            attack();
        
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
