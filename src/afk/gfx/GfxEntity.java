/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.gfx;

import com.hackoeur.jglm.Vec3;

/**
 * Interface to a mesh.
 * @author Daniel
 */
public abstract class GfxEntity
{
    // TODO: check performance issues regarding the use of Vec3.
    // could be a problem to update seeing as their member variables are all finite...
    public Vec3 position = new Vec3(0,0,0);
    public Vec3 rotation = new Vec3(0,0,0);
    public Vec3 scale = new Vec3(1,1,1);
}
