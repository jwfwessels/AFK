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

    int move = 200;
    int turn = 100;

    public SampleBot()
    {
        super();
    }

    @Override
    public void run()
    {
        if (true)
        {
            
        }
            System.out.println("turn" + turn);
            System.out.println("move" + move);
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
