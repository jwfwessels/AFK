
import afk.bot.london.SmallTank;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class SampleBot2 extends SmallTank 
{

    boolean running = true;
    int move = 0;
    float turn = 0;
    int shoot = 0;
    int turns = 0;

    public SampleBot2()
    {
        super();
    }

    @Override
    public void run()
    {
        if (running)
        {
            {
//                if (move < 0)
//                {
                    moveForward();
                    move++;
//                }
                if (turn < 45)
                {
                    turnAntiClockwise();
                    turn++;
                }
                turns++;
                float[] visibles = events.getVisibleBots();
//                if (visibles.length > 0)
//                {
//                    turn = visibles[0];
//                    float diff = FastMath.abs(turn);
//
//                    if (Float.compare(diff, 0.5f) < 0)
//                    {
//                        attack();
//                    } else
//                    {
//                        if (Float.compare(turn, 0) < 0)
//                        {
//
//                            turnClockwise();
//                            turn++;
//                        }
//                        if (Float.compare(turn, 0) > 0)
//                        {
//                            turnAntiClockwise();
//                            turn--;
//                        }
//                    }
//                } else
//                {
//                    turnClockwise();
//                }
            }

        }
    }
}