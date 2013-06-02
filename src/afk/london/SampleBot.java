package afk.london;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import afk.london.Robot;

/**
 *
 * @author Jessica Sample class of what coded bot will look like
 *
 */
public class SampleBot extends Robot
{

    int move = 100;
    int turn = 50;

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
        }
        if (turn <= 25)
        {
            moveBackwards();
            turnAntiClockwise();
        }
        if (turn == 0)
        {
            moveBackwards();
        }
        if (turn == 0 && move == 0)
        {
            attack();
        }

    }
}
