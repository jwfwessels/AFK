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
import afk.ge.ems.Constants;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public abstract class AbstractRobot implements Robot
{

    private static int numBots = 0;
    public static final String MOTOR_TOP_SPEED = "Motor.topSpeed";
    public static final String MOTOR_ANGULAR_VELOCITY = "Motor.angularVelocity";
    public static final String LIFE_MAX_HP = "Life.maxHp";
    public static final String TURRET_ANGULAR_VELOCITY = "Turret.angularVelocity";
    public static final String BARREL_ANGULAR_VELOCITY = "Barrel.angularVelocity";
    public static final String VISION_DIST = "Vision.dist";
    public static final String VISION_FOVY = "Vision.fovy";
    public static final String VISION_FOVX = "Vision.fovx";
    private int[] actions;
    protected RobotEvent events;
    private UUID id;
    private int botNum;
    private RobotConfigManager config;

    public AbstractRobot(int numActions)
    {
        actions = new int[numActions];
        events = new RobotEvent();

        id = UUID.randomUUID();
        botNum = numBots++;
    }

    @Override
    public final void setConfigManager(RobotConfigManager config)
    {
        this.config = config;
    }

    @Override
    public final RobotConfigManager getConfigManager()
    {
        return config;
    }

    @Override
    public void init()
    {
        setName(getClass().getSimpleName());
    }

    /**
     * Set the type of this robot. e.g. small tank, large helicopter, etc.
     *
     * @param type
     */
    protected final void setType(String type)
    {
        setProperty("type", type);
    }

    /**
     * Set the name of this robot. e.g. "The Karl Device" or "Mega J"
     *
     * @param name
     */
    protected final void setName(String name)
    {
        setProperty("name", name);
    }

    private void setProperty(String key, String value)
    {
        if (config == null)
        {
            throw new RuntimeException("Error: No config manager!");
        }
        config.setProperty(id, key, value);
    }
    
    private String getProperty(String key)
    {
        if (config == null)
        {
            throw new RuntimeException("Error: No config manager!");
        }
        return config.getProperty(id, key);
    }
    
    private Object getConstant(String key)
    {
        if (config == null)
        {
            throw new RuntimeException("Error: No config manager!");
        }
        return config.getConstant(id, key);
    }

    @Override
    public final UUID getId()
    {
        return id;
    }

    @Override
    public final int getBotNum()
    {
        return botNum;
    }

    /**
     * Get the top speed of the robot in units per game tick.
     * @return the top speed of the robot.
     */
    public final float getTopSpeed()
    {
        return (Float)getConstant(MOTOR_TOP_SPEED);
    }
    
    /**
     * Get the angular velocity (rotation speed) of the robot in degrees
     * per game tick.
     * @return the angular velocity of the robot.
     */
    public final float getAngularVelocity()
    {
        return (Float)getConstant(MOTOR_ANGULAR_VELOCITY);
    }
    
    /**
     * Get the maximum life of the robot.
     * @return the maximum life of the robot.
     */
    public final float getMaxLife()
    {
        return (Float)getConstant(LIFE_MAX_HP);
    }
    
    /**
     * Get the angular velocity of the robot's turret.
     * @return the angular velocity of the robot's turret.
     */
    public final float getTurretAngularVelocity()
    {
        return (Float)getConstant(TURRET_ANGULAR_VELOCITY);
    }
    
    /**
     * Get the angular velocity of the robot's barrel.
     * @return the angular velocity of the robot's barrel.
     */
    public final float getBarrelAngularVelocity()
    {
        return (Float)getConstant(BARREL_ANGULAR_VELOCITY);
    }
    
    /**
     * Get how far the robot can see.
     * @return the view distance of the robot.
     */
    public final float getViewDistance()
    {
        return (Float)getConstant(VISION_DIST);
    }
    
    /**
     * Get the vertical field of view of the robot in degrees.
     * @return the vertical field of view of the robot.
     */
    public final float getVerticalFOV()
    {
        return (Float)getConstant(VISION_FOVY);
    }
    
    /**
     * Get the horizontal field of view of the robot in degrees.
     * @return the horizontal field of view of the robot.
     */
    public final float getHorizontalFOV()
    {
        return (Float)getConstant(VISION_FOVX);
    }
    
    /**
     * Instruct the robot to apply action to be run for a certain number
     * of ticks.
     * @param index the index of the action to set.
     * @param ticks the number of ticks to apply the action for.
     */
    protected final void setActionValue(int index, int ticks)
    {
        if (ticks < 0)
        {
            throw new RuntimeException("Robot attempted to travel back in time!");
        }
        actions[index] = ticks;
    }
    
    /**
     * Get the number of ticks an action is still running for.
     * e.g. getActionValue(MOVE_FORWARD) would tell you how much further your
     * tank will move forward with its current instructions.
     * @param index
     * @return 
     */
    protected final int getActionValue(int index)
    {
        return actions[index];

    }
    @Override
    public final boolean[] getActions()
    {
        boolean[] flags = new boolean[actions.length];
        for (int i = 0; i < actions.length; i++)
        {
            flags[i] = actions[i] > 0;
        }
        return flags;
    }

    @Override
    public final void clearActions()
    {
        for (int x = 0; x < actions.length; x++)
        {
            actions[x] = 0;
        }
    }

    @Override
    public final void setEvents(RobotEvent event)
    {
        this.events = event;
    }

    private String primitiveToString()
    {
        return getClass().getSimpleName();
    }

    private String complexToString()
    {
        String name = config.getProperty(getId(), "name");
        if (name == null || name.trim().isEmpty())
        {
            name = primitiveToString();
        }
        return name;
    }

    @Override
    public final String toString()
    {
        return config == null ? primitiveToString() : complexToString();
    }
}
