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
