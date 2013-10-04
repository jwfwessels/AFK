package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author daniel
 */
public class State
{

    /// PREVIOUS STATE ///
    public Vec3 prevPos = Vec3.VEC3_ZERO;
    public Vec4 prevRot = Vec4.VEC4_ZERO;
    public Vec3 prevScale = new Vec3(1,1,1);
    /// CURRENT STATE ///
    public Vec3 pos = Vec3.VEC3_ZERO;
    public Vec4 rot = Vec4.VEC4_ZERO;
    public Vec3 scale = new Vec3(1,1,1);

    public State()
    {
    }

    public State(State original, Vec3 posShift)
    {
        this.prevPos = original.pos.add(posShift);
        this.prevRot = original.rot;
        this.prevScale = original.scale;
        this.pos = original.pos.add(posShift);
        this.rot = original.rot;
        this.scale = original.scale;
    }

    public State(Vec3 pos, Vec4 rot, Vec3 scale)
    {
        this.prevPos = pos;
        this.prevRot = rot;
        this.prevScale = scale;
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }

    public void setPrev(Vec3 prevPos, Vec4 prevRot, Vec3 prevScale)
    {
        this.prevPos = prevPos;
        this.prevRot = prevRot;
        this.prevScale = prevScale;
    }
    
    public void set(Vec3 pos, Vec4 rot, Vec3 scale)
    {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }
    
    public void reset(Vec3 pos, Vec4 rot, Vec3 scale)
    {
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
        this.prevPos = pos;
        this.prevRot = rot;
        this.prevScale = scale;
    }
}
