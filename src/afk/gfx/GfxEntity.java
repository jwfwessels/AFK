package afk.gfx;

import com.hackoeur.jglm.Vec3;
import java.util.Collection;

/**
 * Interface to a graphics entity. A Graphics entity is a visual object with
 * position, rotation, scale and colour. Individual implementations and
 * specialisations may interpret each of these differently, but their
 * meanings should all be straightforward.
 * <br/>
 * <em>Note:</em> A graphics entity has an "active" flag, which in most cases
 * will determine the visibility of the entity, but in some cases will mean
 * other things. Please refer to specific implementation documentation for
 * additional information.
 * @author Daniel
 */
public abstract class GfxEntity
{
    /** Graphics Entity behaviours. */
    public static final int NORMAL = 0, BILLBOARD_SPHERICAL = 1,
            BILLBOARD_CYLINDRICAL = 2, PARTICLE_EMITTER = 3;
    
    /** The entity's position. */
    public float xMove = 0, yMove = 0, zMove = 0;
    /** The entity's rotation. */
    public float xRot = 0, yRot = 0, zRot = 0;
    /** The entity's scale. */
    public float xScale = 1, yScale = 1, zScale = 1;
    
    /** The entity's colour. */
    public Vec3 colour = null; // TODO: this may become more generic in the future
    
    /**
     * By default, this boolean dictates whether or not the entity is visible.
     */
    public boolean active = true;

    /**
     * Gets the entity's position as a Vec3.
     * @return the entity's position.
     */
    public Vec3 getPosition()
    {
        return new Vec3(xMove,yMove,zMove);
    }
    
    /**
     * Gets the entity's rotation as a Vec3.
     * @return the entity's rotation.
     */
    public Vec3 getRotation()
    {
        return new Vec3(xRot, yRot, zRot);
    }
    
    /**
     * Gets the entity's scale as a Vec3.
     * @return the entity's scale.
     */
    public Vec3 getScale()
    {
        return new Vec3(xScale, yScale, zScale);
    }
    
    /**
     * Sets the entity's position.
     * @param xMove the X component of the new position.
     * @param yMove the Y component of the new position.
     * @param zMove the Z component of the new position.
     */
    public void setPosition(float xMove, float yMove, float zMove)
    {
        this.xMove = xMove;
        this.yMove = yMove;
        this.zMove = zMove;
    }
    
    /**
     * Sets the entity's position.
     * @param position the new position.
     */
    public void setPosition(Vec3 position)
    {
        this.setPosition(position.getX(), position.getY(), position.getZ());
    }
    
    /**
     * Sets the entity's rotation.
     * @param xMove the component's rotation around the X axis.
     * @param yMove the component's rotation around the Y axis.
     * @param zMove the component's rotation around the Z axis.
     */
    public void setRotation(float xRot, float yRot, float zRot)
    {
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
    }
    
    /**
     * Sets the entity's rotation.
     * @param position the new rotation.
     */
    public void setRotation(Vec3 rotation)
    {
        this.setRotation(rotation.getX(), rotation.getY(), rotation.getZ());
    }
    
    /**
     * Sets the entity's scale.
     * @param xMove the component's scale in the X axis.
     * @param yMove the component's scale in the Y axis.
     * @param zMove the component's scale in the Z axis.
     */
    public void setScale(float xScale, float yScale, float zScale)
    {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
    }
    
    /**
     * Sets the entity's scale.
     * @param position the new scale.
     */
    public void setScale(Vec3 scale)
    {
        this.setScale(scale.getX(), scale.getY(), scale.getZ());
    }
    
    protected abstract void addEntity(GfxEntity entity);
    protected abstract void removeEntity(GfxEntity entity);
    protected abstract Collection<? extends GfxEntity> removeAllEntities();
    protected abstract GfxEntity getParent();
}
