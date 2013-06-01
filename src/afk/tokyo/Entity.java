/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Vec3;

/**
 *
 * @author Jw
 */
public class Entity
{
    private GfxEntity gfxPos;
    
    private Vec3 position;
    private Vec3 momentum;
    
    private Vec3 velocity;
    
    private float mass;
    private float inverseMass;

    void recalculate()
    {
        velocity = momentum.multiply(inverseMass);
    }

    protected class Derivative
    {
        Vec3 velocity;
        Vec3 force;
    }
}
