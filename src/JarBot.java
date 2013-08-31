
import afk.bot.london.TankRobot;


/**
 * Sample robot class, same as randomBot but makes use of SomeClass class
 * Use for testing Jar loading
 * @author Jessica
 *
 */
public class JarBot extends TankRobot
{
    //int movement = 0;
    //int rotation = 0;
    //boolean turning = true;
    RandomClass manager = new RandomClass();

    public JarBot()
    {
        
    }

    @Override
    public void run()
    {
        if (events.hitWall())
        {
            manager.setMovement(200);
            manager.setRotation(180);
            manager.setTurning(true);
        }
        
        float[][] visibles = events.getVisibleBots();
        if (visibles.length > 0)
            attack();
        
        if (manager.getTurning())
        {
            if(manager.turn())
            {
                turnAntiClockwise();
            }
        }
        else
        {
            if(manager.move())
            {
                moveForward();
            }
        }
    }
}
