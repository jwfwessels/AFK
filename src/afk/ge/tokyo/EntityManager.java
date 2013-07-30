/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.AbstractEntity;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.bot.london.LargeTank;
import afk.bot.london.London;
import afk.bot.london.Robot;
import afk.bot.london.SmallTank;
import com.hackoeur.jglm.Vec3;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * @author Jw
 */
public class EntityManager
{

    int NUMCUBES = 5;
    int SPAWNVALUE = (int) (Tokyo.BOARD_SIZE * 0.45);
    public ArrayList<TankEntity> entities;
    public ArrayList<TankEntity> obstacles;
    private ArrayList<AbstractEntity> subEntities;
    private GraphicsEngine gfxEngine;
    Resource cubeMesh;
    Resource halfSphereMesh;
    Resource ringMesh;
    Resource primativeShader;
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
    Resource largeTankBody;
    Resource largeTankBarrel;
    Resource largeTankTracks;
    Resource largeTankTurret;
    Resource largeTankWheels;
    Resource largeTankBodyTex;
    Resource largeTankBarrelTex;
    Resource largeTankTracksTex;
    Resource largeTankTurretTex;
    Resource largeTankWheelsTex;
    Resource largeTankShadowTex;
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
    private Vec3[] SPAWN_POINTS =
    {
        new Vec3(-SPAWNVALUE, 0, -SPAWNVALUE),
        new Vec3(SPAWNVALUE, 0, SPAWNVALUE),
        new Vec3(-SPAWNVALUE, 0, SPAWNVALUE),
        new Vec3(SPAWNVALUE, 0, -SPAWNVALUE),
        new Vec3(0, 0, -SPAWNVALUE),
        new Vec3(0, 0, -SPAWNVALUE),
        new Vec3(-SPAWNVALUE, 0, 0),
        new Vec3(SPAWNVALUE, 0, 0)
    };

    public EntityManager(London botEngine, GraphicsEngine gfxEngine)
    {
        this.botEngine = botEngine;
        this.gfxEngine = gfxEngine;
        entities = new ArrayList<TankEntity>();
        obstacles = new ArrayList<TankEntity>();
        subEntities = new ArrayList<AbstractEntity>();
        System.out.println("SPAWN_POINTS: " + SPAWNVALUE);
    }

    private void createObstacles()
    {
        int min = -18;
        int max = 18;
        for (int i = 0; i < NUMCUBES; i++)
        {

            GfxEntity cubeGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
            cubeGfxEntity.attachResource(cubeMesh);
            cubeGfxEntity.attachResource(primativeShader);
            cubeGfxEntity.setScale(5, 5, 5);

            cubeGfxEntity.setPosition(min + (int) (Math.random() * ((max - min) + 1)), 0, min + (int) (Math.random() * ((max - min) + 1)));
            gfxEngine.getRootEntity().addChild(cubeGfxEntity);

            TankEntity obsticleCube = new TankEntity(null, cubeGfxEntity, this, 100);
            obsticleCube.setColour(new Vec3(0.6f, 0.6f, 0.6f));
            obsticleCube.setOBB();
            obstacles.add(obsticleCube);

        }
        GfxEntity cubeNGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity cubeSGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity cubeEGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity cubeWGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);

        cubeNGfxEntity.attachResource(cubeMesh);
        cubeSGfxEntity.attachResource(cubeMesh);
        cubeEGfxEntity.attachResource(cubeMesh);
        cubeWGfxEntity.attachResource(cubeMesh);

        cubeNGfxEntity.attachResource(primativeShader);
        cubeSGfxEntity.attachResource(primativeShader);
        cubeEGfxEntity.attachResource(primativeShader);
        cubeWGfxEntity.attachResource(primativeShader);

        cubeNGfxEntity.setScale(50, 1, 0.5f);
        cubeSGfxEntity.setScale(50, 1, 0.5f);
        cubeEGfxEntity.setScale(0.5f, 1, 50);
        cubeWGfxEntity.setScale(0.5f, 1, 50);

        cubeNGfxEntity.setPosition(0, 0, -25);
        cubeSGfxEntity.setPosition(0, 0, 25);
        cubeEGfxEntity.setPosition(25, 0, 0);
        cubeWGfxEntity.setPosition(-25, 0, 0);

        gfxEngine.getRootEntity().addChild(cubeNGfxEntity);
        gfxEngine.getRootEntity().addChild(cubeSGfxEntity);
        gfxEngine.getRootEntity().addChild(cubeEGfxEntity);
        gfxEngine.getRootEntity().addChild(cubeWGfxEntity);

        TankEntity nWall = new TankEntity(null, cubeNGfxEntity, this, 100);
        TankEntity sWall = new TankEntity(null, cubeSGfxEntity, this, 100);
        TankEntity eWall = new TankEntity(null, cubeEGfxEntity, this, 100);
        TankEntity wWall = new TankEntity(null, cubeWGfxEntity, this, 100);

        nWall.setColour(new Vec3(0.1f, 0.1f, 0.1f));
        sWall.setColour(new Vec3(0.1f, 0.1f, 0.1f));
        eWall.setColour(new Vec3(0.1f, 0.1f, 0.1f));
        wWall.setColour(new Vec3(0.1f, 0.1f, 0.1f));

        nWall.setOBB();
        sWall.setOBB();
        eWall.setOBB();
        wWall.setOBB();

        obstacles.add(nWall);
        obstacles.add(sWall);
        obstacles.add(eWall);
        obstacles.add(wWall);
    }

    void createBots()
    {
        createObstacles();
        Robot[] bots = botEngine.getRobotInstances();
        for (int i = 0; i < bots.length; i++)
        {
            createEntity(bots[i], SPAWN_POINTS[i], BOT_COLOURS[i]);
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
        GfxEntity oBBEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity visionEntity = gfxEngine.createEntity(GfxEntity.NORMAL);

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

        //OBB
        oBBEntity.attachResource(cubeMesh);
        oBBEntity.attachResource(primativeShader);
        oBBEntity.yScale = 0.55f;
        oBBEntity.xScale = 0.80f;
        oBBEntity.colour = colour;
        oBBEntity.opacity = 0.2f;

        //vision sphere
        visionEntity.attachResource(ringMesh);
        visionEntity.attachResource(primativeShader);
        visionEntity.setScale(5, 5, 5);
        visionEntity.colour = colour;
//        visionEntity.opacity = 0.1f;

        tankGfxEntity.setPosition(spawnPoint);
        tankGfxEntity.addChild(tankBarrelEntity);
        tankGfxEntity.addChild(tankTracksEntity);
        tankGfxEntity.addChild(tankWheelsEntity);
        tankGfxEntity.addChild(tankShadowEntity);
        tankGfxEntity.addChild(visionEntity);
        tankGfxEntity.addChild(oBBEntity);

        gfxEngine.getRootEntity().addChild(tankGfxEntity);

        TankEntity tank = new TankEntity(botController, tankGfxEntity, this, TOTAL_LIFE);
        entities.add(tank);
        tank.name = "tank" + (entities.size() - 1);
        tank.setScaleForOBB(oBBEntity.getScale().scale(SCALE));
        return tank;
    }

    public TankEntity createLargeTank(Robot botController, Vec3 spawnPoint, Vec3 colour)
    {
        float TOTAL_LIFE = 10;
        float SCALE = 4.0f;
        GfxEntity tankGfxEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankBarrelEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankTracksEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankTurretEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankWheelsEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity tankShadowEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity oBBEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
        GfxEntity visionEntity = gfxEngine.createEntity(GfxEntity.NORMAL);

        tankGfxEntity.attachResource(largeTankBody);
        tankGfxEntity.attachResource(largeTankBodyTex);
        tankGfxEntity.attachResource(tankShader);
        tankGfxEntity.setScale(SCALE, SCALE, SCALE);
        tankGfxEntity.colour = colour;

        tankTurretEntity.attachResource(largeTankTurret);
        tankTurretEntity.attachResource(largeTankTurretTex);
        tankTurretEntity.attachResource(tankShader);
        tankTurretEntity.setPosition(0.0f, 0.17623f, -0.15976f);
        tankTurretEntity.colour = colour;

        tankBarrelEntity.attachResource(largeTankBarrel);
        tankBarrelEntity.attachResource(largeTankBarrelTex);
        tankBarrelEntity.attachResource(tankShader);
        tankBarrelEntity.setPosition(0.0f, 0.03200f, 0.22199f);
        tankBarrelEntity.colour = colour;
        tankTurretEntity.addChild(tankBarrelEntity);

        tankTracksEntity.attachResource(largeTankTracks);
        tankTracksEntity.attachResource(largeTankTracksTex);
        tankTracksEntity.attachResource(tankShader);
        tankTracksEntity.colour = new Vec3(0.4f, 0.4f, 0.4f);

        tankWheelsEntity.attachResource(largeTankWheels);
        tankWheelsEntity.attachResource(largeTankWheelsTex);
        tankWheelsEntity.attachResource(tankShader);
        tankWheelsEntity.colour = colour;

        tankShadowEntity.attachResource(floorMesh);
        tankShadowEntity.attachResource(simpleShadowShader);
        tankShadowEntity.attachResource(largeTankShadowTex);
        tankShadowEntity.colour = Vec3.VEC3_ZERO;
        tankShadowEntity.opacity = 0.99f;
        tankShadowEntity.yMove = 0.01f;
        tankShadowEntity.xScale = tankShadowEntity.zScale = 1.5f;

        //OBB
        oBBEntity.attachResource(cubeMesh);
        oBBEntity.attachResource(primativeShader);
        oBBEntity.yScale = 0.30f;
        oBBEntity.xScale = 0.60f;
        oBBEntity.colour = colour;
        oBBEntity.opacity = 0.2f;

        //vision sphere
        visionEntity.attachResource(ringMesh);
        visionEntity.attachResource(primativeShader);
        visionEntity.setScale(10 / SCALE, 10 / SCALE, 10 / SCALE);
        visionEntity.colour = colour;
//        visionEntity.opacity = 0.1f;

        tankGfxEntity.setPosition(spawnPoint);
        tankGfxEntity.addChild(tankTurretEntity);
        tankGfxEntity.addChild(tankTracksEntity);
        tankGfxEntity.addChild(tankWheelsEntity);
        tankGfxEntity.addChild(tankShadowEntity);
        tankGfxEntity.addChild(visionEntity);
        tankGfxEntity.addChild(oBBEntity);

        gfxEngine.getRootEntity().addChild(tankGfxEntity);

        TankEntity tank = new TankEntity(botController, tankGfxEntity, this, TOTAL_LIFE);
        entities.add(tank);
        tank.name = "tank" + (entities.size() - 1);
        tank.setScaleForOBB(oBBEntity.getScale().scale(SCALE));
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
        for (int i = 0; i < entities.size(); i++)
        {
            entities.get(i).checkCollisions();
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
        for (int i = 0; i < obstacles.size(); i++)
        {
            obstacles.get(i).render(alpha);
        }
    }

    protected void loadResources()
    {
        cubeMesh = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "cube");
        ringMesh = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "ring");
//        halfSphereMesh = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "half_sphere");
        primativeShader = gfxEngine.loadResource(Resource.SHADER, "primatives");

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
        largeTankBody = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "large_tank_body");
        largeTankBarrel = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "large_tank_barrel");
        largeTankTracks = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "large_tank_tracks");
        largeTankTurret = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "large_tank_turret");
        largeTankWheels = gfxEngine.loadResource(Resource.WAVEFRONT_MESH, "large_tank_wheels");
        largeTankBodyTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/large_tank_body");
        largeTankBarrelTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/large_tank_barrel");
        largeTankTracksTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/large_tank_tracks");
        largeTankTurretTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/large_tank_turret");
        largeTankWheelsTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/large_tank_wheels");
        largeTankShadowTex = gfxEngine.loadResource(Resource.TEXTURE_2D, "lightmaps/large_tank_shadow");
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

    void removeSubEntity(AbstractEntity entity)
    {
        GfxEntity gfxEntity = entity.getgfxEntity();
        gfxEntity.getParent().removeChild(gfxEntity);
        subEntities.remove(entity);
    }

    void removeEntity(TankEntity entity)
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
