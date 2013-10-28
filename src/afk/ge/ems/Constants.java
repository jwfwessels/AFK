/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
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

        //// DEBUG
        System.out.println("##### CONSTANTS FOR [" + type + "] #####");
        for (Map.Entry<String, Object> e : map.entrySet())
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

            for (Entity e : entity.getDependents())
            {
                add(map, e);
            }

        }
    }

    /**
     * Get the value of a constant.
     *
     * @param type the type to which the constant corresponds.
     * @param name the name of the constant.
     * @return the value of the constant, null if either the type or the
     * constant do not exist.
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
