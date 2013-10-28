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

import java.util.UUID;

/**
 *
 * @author Daniel
 */
public interface RobotConfigManager
{
    
    /**
     * Indicates that the initialisation phase is over and the game has started,
     */
    public void initComplete();
    
    /**
     * Check if initialisation is complete.
     * @return true if initialisation is complete, false otherwise.
     */
    public boolean isInitComplete();
    
    /**
     * Gets a property for a robot.
     * @param id the id of the robot.
     * @param name the name of the property to get.
     * @return the value of the property.
     */
    public String getProperty(UUID id, String name);
    
    /**
     * Sets a property for a robot.
     * @param id the id of the robot.
     * @param name the name of the property to set.
     * @param value the value to set the property to.
     */
    public void setProperty(UUID id, String name, String value);
    
    /**
     * Get the value of a constant.
     * @param the id of the robot.
     * @param name the name of the constant.
     * @return the value of the constant, null if the constant does not exist.
     */
    public Object getConstant(UUID id, String name);
}
