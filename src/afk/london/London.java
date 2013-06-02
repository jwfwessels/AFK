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
    
    public static void registerBot(Robot bot)
    {
        robots.add(bot);
    }
    
    public static ArrayList<Robot> getRobots()
    {
        return (ArrayList<Robot>)robots.clone();
    }
}
