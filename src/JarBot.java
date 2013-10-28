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
 
import afk.bot.london.TankRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;


/**
 * Sample robot class, same as randomBot but makes use of RandomClass class
 * Use for testing Jar loading
 * @author Jessica
 *
 */
public class JarBot extends TankRobot
{
    RandomClass manager = new RandomClass();

    public JarBot()
    {
        
    }

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        manager.setMovement(200);
        manager.setRotation(180);
        manager.setTurning(true);
    }

    @Override
    public void gotHit()
    {
        idle();
    }

    @Override
    public void didHit()
    {
        idle();
    }

    @Override
    public void sonarWarning(float[] distance)
    {
        idle();
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        target(visibleBots.get(0), 0.6f);
    }

    @Override
    public void idle()
    {
        if (manager.getTurning())
        {
            if(manager.turn())
            {
                turnAntiClockwise(1);
            }
        }
        else
        {
            if(manager.move())
            {
                moveForward(1);
            }
        }
    }
}
