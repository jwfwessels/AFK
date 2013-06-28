/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge;

import afk.ge.tokyo.AbstractEntity;
import afk.ge.tokyo.EntityManager;
import afk.gfx.GfxEntity;
import java.util.ArrayList;

/**
 *
 * @author jwfwessels
 */
public abstract class EntityFactory
{

    public static ArrayList<AbstractEntity> entities = new ArrayList<AbstractEntity>();

    public static ArrayList getEntityList()
    {
        return entities;
    }

    /**
     *
     * @param gfxEntity
     * @return
     */
    public abstract AbstractEntity createEntity(GfxEntity gfxEntity, EntityManager entityManager);
}
