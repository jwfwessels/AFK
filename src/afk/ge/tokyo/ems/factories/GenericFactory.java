/*
 * Copyright (c) 2013 Triforce - in association with the University of Pretoria and Epi-Use <Advance/>
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
 package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.ems.FactoryException;
import afk.ge.tokyo.ems.components.Parent;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.jogamp.graph.geom.AABBox;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * 
 * 
 * @author Daniel
 */
public class GenericFactory implements Factory<GenericFactoryRequest>
{

    @Override
    public Entity create(GenericFactoryRequest request)
    {
        Entity entity = new Entity(request.name);
        for (Map.Entry<Class, Map<String, String>> e : request.components.entrySet())
        {
            try
            {
                Object component = e.getKey().newInstance();
                for (Map.Entry<String, String> e2 : e.getValue().entrySet())
                {
                    Field field = e.getKey().getField(e2.getKey());
                    Class fieldClass = field.getType();
                    if (fieldClass == Vec3.class)
                    {
                        field.set(component, parseVec3(e2.getValue()));
                    } else if (fieldClass == Vec4.class)
                    {
                        field.set(component, parseVec4(e2.getValue()));
                    } else if (fieldClass == AABBox.class)
                    {
                        field.set(component, parseAABBox(e2.getValue()));
                    } else if (fieldClass == String.class)
                    {
                        field.set(component, e2.getValue());
                    } else
                    {
                        field.set(component, getWrapperClass(fieldClass).getMethod("valueOf", String.class).invoke(null, e2.getValue()));
                    }
                }
                entity.addComponent(component);
            }
            catch (ReflectiveOperationException ex)
            {
                throw new FactoryException(ex);
            }
        }
        for (GenericFactoryRequest r : request.dependents)
        {
            Entity dep = create(r);
            dep.addComponent(new Parent(entity));
            entity.addDependent(dep);
        }
        return entity;
    }
    
    private static Class getWrapperClass(Class clazz)
    {
        if (clazz == int.class)
        {
            return Integer.class;
        }
        if (clazz == short.class)
        {
            return Short.class;
        }
        if (clazz == byte.class)
        {
            return Byte.class;
        }
        if (clazz == long.class)
        {
            return Long.class;
        }
        if (clazz == float.class)
        {
            return Float.class;
        }
        if (clazz == double.class)
        {
            return Double.class;
        }
        if (clazz == boolean.class)
        {
            return Boolean.class;
        }
        if (clazz == char.class)
        {
            return Character.class;
        }
        System.out.println("WARNING: unknown primitive: " + clazz);
        return Object.class;
    }
    
    private static Vec3 parseVec3(String values)
    {
        String[] split = values.split("\\s+");

        if (split.length != 3)
        {
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 3, found " + split.length);
        }

        return new Vec3(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2]));
    }
    
    private static Vec4 parseVec4(String values)
    {
        String[] split = values.split("\\s+");

        if (split.length != 4)
        {
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 4, found " + split.length);
        }

        return new Vec4(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2]),
                Float.parseFloat(split[3]));
    }

    private static AABBox parseAABBox(String values)
    {
        String[] split = values.split("\\s+");

        if (split.length != 6)
        {
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 6, found " + split.length);
        }

        return new AABBox(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2]),
                Float.parseFloat(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5]));
    }
    
}
