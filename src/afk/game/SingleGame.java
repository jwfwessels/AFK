/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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
    public void nextGame()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
