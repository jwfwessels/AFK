/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.factories;

import afk.bot.RobotConfigManager;
import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.ems.FactoryException;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.ImageComponent;
import afk.ge.tokyo.ems.components.Paint;
import afk.ge.tokyo.ems.components.Spawn;
import afk.ge.tokyo.ems.components.TextLabel;
import com.hackoeur.jglm.Vec4;
import java.awt.image.BufferedImage;
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
        try
        {
            entity = factory.create(GenericFactoryRequest.load(configManager.getProperty(id, "type")));
        } catch (IOException ex)
        {
            throw new FactoryException(ex);
        }
        entity.add(new Spawn(request.spawn, Vec4.VEC4_ZERO));
        entity.add(new TextLabel(configManager.getProperty(id, "name")));
        entity.add(new ImageComponent());
        entity.addToDependents(new Paint(request.colour));
        entity.addToDependents(new Controller(id));
        return entity;
    }
    
}
