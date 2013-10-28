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
 package afk.bot.london;

import afk.bot.RobotConfigManager;
import afk.ge.ems.Constants;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author daniel
 */
public class LondonRobotConfigManager implements RobotConfigManager
{
    private Map<UUID, Map<String, String>> config = new HashMap<UUID, Map<String, String>>();
    // flag that states if bots are still in initialisation phase
    private boolean init = true;

    @Override
    public String getProperty(UUID id, String name)
    {
        return getProperties(id).get(name);
    }

    @Override
    public void setProperty(UUID id, String name, String value)
    {
        if (!init)
        {
            throw new RuntimeException("Property set outside of init phase");
        }
        
        getProperties(id).put(name, value);
    }
    
    private Map<String, String> getProperties(UUID id)
    {
        Map<String, String> properties = config.get(id);
        if (properties == null)
        {
            properties = new HashMap<String, String>();
            config.put(id, properties);
        }
        return properties;
    }
    
    @Override
    public void initComplete()
    {
        init = false;
    }

    @Override
    public boolean isInitComplete()
    {
        return init;
    }

    @Override
    public Object getConstant(UUID id, String name)
    {
        String type = getProperty(id, "type");
        return Constants.getConstant(type, name);
    }
    
}
