/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.bot.london;

import afk.bot.Attackable;
import afk.bot.Aimable;

/**
 *
 * @author Jw
 */
public class RobotUtils
{

    public static void target(Aimable aimable, Attackable attackable, VisibleRobot target, RobotEvent events, float give)
    {
        float bearing = target.bearing;
        float elevation = target.elevation - events.barrel;
        float diff = bearing * bearing + elevation * elevation;

        if (Float.compare(diff, give * give) < 0)
        {
            attackable.attack();
            return;
        }

        if (Float.compare(bearing, 0) < 0)
        {
            aimable.aimAntiClockwise(1);
        }
        if (Float.compare(bearing, 0) > 0)
        {
            aimable.aimClockwise(1);
        }

        if (Float.compare(elevation, 0) < 0)
        {
            aimable.aimDown(1);
        }
        if (Float.compare(elevation, 0) > 0)
        {
            aimable.aimUp(1);
        }
    }
}
