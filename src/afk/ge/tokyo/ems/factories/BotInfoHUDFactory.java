package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.tokyo.ems.components.BotInfoHUD;
import afk.ge.tokyo.ems.components.HUD;
import afk.ge.tokyo.ems.components.HUDImage;

/**
 *
 * @author daniel
 */
public class BotInfoHUDFactory implements Factory<BotInfoHUDFactoryRequest>
{

    @Override
    public Entity create(BotInfoHUDFactoryRequest request)
    {
        Entity entity = new Entity();
        
        entity.addComponent(new HUD(request.top, request.right,
                request.bottom, request.left));
        entity.addComponent(new HUDImage());
        entity.addComponent(new BotInfoHUD(request.bot));
        
        return entity;
    }
    
}
