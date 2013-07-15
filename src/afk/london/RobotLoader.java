/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Jessica
 */
public class RobotLoader extends ClassLoader
{
    ArrayList<Class<?>> bots = new ArrayList<Class<?>>(); 
    HashMap<String, Class<?>> botMap = new HashMap<String, Class<?>>();
    public Robot LoadRobot(String path)
    {
        FileInputStream in;
        File tempFile = null;
        byte[] b = null;
        
        try
        {
            tempFile = new File(path);
            b = new byte[(int)tempFile.length()];
            in = new FileInputStream(tempFile);
            in.read(b);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        String name = tempFile.getName();
        name = name.substring(0, name.lastIndexOf('.'));
        System.out.println("Path: " + path);
        System.out.println("Bot Name1: ");
        Robot newBot;
        Class<?> loadedBot;
        if(!botMap.containsKey(name))
        {
            loadedBot = defineClass(null, b, 0, b.length);
            botMap.put(loadedBot.getName(), loadedBot);
        }
        else
        {
            loadedBot = botMap.get(name);
            newBot = null;
        }
        System.out.println("Bot Name2: " + loadedBot.getName());
        newBot = null;

        try
        {
            Object obj = loadedBot.getDeclaredConstructor().newInstance();
            if(obj instanceof Robot)
            {
                newBot = (Robot)obj;
            }
            else
            {
                //TODO: Proper error reporting
                System.out.println("Not of type Robot");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return newBot;
    }
}
