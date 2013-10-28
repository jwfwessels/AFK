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
 package afk.ge.ems;

import afk.ge.tokyo.ems.components.Parent;
import afk.ge.tokyo.ems.components.State;
import static afk.gfx.GfxUtils.X_AXIS;
import static afk.gfx.GfxUtils.Y_AXIS;
import static afk.gfx.GfxUtils.Z_AXIS;
import com.hackoeur.jglm.Mat4;
import static com.hackoeur.jglm.Matrices.rotate;
import static com.hackoeur.jglm.Matrices.scale;
import static com.hackoeur.jglm.Matrices.translate;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;

/**
 *
 * @author Daniel
 */
public class Utils
{

    public static State getWorldState(Entity entity)
    {
        State state = entity.getComponent(State.class);
        if (state == null)
        {
            return null;
        }
        Parent parent = entity.getComponent(Parent.class);
        if (parent == null)
        {
            return state;
        }
        State parentState = getWorldState(parent.entity);
        if (parentState == null)
        {
            return state;
        }
        return new State(getMatrix(parentState).multiply(state.pos.toPoint()).getXYZ(),
                state.rot.add(parentState.rot),
                state.scale.multiply(parentState.scale));
    }
    
    public static Mat4 getMatrix(State state)
    {
        Mat4 monkeyWorld = new Mat4(1f);

        monkeyWorld = translate(monkeyWorld, state.pos);

        monkeyWorld = rotate(monkeyWorld, state.rot.getX(), X_AXIS);
        monkeyWorld = rotate(monkeyWorld, state.rot.getZ(), Z_AXIS);
        monkeyWorld = rotate(monkeyWorld, state.rot.getY(), Y_AXIS);
        // don't ask...
        monkeyWorld = rotate(monkeyWorld, state.rot.getW(), X_AXIS);

        monkeyWorld = scale(monkeyWorld, state.scale);

        return monkeyWorld;
    }
    
    public static Mat4 getRotationMatrix(State state)
    {
        Mat4 monkeyWorld = new Mat4(1f);

        monkeyWorld = rotate(monkeyWorld, state.rot.getX(), X_AXIS);
        monkeyWorld = rotate(monkeyWorld, state.rot.getZ(), Z_AXIS);
        monkeyWorld = rotate(monkeyWorld, state.rot.getY(), Y_AXIS);
        // don't ask...
        monkeyWorld = rotate(monkeyWorld, state.rot.getW(), X_AXIS);

        return monkeyWorld;
    }
    
    public static Mat4 getBBoxMatrix(State state, Vec3 offset)
    {
        Mat4 monkeyWorld = new Mat4(1f);
        
        Mat4 rotMatrix = getRotationMatrix(state);
        offset = rotMatrix.multiply(new Vec4(offset, 1)).getXYZ();

        monkeyWorld = translate(monkeyWorld, state.pos.add(offset));

        monkeyWorld = rotate(monkeyWorld, state.rot.getX(), X_AXIS);
        monkeyWorld = rotate(monkeyWorld, state.rot.getZ(), Z_AXIS);
        monkeyWorld = rotate(monkeyWorld, state.rot.getY(), Y_AXIS);
        // don't ask...
        monkeyWorld = rotate(monkeyWorld, state.rot.getW(), X_AXIS);

        return monkeyWorld;
    }
    
    public static Vec3 getForward(State state)
    {
        return getForward(getRotationMatrix(state));
    }
    
    public static Vec3 getForward(Mat4 mat)
    {
        return mat.multiply(new Vec4(0,0,1,0)).getXYZ();
    }
}
