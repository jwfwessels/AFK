/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.london;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
//import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.*;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Jessica
 */

public class RobotLoader extends ClassLoader
{
    private ArrayList<Class<?>> bots = new ArrayList<Class<?>>(); 
    private ArrayList<byte[]> fileHash = new ArrayList<byte[]>();
    private HashMap<String, Class<?>> botMap = new HashMap<String, Class<?>>();
    private String error = "";
    
    public Robot LoadRobot(String path)
    {
        if(path.endsWith(".class"))
        {
            return loadFromClass(path);
        }
        else if(path.endsWith(".jar"))
        {
            return loadFromJar(path);
        }
        else
        {
            error = path + " is not a valid Robot file";
            return null;
        }
        
    }
    
    private Robot loadFromClass(String path)
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
        //try
        //{
            if(!botMap.containsKey(name))
            {
                loadedBot = defineClass(null, b, 0, b.length);
                botMap.put(loadedBot.getName(), loadedBot);
                //fileHash.add(MessageDigest.getInstance("MD5").digest(b));
            }
            else /*if(fileHash.contains(MessageDigest.getInstance("MD5").digest(b)))*/
            {
                loadedBot = botMap.get(name);
                newBot = null;
            }
            /*else
            {
                error = "Class already in use: " + path;
                return null;
            }
        }
        catch(NoSuchAlgorithmException e)
        {
        }*/

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
                error = loadedBot.getDeclaredConstructor().getName() + " does not inherit from Robot";
                return null;
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
        return newBot;
    }
    
    //Only loads class files in root directory at present
    private Robot loadFromJar(String path)
    {
        Robot newBot = null;
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
                        // TODO: load class files nested in directories
                    }
                    else if(je.getName().endsWith(".class"))
                    {
                        InputStream in = jarFile.getInputStream(je);
                        try
                        {
                            System.out.println(je.getName());
                            byte[] b = new byte[(int)je.getSize()];
                            in.read(b);
                            Class<?> tempClass = null;
                            String name = je.getName().substring(0, je.getName().lastIndexOf('.'));
                            if(!botMap.containsKey(name))
                            {
                                tempClass = defineClass(null, b, 0, b.length);
                                botMap.put(tempClass.getName(), tempClass);
                                //fileHash.add(MessageDigest.getInstance("MD5").digest(b));
                            }
                            else /*if(fileHash.contains(MessageDigest.getInstance("MD5").digest(b)))*/
                            {
                                tempClass = botMap.get(name);
                                newBot = null;
                            }
                            Class stuff = tempClass.getSuperclass();
                            System.out.println("Length: " + tempClass.getSuperclass());
                            if(stuff.getName().equals("afk.london.Robot"))
                            {
                                System.out.println("I HAS A BOT :)");
                                Object obj = tempClass.getDeclaredConstructor().newInstance();
                                if(obj instanceof Robot)
                                {
                                    newBot = (Robot)obj;
                                }
                            }
                        }
                        catch(Exception ex)
                        {
                            error = "Could not load " + path;
                            return null;
                        }
                    }
            }
        }
        catch(Exception e)
        {
            error = "I don't know what has just happened";
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
