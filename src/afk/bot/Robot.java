/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
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
 package afk.bot;

import afk.bot.london.RobotEvent;
import java.util.UUID;

/**
 * This is the interface to all user-written Robot classes.
 * 
 * @author Daniel
 */
public interface Robot
{

    /**
     * Sets all flags to there default "false" position.
     */
    public void clearActions();

    /**
     * Gets a copy of the action array.
     * @return 
     */
    public boolean[] getActions();
    
    /**
     * Sets the events for this tick.
     * @param events the events.
     */
    public void setEvents(RobotEvent events);

    /**
     * Gets the robot's unique ID.
     * @return 
     */
    public UUID getId();
    
    /**
     * Get the robot's number.
     * @return the robot's number.
     */
    public int getBotNum();

    /**
     * Main execution method of the robot implemented by the user. This is
     * called once every game tick to calculate the actions to take for that
     * game tick.
     */
    public void run();
    
    /**
     * Initialisation code is implemented by the user here. This is where any
     * robot configuration properties may be set.
     */
    public void init();
    
    /**
     * Gets the RobotConfigManager associated with this robot.
     * @return the RobotConfigManager associated with this robot.
     */
    public RobotConfigManager getConfigManager();
    
    /**
     * Sets the RobotConfigManager to associate with this robot.
     * @return the RobotConfigManager to associate with this robot.
     */
    public void setConfigManager(RobotConfigManager config);
    
}
