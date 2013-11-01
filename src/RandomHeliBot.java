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
 
import afk.bot.london.HeliRobot;
import afk.bot.london.VisibleRobot;
import java.util.List;

/**
 * Sample class of what coded bot will look like
 *
 * @author Jessica
 *
 */
public class RandomHeliBot extends HeliRobot
{

    private float GIVE = 1.4f;
    private int AVOIDANCE = 50;

    public RandomHeliBot()
    {
        super();
    }

    @Override
    public void init()
    {
        super.init();
        setName("Whirlybird");
    }

    @Override
    public void start()
    {
        idle();
    }

    @Override
    public void hitObject()
    {
        clearActions();
        cancelAllTimers();
        if (getActionValue(MOVE_BACK) > 0)
        {
            moveForward(AVOIDANCE);
        } else
        {
            moveBackward(AVOIDANCE);
        }
        startTimer(AVOIDANCE / 3, new Runnable()
        {
            @Override
            public void run()
            {
                if (Math.random() > 0.5)
                {
                    turnClockwise(180);
                } else
                {
                    turnAntiClockwise(180);
                }
            }
        });
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        clearActions();
        cancelAllTimers();
        target(visibleBots.get(0), GIVE);
    }

    @Override
    public void idle()
    {
        startTimer((int) (Math.random() * 300), new Runnable() {

            @Override
            public void run()
            {
                int amount = (int) (Math.random() * 180);
                moveForward(amount);
                if (Math.random() > 0.5)
                {
                    turnClockwise(amount);
                } else
                {
                    turnAntiClockwise(amount);
                }
            }
        });

        moveForward((int) (Math.random() * 300));
    }
}
