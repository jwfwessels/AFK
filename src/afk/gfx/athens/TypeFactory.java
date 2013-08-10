package afk.gfx.athens;

import afk.gfx.GfxEntity;
import afk.gfx.athens.particles.ParticleEmitter;
import com.hackoeur.jglm.Vec3;
import java.util.HashMap;
import java.util.Map;
import static afk.gfx.Resource.*;
import static afk.gfx.GfxEntity.*;

/**
 *
 * @author daniel
 */
public class TypeFactory
{
    /// TEMPORARY (maybe) CLASS UNTIL CONFIG ENGINE/DATABASE/SYSTEM/WHATEVER ACTUALLY HAPPENS
    /// (so sort of a placeholder then)
    
    
    private Map<String, TypeFactoryThing> factories = new HashMap<String, TypeFactoryThing>();
    private ResourceManager resourceManager;

    public TypeFactory(ResourceManager resourceManager)
    {
        this.resourceManager = resourceManager;
        
        factories.put("smallTank", new SmallTankFactory());
        factories.put("largeTank", new LargeTankFactory());
        factories.put("projectile", new ProjectileFactory());
        factories.put("wall", new WallFactory());
        factories.put("floor", new FloorFactory());
        factories.put("projectileExplosion", new ExplosionFactory(0));
        factories.put("tankExplosion", new ExplosionFactory(1));
    }
    
    public AthensEntity createInstance(String type)
    {
        return factories.get(type).create();
    }
    
    private interface TypeFactoryThing
    {
        public AthensEntity create();
    }
    private class SmallTankFactory implements TypeFactoryThing
    {

        @Override
        public AthensEntity create()
        {
            AthensEntity rootGfxEntity = createEntity(NORMAL);
            AthensEntity tankBarrelEntity = createEntity(NORMAL);
            AthensEntity tankTracksEntity = createEntity(NORMAL);
            AthensEntity tankWheelsEntity = createEntity(NORMAL);
            AthensEntity tankShadowEntity = createEntity(NORMAL);
            AthensEntity oBBEntity = createEntity(NORMAL);
            AthensEntity visionEntity = createEntity(NORMAL);

            rootGfxEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "small_tank_body"));
            rootGfxEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/small_tank_body"));
            rootGfxEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));

            tankBarrelEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "small_tank_barrel"));
            tankBarrelEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/small_tank_barrel"));
            tankBarrelEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));
            tankBarrelEntity.setPosition(0.0f, 0.41522f, 0.28351f);

            tankTracksEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "small_tank_tracks"));
            tankTracksEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/small_tank_tracks"));
            tankTracksEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));
            tankTracksEntity.colour = new Vec3(0.4f, 0.4f, 0.4f);

            tankWheelsEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "small_tank_wheels"));
            tankWheelsEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/small_tank_wheels"));
            tankWheelsEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));

            tankShadowEntity.attachResource(resourceManager.getResource(PRIMITIVE_MESH, "quad"));
            tankShadowEntity.attachResource(resourceManager.getResource(SHADER, "simpleshadow"));
            tankShadowEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/small_tank_shadow"));
            tankShadowEntity.colour = Vec3.VEC3_ZERO;
            tankShadowEntity.opacity = 0.99f;
            tankShadowEntity.yMove = 0.01f;
            tankShadowEntity.xScale = tankShadowEntity.zScale = 1.5f;
            
            rootGfxEntity.addChild(tankBarrelEntity);
            rootGfxEntity.addChild(tankTracksEntity);
            rootGfxEntity.addChild(tankWheelsEntity);
            rootGfxEntity.addChild(tankShadowEntity);
            rootGfxEntity.addChild(visionEntity);
            rootGfxEntity.addChild(oBBEntity);
            
            return rootGfxEntity;
        }
        
    }
    private class LargeTankFactory implements TypeFactoryThing
    {

        @Override
        public AthensEntity create()
        {
            AthensEntity rootGfxEntity = createEntity(NORMAL);
            AthensEntity tankBarrelEntity = createEntity(NORMAL);
            AthensEntity tankTracksEntity = createEntity(NORMAL);
            AthensEntity tankTurretEntity = createEntity(NORMAL);
            AthensEntity tankWheelsEntity = createEntity(NORMAL);
            AthensEntity tankShadowEntity = createEntity(NORMAL);

            rootGfxEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "large_tank_body"));
            rootGfxEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/large_tank_body"));
            rootGfxEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));

            tankTurretEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "large_tank_turret"));
            tankTurretEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/large_tank_turret"));
            tankTurretEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));
            tankTurretEntity.setPosition(0.0f, 0.17623f, -0.15976f);

            tankBarrelEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "large_tank_barrel"));
            tankBarrelEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/large_tank_barrel"));
            tankBarrelEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));
            tankBarrelEntity.setPosition(0.0f, 0.03200f, 0.22199f);
            tankTurretEntity.addChild(tankBarrelEntity);

            tankTracksEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "large_tank_tracks"));
            tankTracksEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/large_tank_tracks"));
            tankTracksEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));
            tankTracksEntity.colour = new Vec3(0.4f, 0.4f, 0.4f);

            tankWheelsEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "large_tank_wheels"));
            tankWheelsEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/large_tank_wheels"));
            tankWheelsEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));

            tankShadowEntity.attachResource(resourceManager.getResource(PRIMITIVE_MESH, "quad"));
            tankShadowEntity.attachResource(resourceManager.getResource(SHADER, "simpleshadow"));
            tankShadowEntity.attachResource(resourceManager.getResource(TEXTURE_2D, "lightmaps/large_tank_shadow"));
            tankShadowEntity.colour = Vec3.VEC3_ZERO;
            tankShadowEntity.opacity = 0.99f;
            tankShadowEntity.yMove = 0.01f;
            tankShadowEntity.xScale = tankShadowEntity.zScale = 1.5f;
            
            return rootGfxEntity;
        }
        
    }
    private class ProjectileFactory implements TypeFactoryThing
    {

        @Override
        public AthensEntity create()
        {
            AthensEntity projectileGfxEntity = createEntity(GfxEntity.NORMAL);

            projectileGfxEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "cube"));
            projectileGfxEntity.attachResource(resourceManager.getResource(SHADER, "monkey"));
            
            return projectileGfxEntity;
        }
        
    }
    private class WallFactory implements TypeFactoryThing
    {

        @Override
        public AthensEntity create()
        {
            AthensEntity gfxEntity = createEntity(GfxEntity.NORMAL);
            gfxEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "cube"));
            gfxEntity.attachResource(resourceManager.getResource(SHADER, "primative"));
            
            return gfxEntity;
        }
        
    }
    private class FloorFactory implements TypeFactoryThing
    {

        @Override
        public AthensEntity create()
        {
            AthensEntity floorGfxEntity = createEntity(GfxEntity.NORMAL);
            floorGfxEntity.attachResource(resourceManager.getResource(WAVEFRONT_MESH, "cube"));
            floorGfxEntity.attachResource(resourceManager.getResource(SHADER, "floor"));
            
            return floorGfxEntity;
        }
        
    }
    private class ExplosionFactory implements TypeFactoryThing
    {
        String type;

        public ExplosionFactory(int type)
        {
            this.type = type == 0 ? "explosionProjectile" : "explosionTank";
        }

        @Override
        public AthensEntity create()
        {
            AthensEntity explosion = createEntity(GfxEntity.PARTICLE_EMITTER);

            explosion.attachResource(resourceManager.getResource(PARTICLE_PARAMETERS, type));
            
            explosion.attachResource(resourceManager.getResource(PARTICLE_PARAMETERS, "texturedParticle"));
            explosion.attachResource(resourceManager.getResource(PRIMITIVE_MESH, "billboard"));
            explosion.attachResource(resourceManager.getResource(TEXTURE_2D, "explosion"));
            
            explosion.opacity = 0.9f;
            explosion.active = true;
            
            return explosion;
        }
        
    }

    protected AthensEntity createEntity(int behaviour)
    {
        // TODO: research possible performance hit is we use Class.newInstance() instead of using ints

        AthensEntity entity;
        switch (behaviour)
        {
            case NORMAL:
                entity = new AthensEntity();
                break;
            case BILLBOARD_SPHERICAL:
                entity = new BillboardEntity(true);
                break;
            case BILLBOARD_CYLINDRICAL:
                entity = new BillboardEntity(false);
                break;
            case PARTICLE_EMITTER:
                entity = new ParticleEmitter();
                break;
            default:
                // TODO: throw new InvalidEntityBehaviourException();
                return null;
        }

        return entity;
    }
}
