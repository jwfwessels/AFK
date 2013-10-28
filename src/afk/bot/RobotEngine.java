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
 package afk.bot;

import afk.bot.london.RobotEvent;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public interface RobotEngine
{
    /**
     * Adds an instance of a robot to this robot engine and initialises it.
     * @param robot the robot to add
     */
    public void addRobot(Robot robot);
    
    /**
     * Removes an instance of a robot from this robot engine.
     * @param id the id of the robot to remove.
     */
    public void removeRobot(UUID id);
    
    /**
     * Removes an instance of a robot from this robot engine.
     * @param robot the robot to remove.
     */
    public void removeRobot(Robot robot);
    
    /**
     * Removes all robots from this robot engine.
     */
    public void removeAllRobots();

    /**
     * Retrieve a list of all the currently added robot instances.
     * @return a list of the UUIDs of the current robot instances. 
     */
    public Robot[] getAllRobots();
    
    /**
     * Executes a single logic tick of a robot.
     * @param id the id of the robot.
     */
    public void execute(UUID id);

    /**
     * Get the action flags from the specified robot since the last execution.
     * @param id
     * @return 
     */
    public boolean[] getFlags(UUID id);

    /**
     * Sets the events of the specified robot for the next execution.
     * @param id
     * @param events 
     */
    public void setEvents(UUID id, RobotEvent events);
    
    /**
     * Get the configuration manager for this robot engine.
     * @return 
     */
    public RobotConfigManager getConfigManager();
}
