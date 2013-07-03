import afk.london.Robot;

/**
 * Sample class of what coded bot will look like
 * @author Jessica
 *
 */
public class SampleBot2 extends Robot
{

    boolean running = true;
    int move = 0;
    int turn = 0;
    int shoot = 0;

    public SampleBot2()
    {
        super();
    }

    @Override
    public void run()
    {
        if (running)
        {
//            if (move < 200)
//            {
//                moveForward();
//                move++;
//            }
//            else if (turn < 90)
//            {
//                turnClockwise();
//                turn++;
//            }
//            else if (move < 400)
//            {
//                moveBackwards();
//                move++;
//            }
//            else if (move < 800 && turn < 270)
//            {
//                moveBackwards();
//                move++;
//                if (move % 2 == 0)
//                {
//                    turnAntiClockwise();
//                    turn++;
//                }
//            }
//            else if (turn < 360)
//            {
//                turnAntiClockwise();
//                turn++;
//            }
//            else if (move < 1000)
//            {
//                moveForward();
//                move++;
//            }
//            else
//            {
//                shoot++;
//                if (shoot > 240)
//                {
//                    System.out.println("test");
//                running = false;
//                    
//                }
//                attack();
//            }
        }

    }
}