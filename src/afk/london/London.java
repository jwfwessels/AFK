/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

import java.util.ArrayList;

/**
 *
 * @author Jessica
 */
public class London
{

    private static ArrayList<Robot> robots = new ArrayList<Robot>();

    private London()
    {
    }

    public static void registerBot(Robot bot)
    {
        robots.add(bot);
    }

    public static ArrayList<Robot> getRobots()
    {
        return robots;
//        return (ArrayList<Robot>)robots.clone();
    }
}
