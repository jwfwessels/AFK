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
 package afk.bot.london;

import afk.bot.Robot;
import afk.bot.RobotConfigManager;
import afk.bot.RobotEngine;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author Jessica
 */
public class London implements RobotEngine
{
    private RobotConfigManager config;
    private Map<UUID, Robot> robots = new HashMap<UUID, Robot>();

    public London(RobotConfigManager config)
    {
        this.config = config;
    }

    @Override
    public void addRobot(Robot robot) 
    {
        UUID id = robot.getId();
        robots.put(id, (AbstractRobot)robot);
    }

    @Override
    public void removeRobot(Robot robot)
    {
        removeRobot(robot.getId());
    }

    @Override
    public void removeRobot(UUID id)
    {
        robots.remove(id);
    }

    @Override
    public void removeAllRobots()
    {
        robots.clear();
    }

    /// this is where bot execution actually happens
    // TODO: multithread this bad-boy
    @Override
    public void execute(UUID id)
    {
        if (config.isInitComplete())
        {
            throw new RuntimeException("Executing before finishing init phase!");
        }
        Robot robot = robots.get(id);
        if (robot == null)
        {
            throw new RuntimeException("Robot " + id + " does not exist in this engine.");
        }
        //robot.clearFlags();
        robot.run();
    }

    @Override
    public Robot[] getAllRobots()
    {
        return robots.values().toArray(new Robot[0]);
    }

    @Override
    public boolean[] getFlags(UUID id)
    {
        return robots.get(id).getActions();
    }

    @Override
    public void setEvents(UUID id, RobotEvent events)
    {
        robots.get(id).setEvents(events);
    }

    @Override
    public RobotConfigManager getConfigManager()
    {
        return config;
    }
    
}
