package afk.game;

import afk.bot.RobotEngine;
import afk.bot.RobotException;
import afk.bot.RobotLoader;
import afk.bot.london.London;
import afk.bot.london.LondonRobotLoader;
import afk.frontend.Frontend;
import afk.ge.GameEngine;
import afk.ge.tokyo.Tokyo;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class AFKCoordinator implements Coordinator
{

    private RobotLoader botLoader;
    
    private List<String> participants = new ArrayList<String>();

    public AFKCoordinator()
    {
        botLoader = new LondonRobotLoader();
    }

    @Override
    public void loadRobot(String path) throws RobotException
    {
        botLoader.addRobot(path);
    }

    @Override
    public void addRobot(String path)
    {
        participants.add(path);
    }

    @Override
    public void removeRobot(String path)
    {
        participants.remove(path);
    }

    @Override
    public GameCoordinator newGame()
    {
        
        GameCoordinator game = new AFKGameCoordinator(botLoader, participants);
        
        return game;
    }
}
