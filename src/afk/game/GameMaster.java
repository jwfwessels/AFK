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
import afk.bot.RobotException;
import java.awt.Component;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public interface GameMaster
{

    /**
     * Gets the AWT Component for this game.
     *
     * @return the AWT component for viewing the game.
     */
    public Component getAWTComponent();
    
    public void addRobotInstance(Robot robot);
    
    public void removeAllRobotInstances();
    
    public void removeRobotInstance(Robot robot);
    
    public void removeRobotInstance(UUID robot);
    
    public String getRobotName(UUID id);

    /**
     * Start the game.
     */
    public void start();
    
    public void nextGame();
    
    /**
     * Stop the game.
     */
    public void stop();

    public void playPause();
    
    public float getGameSpeed();

    public void increaseSpeed();

    public void decreaseSpeed();
    
    public void gameOver(GameResult result);

    /**
     * Registers a game listener to receive game events such as game over and
     * player death.
     *
     * @param listener
     */
    public void addGameListener(GameListener listener);

    /**
     * De-register a game listener.
     *
     * @param listener
     */
    public void removeGameListener(GameListener listener);
}
