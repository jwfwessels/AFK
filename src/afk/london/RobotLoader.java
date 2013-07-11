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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jessica
 */
public class RobotLoader extends ClassLoader
{
    ArrayList<Class<?>> bots = new ArrayList<Class<?>>(); 
    ArrayList<String> fileHash = new ArrayList<String>();
    HashMap<String, Class<?>> botMap = new HashMap<String, Class<?>>();
    String error = "";
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
        Robot newBot;
        Class<?> loadedBot = null;
        try
        {
            if(!botMap.containsKey(name))
            {
                loadedBot = defineClass(null, b, 0, b.length);
                botMap.put(loadedBot.getName(), loadedBot);
                fileHash.add(MessageDigest.getInstance("MD5").digest(b).toString());
            }
            else if(fileHash.contains(MessageDigest.getInstance("MD5").digest(b).toString()))
            {
                loadedBot = botMap.get(name);
                newBot = null;
            }
            else
            {
                error = "There is already a bot defined by the name: " + path;
                return null;
            }
        }
        catch(NoSuchAlgorithmException e)
        {
            
        }

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
                error = loadedBot.getDeclaredConstructor().getName() + " does not inherit from Robot";
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return newBot;
    }
    
    public String getError()
    {
        return error;         
    }
    
    public void clearMaps()
    {
        fileHash.clear();
        botMap.clear();
    }
}
