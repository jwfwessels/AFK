package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class State
{

    /// PREVIOUS STATE ///
    public Vec3 prevPos;
    public Vec3 prevRot;
    public Vec3 prevScale;
    /// CURRENT STATE ///
    public Vec3 pos;
    public Vec3 rot;
    public Vec3 scale;

    public State(Vec3 pos, Vec3 rot, Vec3 scale)
    {
        this.prevPos = pos;
        this.prevRot = rot;
        this.prevScale = scale;
        this.pos = pos;
        this.rot = rot;
        this.scale = scale;
    }

    public void setPrev(Vec3 prevPos, Vec3 prevRot, Vec3 prevScale)
    {
        this.prevPos = prevPos;
        this.prevRot = prevRot;
        this.prevScale = prevScale;
    }
}
