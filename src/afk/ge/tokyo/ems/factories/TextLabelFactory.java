package afk.ge.tokyo.ems.factories;

import afk.ge.ems.Entity;
import afk.ge.ems.Factory;
import afk.ge.tokyo.ems.components.HUD;
import afk.ge.tokyo.ems.components.HUDImage;
import afk.ge.tokyo.ems.components.TextLabel;

/**
 *
 * @author daniel
 */
public class TextLabelFactory implements Factory<TextLabelFactoryRequest>
{

    @Override
    public Entity create(TextLabelFactoryRequest request)
    {
        Entity entity = new Entity("textlabel");
        
        entity.addComponent(new HUDImage());
        entity.addComponent(new TextLabel(request.text));
        entity.addComponent(new HUD(request.x, request.y));
        
        return entity;
    }
    
}
