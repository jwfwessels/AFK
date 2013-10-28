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
 package afk.ge.tokyo.ems.factories;

import afk.bot.RobotConfigManager;
import afk.ge.ems.Constants;
import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.ems.FactoryException;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.HUDImage;
import afk.ge.tokyo.ems.components.HUDTag;
import afk.ge.tokyo.ems.components.Paint;
import afk.ge.tokyo.ems.components.Spawn;
import afk.ge.tokyo.ems.components.TextLabel;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;
import java.util.UUID;

/**
 *
 * @author Daniel
 */
public class RobotFactory implements Factory<RobotFactoryRequest>
{
    private RobotConfigManager configManager;
    private GenericFactory factory;

    public RobotFactory(RobotConfigManager configManager, GenericFactory factory)
    {
        this.configManager = configManager;
        this.factory = factory;
    }

    @Override
    public Entity create(RobotFactoryRequest request)
    {
        Entity entity;
        UUID id = request.robot.getId();
        String type = configManager.getProperty(id, "type");
        try
        {
            entity = factory.create(GenericFactoryRequest.load(type));
        } catch (IOException ex)
        {
            throw new FactoryException(ex);
        }
        
        Constants.add(type,entity);
        
        entity.addComponent(new Spawn(request.spawn, Vec4.VEC4_ZERO));
        entity.addComponent(new TextLabel(configManager.getProperty(id, "name")));
        entity.addComponent(new HUDTag(0, 15, 0.5f, true, true));
        entity.addComponent(new HUDImage());
        entity.deepAddComponent(new Paint(request.colour));
        entity.deepAddComponent(new Controller(id));
        
        
        return entity;
    }
    
}
