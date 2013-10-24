package afk.ge.ems;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Stores the all the constants of an entity.
 *
 * @author daniel
 */
public class Constants
{

    private static final Map<String, Map<String, Object>> constants = new HashMap<String, Map<String, Object>>();

    /**
     * Add the values of the entity to the constants map.
     *
     * @param type the type to which the constants correspond.
     * @param entity the entity from which to get the values.
     */
    public static void add(String type, Entity entity)
    {
        Map<String, Object> map = constants.get(type);
        if (map == null)
        {
            map = new HashMap<String, Object>();
        }
        add(map, entity);

        for (Entity e : entity.getDependents())
        {
            add(map, e);
        }
        
        //// DEBUG
        System.out.println("##### CONSTANTS FOR [" + type + "] #####");
        for (Map.Entry<String,Object> e : map.entrySet())
        {
            System.out.println("-> " + e.getKey() + " := " + e.getValue());
        }
        System.out.println("#####");
        ////

        constants.put(type, map);
    }

    private static void add(Map<String, Object> map, Entity entity)
    {
        for (Object o : entity.getAllComponents())
        {
            try
            {
                Class clazz = o.getClass();
                String className = clazz.getSimpleName();

                for (Field field : clazz.getFields())
                {
                    String name = className + "." + field.getName();
                    if (map.containsKey(name))
                    {
                        continue;
                    }
                    map.put(name, field.get(o));
                }
            } catch (Exception ex)
            {
                ex.printStackTrace(System.err);
            }
        }
    }

    /**
     * Get the value of a constant.
     * @param type the type to which the constant corresponds.
     * @param name the name of the constant.
     * @return the value of the constant, null if either the type or the constant do not exist.
     */
    public static Object getConstant(String type, String name)
    {
        Map<String, Object> map = constants.get(type);
        if (map == null)
        {
            return null;
        }
        return map.get(name);
    }
}
