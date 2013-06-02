/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package afk.tokyo;

import afk.gfx.GfxEntity;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Jw
 */
public class QuantEntity
{

    private GfxEntity gfxPos;
    //1
    private Quternion test;
    private Vec4 orientation;
    private Vec3 angularMomentum;
    //2
    private Vec4 spin;
    private Vec3 angularVelocity;
    //3
    private float inertia;
    private float inverseInertia;

    void recalculate()
    {
        angularVelocity = angularMomentum.multiply(inverseInertia);
        //does this normalize the vector?
        orientation = orientation.getUnitVector();
        
        spin = orientation.add(new Vec4(angularVelocity, 0).multiply(0.5f));

    }

    protected class Derivative
    {

        Vec3 velocity;
        Vec3 force;
    }

    protected class Quternion
    {

        float w, x, y, z;
    }
}
