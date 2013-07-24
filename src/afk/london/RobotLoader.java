
package afk.london;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author Jessica
 */

public class RobotLoader extends ClassLoader
{
    private final String ROBOT_CLASS = "afk.london.Robot";
    
    private ArrayList<Class<?>> bots = new ArrayList<Class<?>>(); 
    //private ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
    //private ArrayList<byte[]> fileHash = new ArrayList<byte[]>();
    private int numBots = 0;
    private byte[] tempByteArray = null;
    private HashMap<String, Class<?>> classMap = new HashMap<String, Class<?>>();
    private String error = "";
    
    //public Robot LoadRobot(String path)
    //Loads all necessary classes needed for the robot
    public void AddRobot(String path)
    {
        error = "";
        if(path.endsWith(".class"))
        {
            loadFromClass(path);
        }
        else if(path.endsWith(".jar"))
        {
            loadFromJar(path);
        }
        else
        {
            error = path + " is not a valid file";
            return;
            //return null;
        }
        if(error.equals(""))
        {
            numBots++;
            error = "Successfully loaded";
        }
        
    }
    
    //private Robot loadFromClass(String path)
    private void loadFromClass(String path)
    {
        InputStream in = null;
        File tempFile = null;
        try
        {
            tempFile = new File(path);
            in = new FileInputStream(tempFile);
            tempByteArray = new byte[(int)tempFile.length()];
        }
        catch(FileNotFoundException e)
        {
            error = "Could not find file " + path;
            return;
        }
        
        loadClass(in, tempFile.getName());
    }
    
    //Only loads class files in root directory at present
    //private Robot loadFromJar(String path)
    private void loadFromJar(String path)
    {
        try
        {
            JarFile jarFile = new JarFile(path);       
            Enumeration e = jarFile.entries();
            URL[] urls = 
            { 
                new URL("jar:file:" + path + "!/") 
            };
            URLClassLoader cl = URLClassLoader.newInstance(urls);

            while(e.hasMoreElements()) 
            {
                    JarEntry je = (JarEntry)e.nextElement();
                    if(je.isDirectory())
                    {
                        System.out.println("Directory: " + je.getName());
                        // TODO: Load nested files
                    }
                    else if(je.getName().endsWith(".class"))
                    {
                        //TODO: Check for multiple Robot classes within a jar - report on this or only use the first one found - prevent cheating
                        InputStream in = jarFile.getInputStream(je);
                        String name = je.getName().substring(0, je.getName().lastIndexOf('.'));
                        loadClass(in, name);
                      }
                   /* else if(je.getName().endsWith(".jar"))
                    {
                        //TODO: Load nested jars to allow use of libraries etc.
                    }*/
            }
        }
        catch(Exception e)
        {
            error = "I don't know what has just happened. But something went wrong.";
        }
    }
    
    public void loadClass(InputStream in, String name)
    {    
        Class loadedClass = null;

        if(!classMap.containsKey(name))
        {
            loadedClass = defineClass(null, tempByteArray, 0, tempByteArray.length);
            classMap.put(loadedClass.getName(), loadedClass);
        }
    }
    
    //Returns instances of robots that are in classMap - to be used when game is started
    public Robot[] getBots()
    {
        Robot[] bots = new Robot[numBots];
        int x = 0;
        for(Map.Entry entryPair : classMap.entrySet()) 
        {
            Class<?> tempClass = (Class<?>)entryPair.getValue();
            Class superClass = tempClass.getSuperclass();
            Object obj = null;
            if(superClass.getName() == ROBOT_CLASS)
            {
                try
                {
                    obj = tempClass.getDeclaredConstructor().newInstance();
                }
                catch(Exception e)
                {
                    error = "Some error occurred";
                }
                // TODO: Catch exceptions individually
               /* catch(NoSuchMethodException e)
                {
                    error = "Failed to use constructor of robot " + entryPair.getKey();
                    return null;
                    
                }
                catch(InstantiationException e)
                {
                    error = "Failed to instantiate robot " + entryPair.getKey();
                    return null;
                }*/
                
                bots[x] = (Robot)obj;
                x++;
            }
        } 

        return bots;
    }
    
    public String getError()
    {
        return error;         
    }
    
    public void clearMaps()
    {
        //fileHash.clear();
        classMap.clear();
    }
}
