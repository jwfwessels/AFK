/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.EntityFactory;
import afk.gfx.GfxEntity;

/**
 *
 * @author jwfwessels
 */
public class ProjectileFactory extends EntityFactory

{

    @Override
    public AbstractEntity createEntity(GfxEntity gfxEntity)
    {
        ProjectileEntity bullet = new ProjectileEntity(gfxEntity);
        entities.add(bullet);
        return bullet;
    }
    
}
