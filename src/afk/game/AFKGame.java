package afk.game;

import afk.bot.Robot;
import afk.bot.RobotEngine;
import afk.bot.RobotException;
import afk.bot.RobotLoader;
import afk.bot.london.London;
import afk.ge.GameEngine;
import afk.ge.tokyo.GameResult;
import afk.ge.tokyo.Tokyo;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class AFKGame implements GameMaster
{

    private GameEngine gameEngine;
    private GraphicsEngine gfxEngine;
    private RobotEngine botEngine;
    private Collection<GameListener> listeners = new ArrayList<GameListener>();

    public AFKGame(RobotLoader botLoader)
    {
        botEngine = new London(botLoader);
        gfxEngine = new Athens(false);
        gameEngine = new Tokyo(gfxEngine, botEngine, this);
    }

    @Override
    public Component getAWTComponent()
    {
        return gfxEngine.getAWTComponent();
    }

    @Override
    public Robot addRobotInstance(String robot) throws RobotException
    {
        return botEngine.addRobot(robot);
    }

    @Override
    public void removeAllRobotInstances()
    {
        botEngine.removeAllRobots();
    }

    @Override
    public void removeRobotInstance(UUID id)
    {
        botEngine.removeRobot(id);
    }

    @Override
    public void removeRobotInstance(Robot robot)
    {
        botEngine.removeRobot(robot.getId());
    }

    @Override
    public void start() throws RobotException
    {
        botEngine.initComplete();
        gameEngine.startGame();
    }

    @Override
    public void addGameListener(GameListener listener)
    {
        listeners.add(listener);
    }

    @Override
    public void removeGameListener(GameListener listener)
    {
        listeners.remove(listener);
    }

    @Override
    public void playPause()
    {
        gameEngine.playPause();
    }

    @Override
    public float getGameSpeed()
    {
        return gameEngine.getSpeed();
    }

    @Override
    public void increaseSpeed()
    {
        gameEngine.increaseSpeed();
    }

    @Override
    public void decreaseSpeed()
    {
        gameEngine.decreaseSpeed();
    }

    @Override
    public void gameOver(GameResult result)
    {
        for (GameListener l : listeners)
        {
            l.gameOver(result);
        }
    }
}
