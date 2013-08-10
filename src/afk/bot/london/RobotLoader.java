
package afk.bot.london;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;

/**
 *
 * @author Jessica
 */

public class RobotLoader extends ClassLoader
{
    private final String ROBOT_CLASS = "afk.bot.london.Robot";
    
    private byte[] tempByteArray = null;
    private Map<String, Class<?>> robotMap = new HashMap<String, Class<?>>();
    private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
    private boolean robotExists;
    
    //Loads all necessary classes needed for the robot specified by path
    public void addRobot(String path)
    {
        if(path.endsWith(".class"))
        {
            robotExists = false;
            loadFromClass(path);
            if(!robotExists)
            {
                //TODO: throw RobotException
            }
        }
        else if(path.endsWith(".jar"))
        {
            robotExists = false;
            loadJar(path);
            if(!robotExists)
            {
                //TODO: throw RobotException
            }
        }
        else
        {
            // TODO: throw RobotException
        }
    }
    
    private void loadFromClass(String path)
    {
        InputStream in;
        File tempFile;
        try
        {
            tempFile = new File(path);
            in = new FileInputStream(tempFile);
            tempByteArray = new byte[(int)tempFile.length()];
        }
        catch(FileNotFoundException e)
        {
            //TODO: throw RobotException
            e.printStackTrace();
            return;
        }
        loadClass(in, tempFile.getName().substring(0, tempFile.getName().lastIndexOf('.')));
    }
    
    //Only loads class files in root directory at present
    //private Robot loadFromJar(String path)
    private void loadJar(String path)
    {
        try
        {
            JarFile jarFile = new JarFile(path);       
            Enumeration e = jarFile.entries();
            
            while(e.hasMoreElements()) 
            {
                    JarEntry je = (JarEntry)e.nextElement();
                    tempByteArray = new byte[(int)je.getSize()];
                    System.out.println("Jar Entry :)" + je.getName());
                    if(je.isDirectory())
                    {
                        continue;
                    }
                    else if(je.getName().endsWith(".class"))
                    {
                        //TODO: Check for multiple Robot classes within a jar - report on this or only use the first one found - prevent cheating
                        InputStream in = jarFile.getInputStream(je);
                        String name = je.getName().substring(0, je.getName().lastIndexOf('.'));
                        loadClass(in, name);
                    }
                    /*else if(je.getName().endsWith(".jar"))
                    {
                        System.out.println("Jar" + je.getName());
                        //TODO: Load nested jars to allow use of libraries etc.
                    }*/
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            //TODO: throw RobotException
        }
    }
    
    public void loadClass(InputStream in, String name)
    {    
        if(!classMap.containsKey(name))
        {
            System.out.println("Loading class: " + name);
            try
            {
                in.read(tempByteArray);
            }
            catch(Exception e)
            {
                e.printStackTrace();
                  //TODO: throw Robot Exception      
            }
            System.out.println(tempByteArray.toString());
            Class loadedClass = defineClass(null, tempByteArray, 0, tempByteArray.length);
            classMap.put(loadedClass.getName(), loadedClass);
            if(loadedClass.getSuperclass() != null && loadedClass.getSuperclass().getSuperclass() != null)
            {
                if(loadedClass.getSuperclass().getSuperclass().getName().equals(ROBOT_CLASS))
                {
                    System.out.println("It is a bot");
                    robotExists = true;
                    robotMap.put(name, loadedClass);
                    System.out.println("loaded class: " + loadedClass.getName());
                }
            }
        }
    }
    
    //Returns instances of robots that are in robotClasses - to be used when game is started
    public Robot getRobotInstance(String name)
    {
        Class tempClass = robotMap.get(name);
        
        Object obj = null;
        try
        {
            obj = (tempClass.getDeclaredConstructor().newInstance());
            System.out.println("obj: " + obj.toString());
        }
        catch(Exception e)
        {
            //TODO: throw RobotException
            e.printStackTrace();
        }
        
        return (Robot)obj;
    }
    
    public void clearMaps()
    {
        classMap.clear();
        robotMap.clear();
        
        //Hint to garbage collector
        System.gc();
        System.gc();
    }
}
