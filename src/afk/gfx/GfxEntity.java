package afk.gfx;

import com.hackoeur.jglm.Vec3;

/**
 * Interface to a graphics entity.
 * @author Daniel
 */
public abstract class GfxEntity
{
    public float xMove = 0, yMove = 0, zMove = 0;
    public float xRot = 0, yRot = 0, zRot = 0;
    public float xScale = 1, yScale = 1, zScale = 1;
    
    public Vec3 colour = null; // TODO: this may become more generic in the future

    public Vec3 getPosition()
    {
        return new Vec3(xMove,yMove,zMove);
    }
    
    public Vec3 getRotation()
    {
        return new Vec3(xRot, yRot, zRot);
    }
    
    public Vec3 getScale()
    {
        return new Vec3(xScale, yScale, zScale);
    }
    
    public void setPosition(float xMove, float yMove, float zMove)
    {
        this.xMove = xMove;
        this.yMove = yMove;
        this.zMove = zMove;
    }
    
    public void setPosition(Vec3 position)
    {
        this.setPosition(position.getX(), position.getY(), position.getZ());
    }
    
    public void setRotation(float xRot, float yRot, float zRot)
    {
        this.xRot = xRot;
        this.yRot = yRot;
        this.zRot = zRot;
    }
    
    public void setRotation(Vec3 rotation)
    {
        this.setRotation(rotation.getX(), rotation.getY(), rotation.getZ());
    }
    
    public void setScale(float xScale, float yScale, float zScale)
    {
        this.xScale = xScale;
        this.yScale = yScale;
        this.zScale = zScale;
    }
    
    public void setScale(Vec3 scale)
    {
        this.setScale(scale.getX(), scale.getY(), scale.getZ());
    }
}
