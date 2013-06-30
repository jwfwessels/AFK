/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class RobotEvent
{
    
    /** List of visible bots as angles from where tank is facing. Empty if there are none. */
    protected ArrayList<Float> visibleBots;
    
    /** True if bot was hit by another. */
    protected boolean gotHit; // TODO: change to reflect where hit came from
    
    /** True if bot successfully hit another. */
    protected boolean hit;
    
    /** true if bot drove into a wall. */
    protected boolean hitWall;

    public RobotEvent(ArrayList<Float> visibleBots, boolean gotHit, boolean hit, boolean hitWall)
    {
        this.visibleBots = visibleBots;
        this.gotHit = gotHit;
        this.hit = hit;
        this.hitWall = hitWall;
    }
    
}
