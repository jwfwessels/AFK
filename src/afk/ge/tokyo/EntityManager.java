/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.gfx.GfxEntity;
import afk.london.London;
import afk.london.Robot;
import java.util.ArrayList;

/**
 *
 * @author Jw
 */
public class EntityManager
{

    public ArrayList<AbstractEntity> entities;
    public ArrayList<AbstractEntity> subEntities;

    public EntityManager()
    {
        this.entities = new ArrayList<AbstractEntity>();
        this.subEntities = new ArrayList<AbstractEntity>();
    }

    AbstractEntity createTank(GfxEntity gfxEntity)
    {
        //todo create and add to list
        AbstractEntity tank = new TankEntity(gfxEntity);
        entities.add(tank);
        return tank;
    }

    AbstractEntity createProjectile(GfxEntity gfxEntity)
    {
        //todo create and add to list
        AbstractEntity projectile = new ProjectileEntity(gfxEntity);
        subEntities.add(projectile);
        return projectile;
    }

    void updateEntities(float t, float delta)
    {
        //todo 
        ArrayList commands = getInputs();
        for (int i = 0; i < entities.size(); i++)
        {
//            boolean[] flags = (boolean[]) commands.get(i);
            entities.get(i).update(t, t, (boolean[]) commands.get(i));
        }
        for (int i = 0; i < subEntities.size(); i++)
        {
            subEntities.get(i).update(t, t, null);
        }

    }

    private ArrayList getInputs()
    {
        ArrayList<Robot> bots = London.getRobots();
        bots.get(0).run();
        boolean[] temp = bots.get(0).getActionFlags();
        boolean[] flags2 = new boolean[temp.length];
        System.arraycopy(temp, 0, flags2, 0, temp.length);
        bots.get(0).clearFlags();
        ArrayList tempFlags = new ArrayList();
        tempFlags.add(flags2);
        return tempFlags;
    }

    void renderEntities(float alpha)
    {
        //todo
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).render(alpha);
        }
        for (int i = 0; i < subEntities.size(); i++)
        {
            subEntities.get(i).render(alpha);
        }
    }
}
