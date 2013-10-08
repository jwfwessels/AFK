package afk.ge.tokyo.ems.components;

import com.hackoeur.jglm.Vec4;

public class AngleConstraint
{
	public Vec4 min = Vec4.VEC4_ZERO;
	public Vec4 max = Vec4.VEC4_ZERO;

        public AngleConstraint()
        {
        }

	public AngleConstraint(Vec4 min, Vec4 max)
	{
		this.min = min;
		this.max = max;
	}
}