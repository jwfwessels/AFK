
import afk.london.Robot;
import com.hackoeur.jglm.support.FastMath;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class SampleBot extends Robot
{

    boolean running = true;
    int move = 0;
    float turn = 0;
    int shoot = 0;
    int turns = 0;

    public SampleBot()
    {
        super();
    }

    @Override
    public void run()
    {
        if (running)
        {
            {
                if (move < 0)
                {
                    moveForward();
                    move++;
                }
                System.out.println("");
                turns++;
                float[] visibles = events.getVisibleBots();
                if (visibles.length > 0)
                {
                    turn = visibles[0];
                    System.out.println("turn: " + turn);
                    float diff = FastMath.abs(turn);

                    if (Float.compare(diff, 0.5f) < 0)
                    {
//                        attack();
                        System.out.println("BANG!");
                        running = false;
                    } else
                    {
                        if (Float.compare(turn, 0) < 0)
                        {

                            turnClockwise();
                            turn++;
                        }
                        if (Float.compare(turn, 0) > 0)
                        {
                            turnAntiClockwise();
                            turn--;
                        }
                    }
                } else
                {
                    turnClockwise();
                }
            }

        }
    }
}