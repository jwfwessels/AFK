/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.gfx.ResourceNotLoadedException;
import afk.london.London;
import afk.london.Robot;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jw
 */
public class EntityManager
{

    public ArrayList<AbstractEntity> entities;
    private ArrayList<AbstractEntity> subEntities;
    private GraphicsEngine gfxEngine;
    Resource tankMesh;
    Resource tankShader;
    Resource floorMesh;
    Resource floorShader;

    public EntityManager()
    {
        entities = new ArrayList<AbstractEntity>();
        subEntities = new ArrayList<AbstractEntity>();
        //TODO; getinstance still needs to be refactored
        gfxEngine = GraphicsEngine.getInstance(0, 0, null, true);
    }

    public TankEntity createTank()
    {
        GfxEntity tankGfxEntity = gfxEngine.createEntity();
        try
        {
            gfxEngine.attachResource(tankGfxEntity, tankMesh);
            gfxEngine.attachResource(tankGfxEntity, tankShader);
        } catch (ResourceNotLoadedException ex)
        {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        TankEntity tank = new TankEntity(tankGfxEntity, this);
        entities.add(tank);
        return tank;
    }

    public ProjectileEntity createProjectile()
    {

        //dont have a projectile model yet, mini tank will be bullet XD
        GfxEntity projectileGfxEntity = gfxEngine.createEntity();
        try
        {
            gfxEngine.attachResource(projectileGfxEntity, tankMesh);
            gfxEngine.attachResource(projectileGfxEntity, tankShader);
            projectileGfxEntity.setScale(0.2f, 0.2f, 0.2f);
            projectileGfxEntity.setPosition(5, 10, 5);
        } catch (ResourceNotLoadedException ex)
        {
            Logger.getLogger(EntityManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        ProjectileEntity projectile = new ProjectileEntity(projectileGfxEntity, this);
        subEntities.add(projectile);
        return projectile;
    }

    void updateEntities(float t, float delta)
    {
        ArrayList commands = getInputs();
        for (int i = 0; i < entities.size(); i++)
        {
            boolean[] flags = (boolean[]) commands.get(i);
            entities.get(i).update(t, delta, (boolean[]) commands.get(i));
        }
        for (int i = 0; i < subEntities.size(); i++)
        {
            subEntities.get(i).update(t, delta, null);
        }

    }

    private ArrayList getInputs()
    {
        ArrayList tempFlags = new ArrayList();
        ArrayList<Robot> bots = London.getRobots();
        for (int i = 0; i < bots.size(); i++)
        {
            bots.get(i).run();
            boolean[] temp = bots.get(i).getActionFlags();
            boolean[] tempArr = new boolean[temp.length];
            System.arraycopy(temp, 0, tempArr, 0, temp.length);
            bots.get(i).clearFlags();
            tempFlags.add(tempArr);
        }
        return tempFlags;
    }

    void renderEntities(float alpha)
    {
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).render(alpha);
        }
        for (int i = 0; i < subEntities.size(); i++)
        {
            subEntities.get(i).render(alpha);
        }
    }

    protected boolean loadResources()
    {
        tankMesh = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "tank");
        tankShader = gfxEngine.loadResource(Resource.SHADER, "monkey");
        floorMesh = gfxEngine.loadResource(Resource.PRIMITIVE_MESH, "quad");
        floorShader = gfxEngine.loadResource(Resource.SHADER, "floor");

        gfxEngine.dispatchLoadQueue(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    GfxEntity floorGfxEntity = gfxEngine.createEntity();
                    gfxEngine.attachResource(floorGfxEntity, floorMesh);
                    gfxEngine.attachResource(floorGfxEntity, floorShader);
                    floorGfxEntity.setScale(50, 50, 50);

                    TankEntity tank = createTank();
                    TankEntity tank2 = createTank();
                    tank2.setState(new EntityState(new Vec3(0.0f, 0.0f, 10.0f)));
//                    ProjectileEntity bullet = (ProjectileEntity) createProjectile(projectileGfxEntity);//new ProjectileEntity(projectileGfxEntity);
//                  addEntity(tank);
//                    tank.setProjectileGfx(projectileGfxEntity);
//                  addEntity(bullet);

                } catch (ResourceNotLoadedException ex)
                {
                    //System.err.println("Failed to load resource: " + ex.getMessage());
                    throw new RuntimeException(ex);
                }


            }
        });
        return true;
    }
}
