/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel
 */
public class RobotEvent
{
    
    /** List of visible bots as angles from where tank is facing. Empty if there are none. */
    protected List<Float> visibleBots;
    
    /** True if bot was hit by another. */
    public boolean gotHit; // TODO: change to reflect where hit came from
    
    /** True if bot successfully hit another. */
    public boolean didHit;
    
    /** true if bot drove into a wall. */
    public boolean hitWall;

    public RobotEvent()
    {
        visibleBots = new ArrayList<Float>();
        gotHit = false;
        didHit = false;
        hitWall = false;
    }

    public RobotEvent(List<Float> visibleBots, boolean gotHit, boolean didHit, boolean hitWall)
    {
        this.visibleBots = visibleBots;
        this.gotHit = gotHit;
        this.didHit = didHit;
        this.hitWall = hitWall;
    }
    
    public float[] getVisibleBots()
    {
        float[] v = new float[visibleBots.size()];
        for (int i = 0; i < v.length; i++)
            v[i] = visibleBots.get(i);
        return v;
    }

    public boolean gotHit()
    {
        return gotHit;
    }

    public boolean didHit()
    {
        return didHit;
    }

    public boolean hitWall()
    {
        return hitWall;
    }
    
}
