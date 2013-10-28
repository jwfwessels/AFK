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
 
import afk.bot.london.Sonar;
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class DebugBot extends TankRobot
{
    private float theta = Float.POSITIVE_INFINITY;

    public DebugBot()
    {
        super();
    }
    
    private boolean different(float a, float b)
    {
        return Math.abs(a-b) > 0.0001f;
    }

    @Override
    public void start()
    {
    }

    @Override
    public void hitObject()
    {
    }

    @Override
    public void gotHit()
    {
    }

    @Override
    public void didHit()
    {
    }

    @Override
    public void sonarWarning(float[] distance)
    {
        System.out.println("sonar_front: " + distance[Sonar.FRONT]);
        System.out.println("sonar_back: " + distance[Sonar.BACK]);
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        if (different(visibleBots.get(0).bearing, theta))
        {
            System.out.println(getId() + " -> " + (theta = visibleBots.get(0).bearing));
        }
    }

    @Override
    public void idle()
    {
    }
}
