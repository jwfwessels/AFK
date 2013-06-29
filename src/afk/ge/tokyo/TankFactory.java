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
public class TankFactory extends EntityFactory

{

    @Override
    public AbstractEntity createEntity(GfxEntity gfxEntity, EntityManager entityManager)
    {
        TankEntity tank = new TankEntity(gfxEntity, entityManager);
        entities.add(tank);
        return tank;
    }
    
}
