package afk.ge.tokyo;

import afk.ge.ems.Engine;
import afk.ge.ems.Entity;
import afk.ge.tokyo.ems.components.*;
import afk.ge.tokyo.ems.factories.HeightmapFactory;
import afk.ge.tokyo.ems.factories.HeightmapFactoryRequest;
import afk.ge.tokyo.ems.factories.ObstacleFactory;
import afk.ge.tokyo.ems.factories.ObstacleFactoryRequest;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.UUID;

/**
 *
 * @author Jw
 */
public class EntityManager
{

    public static final float WEAPON_RANGE = 50;
    public static final float WEAPON_DAMAGE = 20;
    public static final float BULLET_SPEED = 10;
    public static final float FIRE_RATE = 1f;
    public static final int WEAPON_AMMO = 0;
    public static final float SMALL_TANK_HP = 80;
    public static final float LARGE_TANK_HP = 100;
    public static final String SMALL_TANK_TYPE = "smallTank";
    public static final String LARGE_TANK_TYPE = "largeTankBase";
    public static final String LARGE_TANK_TURRET_TYPE = "largeTankTurret";
    public static final String LARGE_TANK_BARREL_TYPE = "largeTankBarrel";
    public static final Vec3 SMALL_TANK_EXTENTS = new Vec3(0.4385f, 0.2505f, 0.5f);
    public static final Vec3 SMALL_TANK_BBOX_OFFSET = new Vec3(0, SMALL_TANK_EXTENTS.getY(), 0);
    public static final Vec3 LARGE_TANK_EXTENTS = new Vec3(0.311f, 0.1355f, 0.5f);
    public static final Vec3 LARGE_TANK_BBOX_OFFSET = new Vec3(0, LARGE_TANK_EXTENTS.getY(), 0);
    public static final float SMALL_TANK_SCALE = 2;
    public static final float LARGE_TANK_SCALE = 3.5f;
    public static final int TANK_VDIST = 15;
    public static final int TANK_FOVY = 70;
    public static final int TANK_FOVX = 170;
    public static final Vec3 MAGENTA = new Vec3(1, 0, 1);
    int NUMCUBES = 10;
    public static final int SPAWNVALUE = (int) (Tokyo.BOARD_SIZE * 0.45);
    private Queue<Entity> particles = new ArrayDeque<Entity>();
    private ParticleEmitter[] emitters;
    Engine engine;
    public static final Vec3[] BOT_COLOURS =
    {
        new Vec3(1, 0, 0),
        new Vec3(0, 0, 1),
        new Vec3(0, 1, 0),
        new Vec3(1, 1, 0),
        new Vec3(1, 0, 1),
        new Vec3(0, 1, 1),
        new Vec3(0.95f, 0.95f, 0.95f),
        new Vec3(0.2f, 0.2f, 0.2f)
    };
    public static final Vec3[] SPAWN_POINTS =
    {
        new Vec3(-SPAWNVALUE, 0, -SPAWNVALUE),
        new Vec3(SPAWNVALUE, 0, SPAWNVALUE),
        new Vec3(-SPAWNVALUE, 0, SPAWNVALUE),
        new Vec3(SPAWNVALUE, 0, -SPAWNVALUE),
        new Vec3(0, 0, -SPAWNVALUE),
        new Vec3(0, 0, SPAWNVALUE),
        new Vec3(-SPAWNVALUE, 0, 0),
        new Vec3(SPAWNVALUE, 0, 0)
    };

    public EntityManager(Engine engine)
    {
        this.engine = engine;
    }

    

//    public TankEntity createSmallTank(Robot botController, Vec3 spawnPoint, Vec3 colour)
//    {
//        float TOTAL_LIFE = 8;
//        float SCALE = 2.0f;
//        
//        GfxEntity oBBEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
//        GfxEntity visionEntity = gfxEngine.createEntity(GfxEntity.NORMAL);
//
//        //OBB
//        oBBEntity.attachResource(cubeMesh);
//        oBBEntity.attachResource(primativeShader);
//        oBBEntity.yScale = 0.55f;
//        oBBEntity.xScale = 0.80f;
//        oBBEntity.colour = colour;
//        oBBEntity.opacity = 0.2f;
//
//        //vision sphere
//        visionEntity.attachResource(ringMesh);
//        visionEntity.attachResource(primativeShader);
//        visionEntity.setScale(5, 5, 5);
//        visionEntity.colour = colour;
////        visionEntity.opacity = 0.1f;
//        
//        tankGfxEntity.addChild(visionEntity);
//        tankGfxEntity.addChild(oBBEntity);
//
//        gfxEngine.getRootEntity().addChild(tankGfxEntity);
//        
//        tank.name = "tank" + (entities.size() - 1);
//        tank.setScaleForOBB(oBBEntity.getScale().scale(SCALE));
//    }
    public void createProjectileNEU(UUID parent, Weapon weapon, State current, Vec3 forward)
    {
        Entity projectile = new Entity();
        State state = new State(current.pos, current.rot, new Vec3(0.3f, 0.3f, -0.3f));
        projectile.add(state);

        projectile.add(new Velocity(forward.multiply(weapon.speed), Vec4.VEC4_ZERO));
        projectile.add(new Renderable("projectile", new Vec3(0.5f, 0.5f, 0.5f)));
        projectile.add(new Bullet(weapon.range, weapon.damage, parent));

        engine.addEntity(projectile);
    }
}
