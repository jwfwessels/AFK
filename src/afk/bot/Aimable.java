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

/**
 * Interface describing a robot that has the ability to aim a turret and barrel.
 * @author Jw
 */
public interface Aimable
{

    /**
     * Rotates the robot's turret anti clockwise.
     * @param amount the amount (number of ticks) to rotate the turret.
     */
    public void aimAntiClockwise(int amount);

    /**
     * Rotates the robot's turret clockwise.
     * @param amount the amount (number of ticks) to rotate the turret.
     */
    public void aimClockwise(int amount);

    /**
     * Aims the robot's barrel down.
     * @param amount the amount (number of ticks) to rotate the barrel.
     */
    public void aimDown(int amount);

    /**
     * Aims the robot's barrel up.
     * @param amount the amount (number of ticks) to rotate the barrel.
     */
    public void aimUp(int amount);
    
}
