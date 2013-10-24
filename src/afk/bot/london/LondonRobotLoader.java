package afk.bot.london;

import afk.bot.Robot;
import afk.bot.RobotLoader;
import afk.bot.RobotException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.jar.JarFile;
import java.util.jar.JarEntry;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.Map;

/**
 *
 * @author Jessica
 */
public class LondonRobotLoader extends URLClassLoader implements RobotLoader<AbstractRobot>
{

    private final Class ROBOT_CLASS = AbstractRobot.class;
    private byte[] tempByteArray = null;
    private Map<String, Class<?>> robotMap = new HashMap<String, Class<?>>();
    private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
    private boolean robotExists;
    
    private static final URLClassLoader SystemLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();

    public LondonRobotLoader()
    {
        //this(SystemLoader.getURLs());
        this(new URL[] {});
    }

    public LondonRobotLoader(URL[] urls)
    {
        this(urls, null);
    }

    public LondonRobotLoader(ClassLoader parent)
    {
        //this(SystemLoader.getURLs(), parent);
        this(new URL[] {}, parent);
    }

    public LondonRobotLoader(URL[] urls, ClassLoader parent)
    {
        super(urls, parent);
    }

    //Loads all necessary classes needed for the robot specified by path
    @Override
    public void loadRobot(String path) throws RobotException
    {
        if (path.endsWith(".class"))
        {
            robotExists = false;
            loadFromClass(path);
            if (!robotExists)
            {
                throw new RobotException(path + " is not a valid Robot class");
            }
        } else if (path.endsWith(".jar"))
        {
            robotExists = false;
            loadJar(path);
            if (!robotExists)
            {
                throw new RobotException("No robot found. " + path + " must contain a class implementing Robot");
            }
        } else
        {
            throw new RobotException(path + "Is not a valid file format");
        }
    }

    private void loadFromClass(String path) throws RobotException
    {
        InputStream in;
        File tempFile;
        try
        {
            tempFile = new File(path);
            in = new FileInputStream(tempFile);
            tempByteArray = new byte[(int) tempFile.length()];
            addURL(tempFile.toURI().toURL());
            loadClass(in, tempFile.getName().substring(0, tempFile.getName().lastIndexOf('.')));
        } catch (FileNotFoundException e)
        {
            throw new RobotException("Could not fine file " + path);
        } catch (MalformedURLException ex)
        {
        }
    }

    private void loadJar(String path) throws RobotException
    {
        try
        {
            JarFile jarFile = new JarFile(path);
            Enumeration e = jarFile.entries();

            while (e.hasMoreElements())
            {
                JarEntry je = (JarEntry) e.nextElement();
                tempByteArray = new byte[(int) je.getSize()];
                if (je.isDirectory())
                {
                    continue;
                } else if (je.getName().endsWith(".class"))
                {
                    //TODO: Check for multiple Robot classes within a jar - report on this or only use the first one found - prevent cheating
                    InputStream in = jarFile.getInputStream(je);
                    String name = je.getName().substring(0, je.getName().lastIndexOf('.'));
                    loadClass(in, name);
                }
                /*else if(je.getName().endsWith(".jar"))
                 {
                 //TODO: Load nested jars to allow use of libraries etc.
                 }*/
            }
        } catch (Exception e)
        {
            throw new RobotException("An error ocurred while loading jar file " + path, e);
        }
    }

    private void loadClass(InputStream in, String name) throws RobotException
    {
        if (!classMap.containsKey(name))
        {
//            int dollar = name.indexOf("$");
//            if (dollar != -1)
//                name = name.substring(dollar+1);
            System.out.println("Loading class: " + name);
            try
            {
                in.read(tempByteArray);
            } catch (Exception e)
            {
                throw new RobotException("???");
            }
            System.out.println(tempByteArray.toString());
            System.out.println("File read, defining class");
            
            Class loadedClass = null;
            try
            {
                //loadedClass = defineClass(null, tempByteArray, 0, tempByteArray.length);
                loadedClass = super.loadClass(name, robotExists);
            }
            catch (ClassNotFoundException e)
            {
                System.out.println("ERROR MAN: " + e.getMessage());
                throw new RobotException("An error ocurred while loading class file " + name, e);
            }
            catch (Error e)
            {
                System.out.println("ERROR MAN: " + e.getMessage());
                throw new RobotException("An error ocurred while loading class file " + name, e);
            }
            
            System.out.println("Class defined, checking for bots");
            
            if (loadedClass != null)
            {
                System.out.println("$$$$ Class name: " + loadedClass.getName());
                
                classMap.put(loadedClass.getName(), loadedClass);
                if (hasSuperClass(loadedClass, ROBOT_CLASS))
                {
                    System.out.println("It is a bot");
                    robotExists = true;
                    robotMap.put(name, loadedClass);
                    System.out.println("loaded class: " + loadedClass.getName());
                }
            }
            else
            {
                System.out.println("defineClass() did not work so great man");
            }
        }
    }

    private boolean hasSuperClass(Class clazz, Class superClazz)
    {
        clazz = clazz.getSuperclass();
        while (clazz != null && !clazz.equals(Object.class))
        {
            if (clazz.equals(superClazz))
            {
                return true;
            }
            clazz = clazz.getSuperclass();
        }
        return false;
    }

    //Returns instances of robots that are in robotClasses - to be used when game is started
    @Override
    public AbstractRobot getRobotInstance(String name) throws RobotException
    {
        Class tempClass = robotMap.get(name);

        Object obj = null;
        try
        {
            obj = (tempClass.getDeclaredConstructor().newInstance());
            System.out.println("obj: " + obj.toString());
        } catch (Exception e)
        {
            throw new RobotException("Failed to create instance of " + name, e);
        }

        return (AbstractRobot) obj;
    }

    @Override
    public void clearMaps()
    {
        classMap.clear();
        robotMap.clear();

        //Hint to garbage collector
        System.gc();
        System.gc();
    }
}
