/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;

/**
 *
 * @author Jessica
 */
public class RobotLoader extends ClassLoader
{
    public Robot LoadRobot(String path)
    {
        FileInputStream in;
        File tempFile;
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

        Class<?> loadedBot = defineClass(null, b, 0, b.length);
        Robot newBot = null;
        
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
