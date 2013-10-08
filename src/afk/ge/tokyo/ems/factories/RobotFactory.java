/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo.ems.factories;

import afk.bot.RobotConfigManager;
import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.ems.FactoryException;
import static afk.ge.tokyo.Tokyo.BOT_COLOURS;
import static afk.ge.tokyo.Tokyo.SPAWN_POINTS;
import afk.ge.tokyo.ems.components.Controller;
import afk.ge.tokyo.ems.components.Paint;
import afk.ge.tokyo.ems.components.Spawn;
import com.hackoeur.jglm.Vec4;
import java.io.IOException;

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
        try
        {
            entity = factory.create(GenericFactoryRequest.load(configManager.getProperty(request.id, "type")));
        } catch (IOException ex)
        {
            throw new FactoryException(ex);
        }
        entity.add(new Spawn(request.spawn, Vec4.VEC4_ZERO));
        entity.addToDependents(new Paint(request.colour));
        entity.addToDependents(new Controller(request.id));
        return entity;
    }
    
}
