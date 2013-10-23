package afk.ge;

import afk.ge.ems.Utils;
import afk.ge.tokyo.ems.components.BBoxComponent;
import afk.ge.tokyo.ems.components.State;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import com.hackoeur.jglm.support.FastMath;
import static com.hackoeur.jglm.support.FastMath.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Oriented Bounding Box. Stored as a matrix (without scaling) and Extents( x,
 * y, z ).
 *
 * Ported to java from this article:
 * http://www.3dkingdoms.com/weekly/weekly.php?a=21
 *
 * @author Jonathan Kreuzer
 * @author Daniel
 */
public class BBox
{

    private Mat4 m;
    private Vec3 extents;

    public BBox()
    {
    }

    public BBox(final Mat4 m, final Vec3 extents)
    {
        set(m, extents);
    }

    /**
     * BL = Low values corner point, BH = High values corner point.
     */
    public BBox(final Mat4 m, final Vec3 bl, final Vec3 bh)
    {
        set(m, bl, bh);
    }

    public BBox(State state, BBoxComponent bBoxComponent)
    {
        m = Utils.getBBoxMatrix(state, bBoxComponent.offset.multiply(state.scale));

        this.extents = bBoxComponent.extent.multiply(state.scale);
    }

    public final void set(final Mat4 m, final Vec3 extents)
    {
        this.m = m;
        this.extents = extents;
    }

    public final void set(final Mat4 m, final Vec3 bl, final Vec3 bh)
    {
        this.m = m;
        this.m.translate((bh.add(bl)).multiply(0.5f));
        extents = (bh.subtract(bl)).multiply(0.5f);
    }

    public Vec3 getSize()
    {
        return extents.multiply(2.0f);
    }

    public Vec3 getCenterPoint()
    {
        return m.getTranslate();
    }

    /**
     * Check if a point is in this bounding box.
     *
     * @param inP the point to check.
     * @return true if the point is within the box, false otherwise.
     */
    public boolean isPointInBox(final Vec3 inP)
    {
        // Rotate the point into the box's coordinates
        Vec4 p = m.getInverseSimple().multiply(inP.toPoint());

        // Now just use an axis-aligned check
        if (abs(p.getX()) < extents.getX()
                && abs(p.getY()) < extents.getY()
                && abs(p.getZ()) < extents.getZ())
        {
            return true;
        }

        return false;
    }

    /**
     * Check if a sphere overlaps any part of this bounding box.
     *
     * @param inP the center of the sphere.
     * @param fRadius the radius of the sphere.
     * @return true if the sphere overlaps with the box, false otherwise.
     */
    public boolean isSphereInBox(final Vec3 inP, float fRadius)
    {
        float fDist;
        float fDistSq = 0;
        Vec4 p = m.getInverseSimple().multiply(inP.toPoint());

        // Add distance squared from sphere centerpoint to box for each axis
        for (int i = 0; i < 3; i++)
        {
            if (abs(p.get(i)) > extents.get(i))
            {
                fDist = abs(p.get(i)) - extents.get(i);
                fDistSq += fDist * fDist;
            }
        }


        return (fDistSq <= fRadius * fRadius);
    }

    /**
     * Check if the bounding box is completely behind a plane (defined by a
     * normal and a point).
     *
     * @param inNorm the normal of the plane.
     * @param inP a point on the plane.
     * @return true if the box is completely behind a plane, false otherwise.
     */
    public boolean boxOutsidePlane(final Vec3 inNorm, final Vec3 inP)
    {
        // Plane Normal in Box Space
        Vec3 norm = m.getInverseSimple().roatateVector(inNorm); // roatateVector only uses rotation portion of matrix
        norm = new Vec3(abs(norm.getX()), abs(norm.getY()), abs(norm.getZ()));

        float extent = norm.dot(extents); // Box Extent along the plane normal
        float distance = inNorm.dot(getCenterPoint().subtract(inP)); // Distance from Box Center to the Plane

        // If Box Centerpoint is behind the plane further than its extent, the Box is outside the plane
        if (distance < -extent)
        {
            return true;
        }
        return false;
    }

    /**
     * Does the Line (l1, l2) intersect the Box?
     *
     * @param l1 first point of line segment.
     * @param l2 second point of line segment.
     * @return true if line (l1, l2) intersects the box.
     */
    public boolean isLineInBox(final Vec3 l1, final Vec3 l2)
    {
        // Put line in box space
        Mat4 mInv = m.getInverseSimple();
        Vec4 lb1 = mInv.multiply(l1.toPoint());
        Vec4 lb2 = mInv.multiply(l2.toPoint());

        // Get line midpoint and extent
        Vec4 lMid = (lb1.add(lb2)).multiply(0.5f);
        Vec4 l = lb1.subtract(lMid);
        Vec3 lExt = new Vec3(abs(l.getX()), abs(l.getY()), abs(l.getZ()));

        // Use Separating Axis Test
        // Separation vector from box center to line center is LMid, since the line is in box space
        for (int i = 0; i < 3; i++)
        {
            if (abs(lMid.get(i)) > extents.get(i) + lExt.get(i))
            {
                return false;
            }
        }

        // Crossproducts of line and each axis
        if ((abs(lMid.getY() * l.getZ()) - lMid.getZ() * l.getY()) > (extents.getY() * lExt.getZ() + extents.getZ() * lExt.getY()))
        {
            return false;
        }
        if (abs(lMid.getX() * l.getZ() - lMid.getZ() * l.getX()) > (extents.getX() * lExt.getZ() + extents.getZ() * lExt.getX()))
        {
            return false;
        }
        if (abs(lMid.getX() * l.getY() - lMid.getY() * l.getX()) > (extents.getX() * lExt.getY() + extents.getY() * lExt.getX()))
        {
            return false;
        }
        // No separating axis, the line intersects
        return true;
    }

    /**
     * Returns a 3x3 rotation matrix as vectors.
     *
     * @retrun an array of vectors containing the rotation part of the matrix.
     */
    Vec3[] getInvRot()
    {
        Vec3[] pvRot = new Vec3[3];
        pvRot[0] = m.<Vec4>getColumn(0).getXYZ();
        pvRot[1] = m.<Vec4>getColumn(1).getXYZ();
        pvRot[2] = m.<Vec4>getColumn(2).getXYZ();
        return pvRot;
    }

    /**
     * Check if any part of a box is inside any part of another box. Uses the
     * separating axis test.
     *
     * @param BBox the other box to check.
     * @return true if any part of the given box is inside this box.
     */
    public boolean isBoxInBox(BBox BBox)
    {
        Vec3 sizeA = extents;
        Vec3 sizeB = BBox.extents;
//        System.out.println("sizeA: " + sizeA);
//        System.out.println("mA: " + m);
//        System.out.println("sizeB: " + sizeB);
//        System.out.println("mB: " + BBox.m);
        Vec3[] rotA, rotB;
        rotA = getInvRot();
        rotB = BBox.getInvRot();

        float[][] r = new float[3][3];  // Rotation from B to A
        float[][] ar = new float[3][3]; // absolute values of R matrix, to use with box extents
        float extentA, extentB, separation;
        int i, k;

        // Calculate B to A rotation matrix
        for (i = 0; i < 3; i++)
        {
            for (k = 0; k < 3; k++)
            {
                r[i][k] = rotA[i].dot(rotB[k]);
                ar[i][k] = abs(r[i][k]);
            }
        }

        // Vector separating the centers of Box B and of Box A	
        Vec3 vSepWS = BBox.getCenterPoint().subtract(getCenterPoint());
        // Rotated into Box A's coordinates
        Vec3 vSepA = new Vec3(
                vSepWS.dot(rotA[0]),
                vSepWS.dot(rotA[1]),
                vSepWS.dot(rotA[2]));

        // Test if any of A's basis vectors separate the box
        for (i = 0; i < 3; i++)
        {
            extentA = sizeA.get(i);
            extentB = sizeB.dot(new Vec3(ar[i][0], ar[i][1], ar[i][2]));
            separation = abs(vSepA.get(i));

            if (separation > extentA + extentB)
            {
                return false;
            }
        }

        // Test if any of B's basis vectors separate the box
        for (k = 0; k < 3; k++)
        {
            extentA = sizeA.dot(new Vec3(ar[0][k], ar[1][k], ar[2][k]));
            extentB = sizeB.get(k);
            separation = abs(vSepA.dot(new Vec3(r[0][k], r[1][k], r[2][k])));

            if (separation > extentA + extentB)
            {
                return false;
            }
        }

        // Now test Cross Products of each basis vector combination ( A[i], B[k] )
        for (i = 0; i < 3; i++)
        {
            for (k = 0; k < 3; k++)
            {
                int i1 = (i + 1) % 3, i2 = (i + 2) % 3;
                int k1 = (k + 1) % 3, k2 = (k + 2) % 3;
                extentA = sizeA.get(i1) * ar[i2][k] + sizeA.get(i2) * ar[i1][k];
                extentB = sizeB.get(k1) * ar[i][k2] + sizeB.get(k2) * ar[i][k1];
                separation = abs(vSepA.get(i2) * r[i1][k] - vSepA.get(i1) * r[i2][k]);
                if (separation > extentA + extentB)
                {
                    return false;
                }
            }
        }

        // No separating axis found, the boxes overlap	
        return true;
    }

    public float getEntrancePointDistance(Vec3 org, Vec3 ray)
    {
        List<Vec3> ps = getIntersectionPoints(org, ray);
        
        if (ps.isEmpty())
        {
            return Float.POSITIVE_INFINITY;
        }
        float cdist = Float.POSITIVE_INFINITY;
        for (int i = 0; i < ps.size(); i++)
        {
            float dist = ps.get(i).subtract(org).getLengthSquared();
            if (dist < cdist)
            {
                cdist = dist;
            }
        }
        return FastMath.sqrtFast(cdist);
    }
    
    public List<Vec3> getIntersectionPoints(Vec3 org, Vec3 ray)
    {
        // Put ray in box space
        Mat4 mInv = m.getInverseSimple();
        ray = mInv.multiply(ray.toDirection()).getXYZ();
        org = mInv.multiply(org.toPoint()).getXYZ();

        Vec3 mext = extents.getNegated();
        ArrayList<Vec3> ps = new ArrayList<Vec3>();
        for (int i = 0; i < 3; i++)
        {
            if (org.get(i) >= extents.get(i))
            {
                if (ray.get(i) >= 0)
                {
                    return new ArrayList<Vec3>(); // ray points away from box
                }
                ps.addAll(lineIntersection(i, org, ray, extents));
            } else if (org.get(i) <= mext.get(i))
            {
                if (ray.get(i) <= 0)
                {
                    return new ArrayList<Vec3>(); // ray points away from box
                }
                ps.addAll(lineIntersection(i, org, ray, mext));
            }
        }
        return ps;
    }

    private ArrayList<Vec3> lineIntersection(int xi, Vec3 org,
            Vec3 ray, Vec3 lext)
    {
        ArrayList<Vec3> ps = new ArrayList<Vec3>();

        int yi = (xi + 2) % 3;
        int zi = (xi + 1) % 3;

        final float JZERO = 0.00000000001f;

        float t0 = (lext.get(xi) - org.get(xi))
                / (ray.get(xi) == 0 ? JZERO : ray.get(xi));
        float t1 = (lext.get(yi) - org.get(yi))
                / (ray.get(yi) == 0 ? JZERO : ray.get(yi));
        float t2 = (lext.get(zi) - org.get(zi))
                / (ray.get(zi) == 0 ? JZERO : ray.get(zi));

        float[] r = new float[3];
        r[xi] = lext.get(xi);
        r[yi] = org.get(yi) + ray.get(yi) * t0;
        r[zi] = org.get(zi) + ray.get(zi) * t0;

        if (t0 > 0
                && Math.abs(r[yi]) <= extents.get(yi)
                && Math.abs(r[zi]) <= extents.get(zi))
        {
            ps.add(m.multiply(new Vec4(r[0], r[1], r[2],1.0f)).getXYZ());
        }

        r = new float[3];
        r[xi] = org.get(xi) + ray.get(xi) * t1;
        r[yi] = lext.get(yi);
        r[zi] = org.get(zi) + ray.get(zi) * t1;

        if (t1 > 0
                && Math.abs(r[xi]) <= extents.get(xi)
                && Math.abs(r[zi]) <= extents.get(zi))
        {
            ps.add(m.multiply(new Vec4(r[0], r[1], r[2],1.0f)).getXYZ());
        }

        r = new float[3];
        r[xi] = org.get(xi) + ray.get(xi) * t2;
        r[yi] = org.get(yi) + ray.get(yi) * t2;
        r[zi] = lext.get(zi);

        if (t2 > 0
                && Math.abs(r[xi]) <= extents.get(xi)
                && Math.abs(r[yi]) <= extents.get(yi))
        {
            ps.add(m.multiply(new Vec4(r[0], r[1], r[2],1.0f)).getXYZ());
        }

        return ps;
    }
}
