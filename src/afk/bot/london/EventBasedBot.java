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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * A Robot that is primarily driven through events.
 * 
 * @author Jw
 */
public abstract class EventBasedBot extends AbstractRobot
{

    private int numActions;
    private boolean first = true;
    private float sonarWarningThreshold = 3.0f;
    private long time;
    private boolean idle;
    private int numTimers = 0;

    private class RobotTimer
    {

        Runnable r;
        int ticks;

        public RobotTimer(Runnable r, int ticks)
        {
            this.r = r;
            this.ticks = ticks;
        }

        public boolean tick()
        {
            ticks--;
            if (ticks <= 0)
            {
                jobs.add(r);
                return false;
            }
            return true;
        }
    }
    private HashMap<Integer, RobotTimer> timers = new HashMap<Integer, RobotTimer>();
    private ArrayList<Runnable> jobs = new ArrayList<Runnable>();

    public EventBasedBot(int numActions)
    {
        super(numActions);
        this.numActions = numActions;
    }

    @Override
    public final void run()
    {

        // "use up" a tick in each action value
        for (int i = 0; i < numActions; i++)
        {
            int value = getActionValue(i) - 1;
            if (value >= 0)
            {
                setActionValue(i, value);
            }
        }

        idle = true;
        if (first)
        {
            idle = false;
            start();
            first = false;
        }

        Iterator<RobotTimer> it = timers.values().iterator();
        while (it.hasNext())
        {
            if (!it.next().tick())
            {
                it.remove();
            }
        }
        for (Runnable r : jobs)
        {
            r.run();
        }
        jobs.clear();

        for (int d = 0; d < events.sonar.distance.length; d++)
        {
            if (events.sonar.distance[d] < sonarWarningThreshold)
            {
                idle = false;
                sonarWarning(events.sonar.distance);
                break;
            }
        }
        if (events.hitWall)
        {
            idle = false;
            hitObject();
        }
        if (events.didHit)
        {
            idle = false;
            didHit();
        }
        if (!events.visibleBots.isEmpty())
        {
            idle = false;
            robotVisible(events.visibleBots);
        }
        if (events.gotHit)
        {
            idle = false;
            gotHit();
        }
        for (int i = 0; i < numActions; i++)
        {
            if (getActionValue(i) > 0)
            {
                idle = false;
                break;
            }
        }

        if (idle)
        {
            idle();
        }
        
        onTick();

        time++;
    }

    /**
     * Sets the sonar warning distance for sonar events. Sonar warning events
     * are handled by the sonarWarning(int) method.
     *
     * @param threshold the new sonar warning distance.
     */
    protected final void setSonarWarningThreshold(float threshold)
    {
        this.sonarWarningThreshold = threshold;
    }

    /**
     * Get the number of ticks since the start of the game.
     *
     * @return the number of ticks since the start of the game.
     */
    protected final long currentTime()
    {
        return time;
    }

    /**
     * Start a new timer. The runnable will execute after the specified
     * number of ticks have passed.
     * @param ticks number of ticks to run the timer for.
     * @param runnable the runnable to be executed after the timer ends.
     * @return an identification number that can be used to cancel or query the timer.
     */
    protected final int startTimer(int ticks, Runnable runnable)
    {
        int timerID = numTimers++;
        timers.put(timerID, new RobotTimer(runnable, ticks));
        return timerID;
    }

    /**
     * Cancels a timer.
     * @param timerID the identification number of the timer.
     */
    protected final void cancelTimer(int timerID)
    {
        if (timers.containsKey(timerID))
        {
            timers.remove(timerID);
        }
    }
    
    /**
     * Reads the number of ticks left on a timer.
     * @param timerID the identification number of the timer.
     * @return the number of ticks left on the timer, -1 if the timer does not exist.
     */
    protected final int readTimer(int timerID)
    {
        if (timers.containsKey(timerID))
        {
            return timers.get(timerID).ticks;
        }
        return -1;
    }

    /**
     * Stops all existing timers.
     */
    protected final void cancelAllTimers()
    {
        timers.clear();
    }
    
    ////// THE FOLLOWING FUNCTIONS ARE TO BE OVERRIDDEN BY THE USERS ///////

    /**
     * Called at the start of the game. User must give the robot's first
     * commands here.
     */
    protected void start()
    {
        idle = true;
    }

    /**
     * Called when the robot hits an object (e.g. a wall or another bot).
     */
    protected void hitObject()
    {
        idle = true;
    }

    /**
     * Called when the robot gets hit by another robot's projectile.
     */
    protected void gotHit()
    {
        idle = true;
    }

    /**
     * Called when the robot successfully lands a shot on another robot.
     */
    protected void didHit()
    {
        idle = true;
    }

    /**
     * Called when at least one of the sonars reads a measurment below
     * the sonar warning threshold. The threshold can be set through
     * setSonarWarningThreshold(float).
     *
     * @param distance the distances of the sonar in each direction. Distance
     * indices can be found in afk.bot.london.Sonar.
     */
    protected void sonarWarning(float[] distance)
    {
        idle = true;
    }

    /**
     * Called when at least one robot is visible.
     *
     * @param visibleBots the list of visible bots. Guaranteed to not be empty.
     */
    protected void robotVisible(List<VisibleRobot> visibleBots)
    {
        idle = true;
    }

    /**
     * Called when no events were triggered during the tick AND the robot has no
     * commands left to execute.
     */
    protected void idle()
    {
        
    }
    
    /**
     * Called every game tick.
     */
    protected void onTick()
    {
        
    }
}
