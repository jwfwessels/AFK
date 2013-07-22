/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.AbstractEntity;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.london.RobotClasses.LargeTank;
import afk.london.London;
import afk.london.Robot;
import afk.london.RobotClasses.SmallTank;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class EntityManager
{

    public ArrayList<TankEntity> entities;
    private ArrayList<AbstractEntity> subEntities;
    private GraphicsEngine gfxEngine;
    Resource tankMesh;
    Resource tankShader;
    Resource smallTankBody;
    Resource smallTankBarrel;
    Resource smallTankTracks;
    Resource smallTankWheels;
    Resource smallTankBodyTex;
    Resource smallTankBarrelTex;
    Resource smallTankTracksTex;
    Resource smallTankWheelsTex;
    Resource smallTankShadowTex;
    Resource floorMesh;
    Resource floorShader;
    Resource explosionProjectile;
    Resource explosionTank;
    Resource particleShader;
    Resource billboardMesh;
    Resource simpleShadowShader;
    Resource explosionTexture;
    London botEngine;
    protected AtomicBoolean loaded = new AtomicBoolean(false);
    private static final Vec3[] BOT_COLOURS =
    {
        new Vec3(1, 0, 0),
        new Vec3(0, 0, 1),
        new Vec3(0, 1, 0),
        new Vec3(1, 1, 0),
        new Vec3(1, 0, 1),
        new Vec3(0, 1, 1),
        new Vec3(0.6f, 0.6f, 0.6f),
    };
    private static Vec3[] SPAWN_POINTS =
    {
        new Vec3(-20, 0, -20),
        new Vec3(20, 0, 20),
        new Vec3(-20, 0, 20),
        new Vec3(20, 0, -20),
        new Vec3(-20, 0, 0),
        new Vec3(0, 0, -20),
        new Vec3(20, 0, 0)
    };

    public EntityManager(London botEngine, GraphicsEngine gfxEngine)
    {
        this.botEngine = botEngine;
        this.gfxEngine = gfxEngine;
        entities = new ArrayList<TankEntity>();
        subEntities = new ArrayList<AbstractEntity>();
    }

    void createBots()
    {
        ArrayList<Robot> bots = botEngine.getRobots();
        for (int i = 0; i < bots.size(); i++)
        {
            createEntity(bots.get(i), SPAWN_POINTS[i], BOT_COLOURS[i]);
        }
    }

    public void createEntity(Robot botController, Vec3 spawnPoint, Vec3 colour)
    {
        if (LargeTank.class.isInstance(botController))
        {
            System.out.println("type LargeTank");
            createLargeTank(botController, spawnPoint, colour);
        }
        if (SmallTank.class.isInstance(botController))
        {
            System.out.println("type SmallTank");
            createSmallTank(botController, spawnPoint, colour);
        }
    }

    public TankEntity createSmallTank(Robot botController, Vec3 spawnPoint, Vec3 colour)
    {
        float TOTAL_LIFE = 8;
        float SCALE = 2.0f;
        GfxEntity tankGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankBarrelEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankTracksEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankWheelsEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankShadowEntity = gfxEngine.createEntity(GfxEntity.NORMAL);

        tankGfxEntity.attachResource(smallTankBody);
        tankGfxEntity.attachResource(smallTankBodyTex);
        tankGfxEntity.attachResource(tankShader);
        tankGfxEntity.setScale(SCALE, SCALE, SCALE);
        tankGfxEntity.colour = colour;

        tankBarrelEntity.attachResource(smallTankBarrel);
        tankBarrelEntity.attachResource(smallTankBarrelTex);
        tankBarrelEntity.attachResource(tankShader);
        tankBarrelEntity.setPosition(0.0f, 0.41522f, 0.28351f);
        tankBarrelEntity.colour = colour;

        tankTracksEntity.attachResource(smallTankTracks);
        tankTracksEntity.attachResource(smallTankTracksTex);
        tankTracksEntity.attachResource(tankShader);
        tankTracksEntity.colour = new Vec3(0.4f, 0.4f, 0.4f);

        tankWheelsEntity.attachResource(smallTankWheels);
        tankWheelsEntity.attachResource(smallTankWheelsTex);
        tankWheelsEntity.attachResource(tankShader);
        tankWheelsEntity.colour = colour;

        tankShadowEntity.attachResource(floorMesh);
        tankShadowEntity.attachResource(simpleShadowShader);
        tankShadowEntity.attachResource(smallTankShadowTex);
        tankShadowEntity.colour = Vec3.VEC3_ZERO;
        tankShadowEntity.opacity = 0.99f;
        tankShadowEntity.yMove = 0.01f;
        tankShadowEntity.xScale = tankShadowEntity.zScale = 1.5f;

        tankGfxEntity.setPosition(spawnPoint);
        tankGfxEntity.addChild(tankBarrelEntity);
        tankGfxEntity.addChild(tankTracksEntity);
        tankGfxEntity.addChild(tankWheelsEntity);
        tankGfxEntity.addChild(tankShadowEntity);

        gfxEngine.getRootEntity().addChild(tankGfxEntity);

        TankEntity tank = new TankEntity(botController, tankGfxEntity, this, TOTAL_LIFE);
        entities.add(tank);
        tank.name = "tank" + (entities.size() - 1);
        return tank;
    }

    public TankEntity createLargeTank(Robot botController, Vec3 spawnPoint, Vec3 colour)
    {
        float TOTAL_LIFE = 10;
        float SCALE = 2.0f;
        GfxEntity tankGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankShadowEntity = gfxEngine.createEntity(GfxEntity.NORMAL);

        tankGfxEntity.attachResource(tankMesh);
        tankGfxEntity.attachResource(tankShader);
        tankGfxEntity.setScale(SCALE, SCALE, SCALE);

        tankShadowEntity.attachResource(floorMesh);
        tankShadowEntity.attachResource(simpleShadowShader);
        tankShadowEntity.colour = Vec3.VEC3_ZERO;
        tankShadowEntity.opacity = 0.7f;
        tankShadowEntity.yMove = 0.01f;
        tankShadowEntity.xScale = tankShadowEntity.zScale = 2.7f;

        tankGfxEntity.colour = colour;
        tankGfxEntity.setPosition(spawnPoint);
        tankGfxEntity.addChild(tankShadowEntity);

        gfxEngine.getRootEntity().addChild(tankGfxEntity);

        TankEntity tank = new TankEntity(botController, tankGfxEntity, this, TOTAL_LIFE);
        entities.add(tank);
        tank.name = "tank" + (entities.size() - 1);
        return tank;
    }

    public ProjectileEntity createProjectile(AbstractEntity parent)
    {

        float DAMAGE = 1.5f;
        //dont have a projectile model yet, mini tank will be bullet XD
        GfxEntity projectileGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);

        projectileGfxEntity.attachResource(smallTankBody);
        projectileGfxEntity.attachResource(tankShader);

        projectileGfxEntity.setScale(0.1f, 0.1f, 0.1f);
        projectileGfxEntity.setPosition(5, 10, 5);

        gfxEngine.getRootEntity().addChild(projectileGfxEntity);
        ProjectileEntity projectile = new ProjectileEntity(projectileGfxEntity, this, parent, DAMAGE);
        subEntities.add(projectile);
        projectile.name = parent.name + "projectile";
        return projectile;
    }

    void updateEntities(float t, float delta)
    {
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).update(t, delta);
        }
        for (int i = 0; i < subEntities.size(); i++)
        {
            subEntities.get(i).update(t, delta);
        }
    }

    void renderEntities(double alpha)
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

    protected void loadResources()
    {
        tankMesh = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "tank");
        smallTankBody = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "small_tank_body");
        smallTankBarrel = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "small_tank_barrel");
        smallTankTracks = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "small_tank_tracks");
        smallTankWheels = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "small_tank_wheels");
        smallTankBodyTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/small_tank_body");
        smallTankBarrelTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/small_tank_barrel");
        smallTankTracksTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/small_tank_tracks");
        smallTankWheelsTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/small_tank_wheels");
        smallTankShadowTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/small_tank_shadow");
        explosionTexture = gfxEngine.loadResource(Resource.TEXTURE_2D, "explosion");
        tankShader = gfxEngine.loadResource(Resource.SHADER, "monkey");
        floorMesh = gfxEngine.loadResource(Resource.PRIMITIVE_MESH, "quad");
        floorShader = gfxEngine.loadResource(Resource.SHADER, "floor");

        explosionProjectile = gfxEngine.loadResource(Resource.PARTICLE_PARAMETERS, "explosionProjectile");
        explosionTank = gfxEngine.loadResource(Resource.PARTICLE_PARAMETERS, "explosionTank");
        particleShader = gfxEngine.loadResource(Resource.SHADER, "texturedParticle");
        billboardMesh = gfxEngine.loadResource(Resource.PRIMITIVE_MESH, "billboard");
        simpleShadowShader = gfxEngine.loadResource(Resource.SHADER, "simpleshadow");
        gfxEngine.dispatchLoadQueue(new Runnable()
        {
            @Override
            public void run()
            {

                GfxEntity floorGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
                floorGfxEntity.attachResource(floorMesh);
                floorGfxEntity.attachResource(floorShader);
                floorGfxEntity.setScale(
                        Tokyo.BOARD_SIZE,
                        Tokyo.BOARD_SIZE,
                        Tokyo.BOARD_SIZE);
                gfxEngine.getRootEntity().addChild(floorGfxEntity);

                loaded.set(true);
            }
        });

    }

    void RomoveSubEntity(AbstractEntity entity)
    {
        GfxEntity gfxEntity = entity.getgfxEntity();
        gfxEntity.getParent().removeChild(gfxEntity);
        subEntities.remove(entity);
    }

    void RomoveEntity(TankEntity entity)
    {
        GfxEntity gfxEntity = entity.getgfxEntity();
        gfxEntity.getParent().removeChild(gfxEntity);
        entities.remove(entity);
    }

    // TODO: This is horrible! It's just for testing!
    // Please optimise/refactor before the universe ends!
    void makeExplosion(Vec3 where, AbstractEntity parent, int type)
    {
        GfxEntity explosion = gfxEngine.createEntity(GfxEntity.PARTICLE_EMITTER);

        if (type == 0)
        {
            explosion.attachResource(explosionProjectile);
        } else if (type == 1)
        {
            explosion.attachResource(explosionTank);
        }
        explosion.attachResource(particleShader);
        explosion.attachResource(billboardMesh);
        explosion.attachResource(explosionTexture);

        explosion.colour = parent.getgfxEntity().colour;
        explosion.opacity = 0.9f;
        explosion.setScale(Vec3.VEC3_ZERO);
        explosion.setPosition(where);
        gfxEngine.getRootEntity().addChild(explosion);

        explosion.active = true;
    }
}
