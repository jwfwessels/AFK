
import afk.bot.london.HeliRobot;
import afk.bot.london.VisibleBot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class AimHeliBot extends HeliRobot
{

    private int movement = 0;
    private int rotation = 0;
    private boolean turning = true;
    private int retaliating = 0;

    public AimHeliBot()
    {
        super();
    }

    @Override
    public void init()
    {
        super.init();
        setName("Whirlybird");
    }

    @Override
    public void run()
    {
        List<VisibleBot> visibles = events.getVisibleBots();
        if (!visibles.isEmpty())
        {
            VisibleBot visible = visibles.get(0);
            System.out.println("elevation: " + visible.elevation);
            System.out.println("barrel: " + events.barrel);
            float bearing = visible.bearing;
            float elevation = visible.elevation-events.barrel;
            float diff = bearing*bearing+elevation*elevation;
            final float give = 1.5f;
            System.out.println("diff0ld: " + diff + "      " + 0.6f*0.6f);
            System.out.println("diffnew: " + diff + "      " + give*give);
            if (Float.compare(diff, give*give) < 0)
            {
                attack();
                return;
            }
            
            if (Float.compare(bearing, 0) < 0)
            {
                aimAntiClockwise();
            }
            if (Float.compare(bearing, 0) > 0)
            {
                aimClockwise();
            }

            if (Float.compare(elevation, 0) < 0)
            {
                aimDown();
            }
            if (Float.compare(elevation, 0) > 0)
            {
                aimUp();
            }
            return;
        }
    }
}
