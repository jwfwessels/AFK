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
 * @author Ruben
 */
public class SaladBot extends TankRobot
{
    private SaladState _state;
    
    public SaladBot()
    {
        super();
        _state = new HuntState();
    }

    @Override
    public void init()
    {
        super.init();
        setName("Salad");
    }

    @Override
    public void start()
    {
        _state.act();
    }

    @Override
    public void hitObject()
    {
        _state.onHitObject();
    }

    @Override
    public void robotVisible(List<VisibleRobot> visibleBots)
    {
        _state.robotVisible(visibleBots);
    }

    @Override
    public void idle()
    {
        _state.onIdle();
    }
    
    public abstract class SaladState
    {
        public abstract void act();
        
        public void setState(SaladState newState)
        {
            _state = newState;
            _state.act();
        }
        
        public abstract void onIdle();
        
        public void onHitObject()
        {
            clearActions();
            
            turnAntiClockwise(50);
            
            startTimer(50, new Runnable() {
                @Override
                public void run()
                {
                    if (getActionValue(MOVE_BACK) > 0)
                    {
                        moveForward(50);
                    } else
                    {
                        moveBackward(50);
                    }
                }
            });
        }
        
        public abstract void robotVisible(List<VisibleRobot> visibleBots);
    }
    
    public class HuntState extends SaladState
    {
        private int _direction = 1;
        
        public HuntState()
        {
            System.out.println("Hunting time");
        }
        
        @Override
        public void act()
        {
            int turnAmount = (int) (Math.random() * 30.0) + 30;
            
            if (_direction > 0)
            {
                turnClockwise(turnAmount);
            }
            else
            {
                turnAntiClockwise(turnAmount);
            }
            
            moveForward(300);
        }

        @Override
        public void onIdle()
        {
            System.out.println("Changing direction");
            _direction = _direction * -1;
            act();
        }

        @Override
        public void robotVisible(List<VisibleRobot> visibleBots)
        {
            if (visibleBots.size() > 0)
            {
                System.out.println("Saw a prawn");

                VisibleRobot prey = visibleBots.get(0);

                setState(new KillState(prey));
            }
        }
    }
    
    public class KillState extends SaladState
    {
        private VisibleRobot _prey;
        
        public KillState(VisibleRobot prey)
        {
            _prey = prey;
            System.out.println("Time to kill");
        }
        
        @Override
        public void act()
        {
            System.out.println("Blood thirst");
            moveForward(50);
            turnClockwise(50);
        }

        @Override
        public void onIdle()
        {
            act();
        }

        @Override
        public void robotVisible(List<VisibleRobot> visibleBots)
        {
            boolean preyIsStillVisible = false;
            
            for (VisibleRobot v : visibleBots)
            {
                if (v.targetUUID.equals(_prey.targetUUID))
                {
                    preyIsStillVisible = true;
                    break;
                }
            }
            
            if (!preyIsStillVisible)
            {
                setState(new HuntState());
                return;
            }
            
            startTimer(50, new Runnable() {
                @Override
                public void run()
                {
                    clearActions();
                    target(_prey, 0.4f);
                    
                    startTimer(50, new Runnable() {
                        @Override
                        public void run()
                        {
                            clearActions();
                            act();
                        }
                    });
                }
            });
        }
    }
}
