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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class RobotEvent
{

    /**
     * List of visible bots as angles from where tank is facing. Empty if there
     * are none.
     */
    public List<VisibleRobot> visibleBots = new ArrayList<VisibleRobot>();
    /**
     * Information from the robot's sonar;
     */
    public Sonar sonar = new Sonar();
    /**
     * The robot's current pitch.
     */
    public float pitch = 0;
    /**
     * The robot's current heading.
     */
    public float heading = 0;
    /**
     * The current angle of the turret relative to the robot.
     */
    public float turret = 0;
    /**
     * The current angle of the barrel relative to the robot.
     */
    public float barrel = 0;
    /**
     * True if bot was hit by another.
     */
    public boolean gotHit = false;
    /**
     * True if bot successfully hit another.
     */
    public boolean didHit = false;
    /**
     * true if bot drove into a wall.
     */
    public boolean hitWall = false;
}
