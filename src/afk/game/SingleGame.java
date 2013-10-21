package afk.game;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.bot.RobotEngine;
import afk.bot.london.London;
import afk.ge.GameEngine;
import afk.ge.tokyo.Tokyo;
import afk.gfx.GraphicsEngine;
import afk.gfx.athens.Athens;
import java.awt.Component;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class SingleGame extends AbstractGameMaster
{

    private GameEngine gameEngine;
    private GraphicsEngine gfxEngine;
    private RobotEngine botEngine;

    public SingleGame(RobotConfigManager config)
    {
        this.botEngine = new London(config);
        gfxEngine = new Athens(false);
        gameEngine = new Tokyo(gfxEngine, botEngine, this);
    }

    @Override
    public Component getAWTComponent()
    {
        return gfxEngine.getAWTComponent();
    }

    @Override
    public void addRobotInstance(Robot robot)
    {
        botEngine.addRobot(robot);
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
    public String getRobotName(UUID id)
    {
        return botEngine.getConfigManager().getProperty(id, "name");
    }

    @Override
    public void start() 
    {
        gameEngine.startGame();
        for (GameListener listener : listeners)
        {
            listener.newGame(this);
        }
    }

    @Override
    public void stop()
    {
        gameEngine.stopGame();
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
}
