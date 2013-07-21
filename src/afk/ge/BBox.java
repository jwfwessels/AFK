package afk.ge;

import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.Vec4;
import static com.hackoeur.jglm.support.FastMath.*;

/**
 * Oriented Bounding Box. Stored as a matrix( without scaling ) and Extents( x,
 * y, z )
 *
 * @author daniel, from http://www.3dkingdoms.com/weekly/weekly.php?a=21
 */
public class BBox
{

    private Mat4 m;
    private Vec3 extents;
    
    BBox() {}
    BBox( final Mat4 M, final Vec3 Extent ) 
            { Set( M, Extent );	}
    // BL = Low values corner point, BH = High values corner point
    BBox( final Mat4 M, final Vec3 BL, final Vec3 BH ) 
            { Set( M, BL, BH );	}

    public void Set( final Mat4 M, final Vec3 Extent )
    {
     m = M;
     extents = Extent;
    }	
    public void Set( final Mat4 M, final Vec3 BL, final Vec3 BH )
    {
     m = M;
     m.translate((BH.add(BL)).multiply(0.5f));
     extents = (BH.subtract(BL)).multiply(0.5f);
    }

    Vec3 GetSize() 
            { return extents.multiply(2.0f); }
    Vec3 getCenterPoint() 
            { return m.getTranslate(); }

    /**
     * Check if a point is in this bounding box.
     *
     * @param inP the point to check.
     * @return true if the point is within the box, false otherwise.
     */
    public boolean isPointInBox(final Vec3 inP)
    {
        // Rotate the point into the box's coordinates
        Vec4 P = m.invertSimple().multiply(inP.toPoint());

        // Now just use an axis-aligned check
        if (abs(P.getX()) < extents.getX() && abs(P.getY()) < extents.getY() && abs(P.getZ()) < extents.getZ())
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
        Vec4 P = m.invertSimple().multiply(inP.toPoint());

        // Add distance squared from sphere centerpoint to box for each axis
        for (int i = 0; i < 3; i++)
        {
            if (abs(P.get(i)) > extents.get(i))
            {
                fDist = abs(P.get(i)) - extents.get(i);
                fDistSq += fDist * fDist;
            }
        }


        return (fDistSq <= fRadius * fRadius);
    }

    /* TODO: requires "RotByMatrix" function
    /**
     * Check if the bounding box is completely behind a plane( defined by a
     * normal and a point ).
     *
    public boolean boxOutsidePlane(final Vec3 InNorm, final Vec3 InP)
    {
        // Plane Normal in Box Space
        Vec3 Norm = InNorm.RotByMatrix(m.InvertSimple().mf); // RotByMatrix only uses rotation portion of matrix
        Norm = Vec3(abs(Norm.x), abs(Norm.y), abs(Norm.z));

        float Extent = Norm.Dot(extents); // Box Extent along the plane normal
        float Distance = InNorm.Dot(GetCenterPoint() - InP); // Distance from Box Center to the Plane

        // If Box Centerpoint is behind the plane further than its extent, the Box is outside the plane
        if (Distance < -Extent)
        {
            return true;
        }
        return false;
    }
    */

    /**
     * Does the Line (L1, L2) intersect the Box?
     * @param L1 first point of line segment.
     * @param L2 second point of line segment.
     * @return true if line (L1, L2) intersects the box.
     */
    public boolean isLineInBox(final Vec3 L1, final Vec3 L2 )
{	
	// Put line in box space
	Mat4 MInv = m.invertSimple();
        Vec4 LB1 = MInv.multiply(L1.toPoint());
        Vec4 LB2 = MInv.multiply(L2.toPoint());

        // Get line midpoint and extent
        Vec4 LMid = (LB1.add(LB2)).multiply(0.5f);
        Vec4 L = LB1.subtract(LMid);
        Vec3 LExt = new Vec3(abs(L.getX()), abs(L.getY()), abs(L.getZ()));

        // Use Separating Axis Test
        // Separation vector from box center to line center is LMid, since the line is in box space
        for (int i = 0; i < 3; i++)
        {
            if (abs(LMid.get(i)) > extents.get(i) + LExt.get(i))
            {
                return false;
            }
        }
        
        // Crossproducts of line and each axis
        if ((abs(LMid.getY() * L.getZ()) - LMid.getZ() * L.getY()) > (extents.getY() * LExt.getZ() + extents.getZ() * LExt.getY()))
        {
            return false;
        }
        if (abs(LMid.getX() * L.getZ() - LMid.getZ() * L.getX()) > (extents.getX() * LExt.getZ() + extents.getZ() * LExt.getX()))
        {
            return false;
        }
        if (abs(LMid.getX() * L.getY() - LMid.getY() * L.getX()) > (extents.getX() * LExt.getY() + extents.getY() * LExt.getX()))
        {
            return false;
        }
        // No separating axis, the line intersects
        return true;
    }
    
    //
    // Returns a 3x3 rotation matrix as vectors
    //
    Vec3[] getInvRot()
    {
        Vec3[] pvRot = new Vec3[3];
	pvRot[0] = m.<Vec4>getColumn(0).getXYZ();
        pvRot[1] = m.<Vec4>getColumn(1).getXYZ();
        pvRot[2] = m.<Vec4>getColumn(2).getXYZ();
        return pvRot;
    }

    //
    // Check if any part of a box is inside any part of another box
    // Uses the separating axis test.
    //
    boolean isBoxInBox(BBox BBox )
    {
	Vec3 SizeA = extents;
        Vec3 SizeB = BBox.extents;
        Vec3[] RotA, RotB;
        RotA = getInvRot();
        RotB = BBox.getInvRot();

        float[][] R = new float[3][3];  // Rotation from B to A
        float[][] AR = new float[3][3]; // absolute values of R matrix, to use with box extents
        float ExtentA, ExtentB, Separation;
        int i, k;

        // Calculate B to A rotation matrix
        for (i = 0; i < 3; i++)
        {
            for (k = 0; k < 3; k++)
            {
                R[i][k] = RotA[i].dot(RotB[k]);
                AR[i][k] = abs(R[i][k]);
            }
        }

        // Vector separating the centers of Box B and of Box A	
        Vec3 vSepWS = BBox.getCenterPoint().subtract(getCenterPoint());
        // Rotated into Box A's coordinates
        Vec3 vSepA = new Vec3(
                vSepWS.dot(RotA[0]),
                vSepWS.dot(RotA[1]),
                vSepWS.dot(RotA[2])
            );

     // Test if any of A's basis vectors separate the box
        for( i = 0; i < 3; i++ )
	{
		ExtentA = SizeA.get(i);
		ExtentB = SizeB.dot( new Vec3( AR[i][0], AR[i][1], AR[i][2] ) );
		Separation = abs( vSepA.get(i) );

		if( Separation > ExtentA + ExtentB ) return false;
	}

        // Test if any of B's basis vectors separate the box
        for (k = 0; k < 3; k++)
        {
            ExtentA = SizeA.dot(new Vec3(AR[0][k], AR[1][k], AR[2][k]));
            ExtentB = SizeB.get(k);
            Separation = abs(vSepA.dot(new Vec3(R[0][k], R[1][k], R[2][k])));

            if (Separation > ExtentA + ExtentB)
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
                ExtentA = SizeA.get(i1) * AR[i2][k] + SizeA.get(i2) * AR[i1][k];
                ExtentB = SizeB.get(k1) * AR[i][k2] + SizeB.get(k2) * AR[i][k1];
                Separation = abs(vSepA.get(i2) * R[i1][k] - vSepA.get(i1) * R[i2][k]);
                if (Separation > ExtentA + ExtentB)
                {
                    return false;
                }
            }
        }

        // No separating axis found, the boxes overlap	
        return true;
    }
}
