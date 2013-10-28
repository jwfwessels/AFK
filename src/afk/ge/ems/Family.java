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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author daniel
 */
public class Family
{
    Class nodeClass;
    Map<Class, Field> components = new HashMap<Class, Field>();
    List<Node> nodes = new ArrayList<Node>();
    Map<Entity, Node> entities = new HashMap<Entity, Node>();

    public Family(Class nodeClass)
    {
        System.out.println("creating:\t" + nodeClass.getSimpleName() + " family");
        this.nodeClass = nodeClass;
        
        Field[] fields = nodeClass.getFields();
        for (Field field : fields)
        {
            Class fieldType = field.getType();
            
            // FIXME: use a better way (like "transient" or something) to distinguish non-components
            if (fieldType == Entity.class)
                continue;
            System.out.println("\tputting:\t" + fieldType.getSimpleName() + " " + field.getName());
            components.put(field.getType(), field);
        }
        System.out.println("");
    }
    
    public List getNodeList()
    {
        return nodes;
    }
    
    public void newEntity(Entity entity)
    {
        addIfMatch(entity);
    }
    
    public void componentAddedToEntity(Entity entity, Class componentClass)
    {
        addIfMatch(entity);
    }
    
    public void componentRemovedFromEntity(Entity entity, Class componentClass)
    {
        if (components.containsKey(componentClass))
            removeIfMatch(entity);
    }
    
    public void removeEntity(Entity entity)
    {
        removeIfMatch(entity);
    }
    
    private void addIfMatch(Entity entity)
    {
        if (!entities.containsKey(entity))
        {
            for (Class componentClass : components.keySet())
            {
                if (!entity.hasComponent(componentClass))
                    return;
            }
            
            try
            {
                Node node = (Node)nodeClass.newInstance(); 

                for (Map.Entry<Class, Field> e : components.entrySet())
                {
                    e.getValue().set(node, entity.getComponent(e.getKey()));
                }
                
                node.entity = entity;
                entities.put(entity, node);
                nodes.add(node);
            }
            catch (ReflectiveOperationException ex)
            {
                ex.printStackTrace(System.err);
            }
        }
    }
    
    private void removeIfMatch(Entity entity)
    {
        Node node = entities.get(entity);
        if (node != null)
        {
            entities.remove(entity);
            nodes.remove(node);
        }
    }
}
