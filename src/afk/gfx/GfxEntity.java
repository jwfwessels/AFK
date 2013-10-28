/*
 * Copyright (c) 2013 Triforce
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
 package afk.gfx;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
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
    public static final Vec3 RED = new Vec3(1, 0, 0);
    public static final Vec3 GREEN = new Vec3(0, 1, 0);
    public static final Vec3 BLUE = new Vec3(0, 0, 1);
    public static final Vec3 CYAN = new Vec3(0, 1, 1);
    public static final Vec3 MAGENTA = new Vec3(1, 0, 1);
    public static final Vec3 YELLOW = new Vec3(1, 1, 0);
    public static final Vec3 BLACK = new Vec3(0, 0, 0);
    public static final Vec3 WHITE = new Vec3(1, 1, 1);
    
    /** Graphics Entity behaviours. */
    public static final int NORMAL = 0, BILLBOARD_SPHERICAL = 1,
            BILLBOARD_CYLINDRICAL = 2;
    
    /** The entity's position. */
    public Vec3 position = Vec3.VEC3_ZERO;
    /** The entity's rotation. */
    public Vec4 rotation = Vec4.VEC4_ZERO;
    /** The entity's scale. */
    public Vec3 scale = new Vec3(1,1,1);
    
    /** The entity's colour. */
    public Vec3 colour = null; // TODO: this may become more generic in the future
    
    /** The entity's level of opacity. */
    public float opacity = 1.0f;
    
    /** The entity's lifetime */
    public float life;
    
    /**
     * By default, this boolean dictates whether or not the entity is visible.
     */
    public boolean active = true;
    
    
    /**
     * Gets the entity's world position as a Vec3.
     * @return the entity's position.
     */
    public Vec3 getWorldPosition()
    {
        GfxEntity parent = getParent();
        if (parent != null)
            return position.multiply(parent.getWorldScale())
                    .add(parent.getWorldPosition());
        return position;
    }
    
    /**
     * Gets the entity's world rotation as a Vec3.
     * @return the entity's rotation.
     */
    public Vec4 getWorldRotation()
    {
        GfxEntity parent = getParent();
        if (parent != null)
            return rotation.add(parent.getWorldRotation());
        return rotation;
    }
    
    /**
     * Gets the entity's world scale as a Vec3.
     * @return the entity's scale.
     */
    public Vec3 getWorldScale()
    {
        GfxEntity parent = getParent();
        if (parent != null)
            return scale.multiply(parent.getWorldScale());
        return scale;
    }
    
    /**
     * Get this entity's world matrix.
     * @return this entity's world matrix.
     */
    public abstract Mat4 getWorldMatrix();
    
    /**
     * Adds the entity as a child entity of this entity. The child entity will
     * be draw whenever the parent is drawn. The child entity will use any of
     * the parent's resources that the child does not have of its own.
     * @param entity the entity to add as the child,
     */
    public abstract void addChild(GfxEntity entity);
    
    /**
     * Removes the child entity from this entity. The child entity will no
     * longer be drawn when its (former) parent is drawn. If the given entity is
     * not a child of this entity, then the method has no effect.
     * @param entity the child entity to remove.
     */
    public abstract void removeChild(GfxEntity entity);
    
    /**
     * Removes all child entities from this entity. This method returns a
     * collection of all children belonging to this entity. If there are no
     * children, an empty collection is returned.
     * @return A collection containing all removed child entities.
     */
    public abstract Collection<? extends GfxEntity> removeAllChildren();
    
    /**
     * Gets the parent of this entity.
     * @return the parent of this entity, null if the entity has no parent.
     */
    public abstract GfxEntity getParent();
    
    /**
     * Attaches the specified resource to this GfxEntity.
     * @param resource the resource to attach to the entity.
     * @throws ResourceNotCompatableException if this entity does not support
     * the attachment of the given resource.
     */
    public abstract void attachResource(Resource resource)
            // TODO: throws ResourceNotCompatableException
            ;
}
