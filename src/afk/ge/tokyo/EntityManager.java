/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.ge.tokyo;

import afk.ge.tokyo.ems.Engine;
import afk.ge.tokyo.ems.Entity;
import afk.ge.tokyo.ems.components.*;
import static afk.gfx.GfxUtils.*;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.jogamp.graph.geom.AABBox;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
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
    public static final float WEAPON_HEALTH_PENALTY = 3;
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

        emitters = new ParticleEmitter[2];
        try
        {
            emitters[0] = loadParticleParameters("explosionProjectile");
            emitters[1] = loadParticleParameters("explosionTank");
        } catch (IOException ex)
        {
            System.err.println("Error loading particle config: " + ex.getMessage());
        }
    }

    public void spawnStuff()
    {
        createFloor();
        engine.addEntity(createGraphicWall(new Vec3(0, 0, -Tokyo.BOARD_SIZE / 2), new Vec3(Tokyo.BOARD_SIZE, 5, 0.5f)));
        engine.addEntity(createGraphicWall(new Vec3(0, 0, Tokyo.BOARD_SIZE / 2), new Vec3(Tokyo.BOARD_SIZE, 5, 0.5f)));
        engine.addEntity(createGraphicWall(new Vec3(Tokyo.BOARD_SIZE / 2, 0, 0), new Vec3(0.5f, 5, Tokyo.BOARD_SIZE)));
        engine.addEntity(createGraphicWall(new Vec3(-Tokyo.BOARD_SIZE / 2, 0, 0), new Vec3(0.5f, 5, Tokyo.BOARD_SIZE)));
    }

    public void createFloor()
    {
        Entity floor = new Entity();
        floor.add(new State(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO,
                new Vec3(Tokyo.BOARD_SIZE)));
        floor.add(new Renderable("floor", new Vec3(1.0f, 1.0f, 1.0f)));
        try
        {
            floor.add(new HeightmapLoader().load("hm2", Tokyo.BOARD_SIZE, Tokyo.BOARD_SIZE, Tokyo.BOARD_SIZE));
        } catch (IOException ex)
        {
            System.err.println("Error loading up heightmap: " + ex.getMessage());
        }

        engine.addEntity(floor);
    }

    public Entity createGraphicWall(Vec3 pos, Vec3 scale)
    {
        Entity wall = new Entity();
        wall.add(new State(pos, Vec4.VEC4_ZERO, scale));
        wall.add(new BBoxComponent(scale.scale(0.5f),new Vec3(0,scale.getY()*0.5f,0)));
        wall.add(new Renderable("wall", new Vec3(0.75f, 0.75f, 0.75f)));

        return wall;
    }

    public void createObstacles(Vec3 scale)
    {
        int min = -18;
        int max = 18;
        for (int i = 0; i < NUMCUBES; i++)
        {

            Vec3 pos = new Vec3(min + (int) (Math.random() * ((max - min) + 1)), 0, min + (int) (Math.random() * ((max - min) + 1)));

            createGraphicWall(pos, scale);

        }
    }

    public Entity createTankEntityNEU(UUID id, Vec3 spawnPoint, Vec3 colour)
    {
        Vec3 scale = new Vec3(LARGE_TANK_SCALE);

        Entity tank = new Entity();

        Controller controller = new Controller(id);
        tank.add(controller);
        tank.add(new State(spawnPoint, Vec4.VEC4_ZERO, scale));
        // tank.add(new BBoxComponent(new Vec3(1.0f,0.127f,0.622f).multiply(scale)));
        tank.add(new BBoxComponent(LARGE_TANK_EXTENTS.multiply(scale), LARGE_TANK_BBOX_OFFSET.multiply(scale)));
        tank.add(new Velocity(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO));
        tank.add(new Motor(2f, 20f));
        tank.add(new Life(SMALL_TANK_HP));
        tank.add(new Renderable(LARGE_TANK_TYPE, colour));
        tank.add(new RobotToken());
        tank.add(new Targetable());
        tank.add(new TankTracks());
        tank.add(new SnapToTerrain());
        
        
        Entity turret = createLargeTankTurret(colour);
        turret.add(new Parent(tank));
        turret.add(controller);
        tank.addDependent(turret);
        Entity barrel = createLargeTankBarrel(colour);
        barrel.add(new Parent(turret));
        barrel.add(controller);
        turret.addDependent(barrel);

        return tank;
    }

    public Entity createLargeTankTurret(Vec3 colour)
    {
        Entity entity = new Entity();

        entity.add(new State(new Vec3(0.0f, 0.17623f, -0.15976f), Vec4.VEC4_ZERO, new Vec3(1)));
        entity.add(new Velocity(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO));
        entity.add(new Renderable(LARGE_TANK_TURRET_TYPE, colour));
        entity.add(new Vision(TANK_VDIST, TANK_FOVY, TANK_FOVX));
        entity.add(new TankTurret());

        return entity;
    }

    public Entity createLargeTankBarrel(Vec3 colour)
    {
        Entity entity = new Entity();

        entity.add(new State(new Vec3(0.0f, 0.03200f, 0.22199f), Vec4.VEC4_ZERO, new Vec3(1)));
        entity.add(new Weapon(WEAPON_RANGE, WEAPON_DAMAGE, BULLET_SPEED, 1.0f / FIRE_RATE, WEAPON_AMMO, WEAPON_HEALTH_PENALTY));
        entity.add(new Velocity(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO));
        entity.add(new Renderable(LARGE_TANK_BARREL_TYPE, colour));
        entity.add(new AngleConstraint(new Vec4(0,0,0,-45),new Vec4(0,0,0,10)));
        entity.add(new TankBarrel(0.545f));

        return entity;
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

    public Entity makeExplosion(Vec3 where, UUID parent, int type)
    {
        Entity entity = new Entity();

        entity.add(new State(where, Vec4.VEC4_ZERO, new Vec3(1, 1, 1)));

        ParticleEmitter emitter = new ParticleEmitter(emitters[type]);
        emitter.colour = MAGENTA; // TODO: get colour from config
        entity.add(emitter);

        return entity;
    }

    /**
     * A Pie is a flat plane that always faces your face.
     */
    public void makePie(State emitterState, ParticleEmitter emitter)
    {
        Entity pie = particles.poll();
        if (pie == null)
        {
            pie = new Entity();
            pie.add(new State(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO, Vec3.VEC3_ZERO));
            pie.add(new Lifetime(0));
            pie.add(new Velocity(Vec3.VEC3_ZERO, Vec4.VEC4_ZERO));
            pie.add(new Renderable("particle", Vec3.VEC3_ZERO));
        }

        State state = pie.get(State.class);
        Velocity velocity = pie.get(Velocity.class);
        Lifetime lifetime = pie.get(Lifetime.class);
        Renderable renderable = pie.get(Renderable.class);

        Vec3 pos = emitterState.pos;
        Vec4 rot = emitterState.rot;
        Vec3 scale = emitterState.scale;

        state.reset(new Vec3(
                jitter(pos.getX(), scale.getX()),
                jitter(pos.getY(), scale.getY()),
                jitter(pos.getZ(), scale.getZ())),
                Vec4.VEC4_ZERO,
                new Vec3(jitter(emitter.scale, emitter.scaleJitter)));

        Vec3 dir;
        if (emitter.noDirection)
        {
            // uniform sphere distribution
            dir = new Vec3(
                    (float) random.nextGaussian(),
                    (float) random.nextGaussian(),
                    (float) random.nextGaussian())
                    .getUnitVector();
        } else
        {
            // uniform cone distribution (I think)
            Mat4 rotation = Matrices.rotate(new Mat4(1.0f), jitter(rot.getX(), emitter.angleJitter.getX()), X_AXIS);
            rotation = Matrices.rotate(rotation, jitter(rot.getY(), emitter.angleJitter.getY()), Y_AXIS);
            rotation = Matrices.rotate(rotation, jitter(rot.getZ(), emitter.angleJitter.getZ()), Z_AXIS);

            Vec4 newRotation = rotation.multiply(ANCHOR);

            dir = new Vec3(newRotation.getX(), newRotation.getY(), newRotation.getZ());
        }

        float speed = jitter(emitter.speed, emitter.speedJitter);

        velocity.v = dir.scale(speed);

        lifetime.life = jitter(emitter.maxLife, emitter.lifeJitter);

        renderable.colour = emitter.colour;

        engine.addEntity(pie);
    }

    public void recyclePie(Entity entity)
    {
        // TODO: consider making queue bounded so as to limit lots of particles
        particles.offer(entity);
    }

    public static ParticleEmitter loadParticleParameters(String name) throws IOException
    {
        ParticleEmitter emitter = new ParticleEmitter();
        loadParticleParameters(emitter, name);
        return emitter;
    }

    public static void loadParticleParameters(ParticleEmitter emitter, String name) throws IOException
    {
        BufferedReader br = new BufferedReader(new FileReader("particles/" + name + ".px"));
        try
        {

            String line;
            for (int lineNumber = 1; (line = br.readLine()) != null; lineNumber++)
            {
                line = line.replaceFirst(";.*$", "").trim();

                if (line.isEmpty())
                {
                    continue;
                }

                String[] split1 = line.split("\\s*=\\s*");

                String param = split1[0];
                String value = split1[1];


                Field field;
                try
                {
                    field = ParticleEmitter.class.getField(param);
                } catch (NoSuchFieldException ex)
                {
                    throw new IOException("Invalid parameter name [" + param
                            + "] on line " + lineNumber);
                } catch (SecurityException ex)
                {
                    throw new IOException("Parameter error [" + param
                            + "] on line " + lineNumber + ": " + ex.getMessage());
                }

                Class fieldClass = field.getType();



                try
                {
                    if (fieldClass == Vec3.class)
                    {
                        field.set(emitter, parseVec3(value));
                    } else if (fieldClass == AABBox.class)
                    {
                        field.set(emitter, parseAABBox(value));
                    } else
                    {
                        field.set(emitter, getWrapperClass(fieldClass).getMethod("valueOf", String.class).invoke(null, value));
                    }
                } catch (NumberFormatException nfe)
                {
                    throw new IOException("Invalid value on line " + lineNumber + ": "
                            + nfe.getMessage());
                } catch (ReflectiveOperationException ex)
                {
                    throw new IOException("Fatal error on line " + lineNumber + ": "
                            + ex.getMessage());
                }
            }
        } finally
        {
            br.close();
        }
    }

    private static Class getWrapperClass(Class clazz)
    {
        if (clazz == int.class)
        {
            return Integer.class;
        }
        if (clazz == short.class)
        {
            return Short.class;
        }
        if (clazz == byte.class)
        {
            return Byte.class;
        }
        if (clazz == long.class)
        {
            return Long.class;
        }
        if (clazz == float.class)
        {
            return Float.class;
        }
        if (clazz == double.class)
        {
            return Double.class;
        }
        if (clazz == boolean.class)
        {
            return Boolean.class;
        }
        if (clazz == char.class)
        {
            return Character.class;
        }
        return Object.class;
    }

    private static Vec3 parseVec3(String values)
    {
        String[] split = values.split("\\s+");

        if (split.length != 3)
        {
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 3, found " + split.length);
        }

        return new Vec3(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2]));
    }

    private static AABBox parseAABBox(String values)
    {
        String[] split = values.split("\\s+");

        if (split.length != 6)
        {
            throw new NumberFormatException("Invalid number of parameters: "
                    + "need 6, found " + split.length);
        }

        return new AABBox(
                Float.parseFloat(split[0]),
                Float.parseFloat(split[1]),
                Float.parseFloat(split[2]),
                Float.parseFloat(split[3]),
                Float.parseFloat(split[4]),
                Float.parseFloat(split[5]));
    }
}
