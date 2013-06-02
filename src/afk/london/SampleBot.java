package afk.london;
import afk.london.Robot;

/**
 * Sample class of what coded bot will look like
 * @author Jessica
 *
 */
public class SampleBot extends Robot
{

    int move = 200;
    int turn = 100;

    public SampleBot()
    {
        super();
    }

    @Override
    public void run()
    {
        if (move > 50)
        {
            moveForward();
            move--;
        }
        if (turn > 25 && move <= 50)
        {
            turnClockwise();
            turn--;
        }
        if (turn <= 50 && turn > 0)
        {
            moveBackwards();
            turnAntiClockwise();
            move--;
            turn--;
        }
        if (turn == 0 && move > 0)
        {
            moveBackwards();
            move--;
        }
        if (turn == 0 && move == 0)
        {
            attack();

        }

    }
}
