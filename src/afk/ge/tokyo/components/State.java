package afk.ge.tokyo.components;

import com.hackoeur.jglm.Vec3;

/**
 *
 * @author daniel
 */
public class State {
    
    /// PREVIOUS STATE ///
    Vec3 prevPos;
    Vec3 prevRot;
    Vec3 prevScale;
    
    /// CURRENT STATE ///
    Vec3 pos;
    Vec3 rot;
    Vec3 scale;
}
