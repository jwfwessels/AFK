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
 package afk.ge.tokyo;

import afk.ge.tokyo.ems.components.Heightmap;
import com.hackoeur.jglm.Vec3;
import com.hackoeur.jglm.support.FastMath;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
 *
 * @author Daniel
 */
public class HeightmapLoader
{

    public Heightmap load(String name, float width, float length, float height) throws IOException
    {
        Heightmap hm = new Heightmap();

        hm.width = width;
        hm.length = length;
        hm.height = height;

        hm.heightmap = ImageIO.read(
                new File("textures/heightmaps/" + name + ".png"));

        hm.xGrid = hm.heightmap.getWidth();
        hm.yGrid = hm.heightmap.getHeight();

        final float QUAD_WIDTH = 1.0f / (float) hm.xGrid;
        final float QUAD_LENGTH = 1.0f / (float) hm.yGrid;

        hm.vertices = new float[hm.xGrid][hm.yGrid][3];
        hm.normals = new float[hm.xGrid][hm.yGrid][3];

        float x, y, z;
        Vec3 normal;
        for (int i = 0; i < hm.xGrid; i++)
        {
            for (int j = 0; j < hm.yGrid; j++)
            {
                x = i * QUAD_WIDTH;
                y = h(i, j, hm);
                z = j * QUAD_LENGTH;

                float Hx = h(i < hm.xGrid - 1 ? i + 1 : i, j, hm) - h(i > 0 ? i - 1 : i, j, hm);
                if (i == 0 || i == hm.xGrid - 1)
                {
                    Hx *= 2;
                }
                Hx /= QUAD_WIDTH;

                float Hz = h(i, j < hm.yGrid - 1 ? j + 1 : j, hm) - h(i, j > 0 ? j - 1 : j, hm);
                if (j == 0 || j == hm.yGrid - 1)
                {
                    Hz *= 2;
                }
                Hz /= QUAD_LENGTH;

                normal = new Vec3(-Hx, 1.0f, -Hz).getUnitVector();

                hm.vertices[i][j][0] = (x - 0.5f);
                hm.vertices[i][j][1] = (y - 0.5f);
                hm.vertices[i][j][2] = (z - 0.5f);

                hm.normals[i][j][0] = normal.getX();
                hm.normals[i][j][1] = normal.getX();
                hm.normals[i][j][2] = normal.getX();
            }
        }

        return hm;
    }

    public static float samplei(int u, int v, Heightmap hm)
    {
        if (u < 0)
        {
            u = 0;
        }
        if (v < 0)
        {
            v = 0;
        }
        if (u >= hm.heightmap.getWidth())
        {
            u = hm.heightmap.getWidth() - 1;
        }
        if (v >= hm.heightmap.getHeight())
        {
            v = hm.heightmap.getHeight() - 1;
        }
        return (float) (new Color(hm.heightmap.getRGB(u, v)).getRed()) / 256.0f;
    }

    public static float samplef(float u, float v, Heightmap hm)
    {
        if (u < 0)
        {
            u = 0;
        }
        if (u > 1)
        {
            u = 1;
        }
        if (v < 0)
        {
            v = 0;
        }
        if (v > 1)
        {
            v = 1;
        }

        u = u * hm.heightmap.getWidth() - 0.5f;
        v = v * hm.heightmap.getHeight() - 0.5f;
        int x = (int) (u);
        int y = (int) (v);
        float u_ratio = u - x;
        float v_ratio = v - y;
        float u_opposite = 1 - u_ratio;
        float v_opposite = 1 - v_ratio;

        return (samplei(x, y, hm) * u_opposite + samplei(x + 1, y, hm) * u_ratio) * v_opposite
                + (samplei(x, y + 1, hm) * u_opposite + samplei(x + 1, y + 1, hm) * u_ratio) * v_ratio;
    }

    public static float h(int i, int j, Heightmap hm)
    {
        return samplef((float) i / (float) (hm.xGrid - 1), (float) j / (float) (hm.yGrid - 1), hm);
    }

    public static float getHeight(float x, float y, Heightmap hm)
    {
        x += hm.width / 2;
        y += hm.length / 2;
        return hm.height * (samplef(x / hm.width, y / hm.length, hm) - 0.5f);
    }

    public static Vec3 getNormal(float x, float y, Heightmap hm)
    {
        final float QUAD_WIDTH = hm.width / hm.xGrid;
        final float QUAD_LENGTH = hm.length / hm.yGrid;

        float Hx = getHeight(x + QUAD_WIDTH, y, hm) - getHeight(x - QUAD_WIDTH, y, hm);
        Hx /= QUAD_WIDTH * 2;

        float Hz = getHeight(x, y + QUAD_LENGTH, hm) - getHeight(x, y - QUAD_LENGTH, hm);
        Hz /= QUAD_LENGTH * 2;

        return new Vec3(-Hx, 1.0f, -Hz).getUnitVector();
    }

    /**
     * Checks if the given point lies below the terrain.
     * @param v the point.
     * @param hm the heightmap representing the terrain.
     * @return true if the point lies under the terrain, false otherwise.
     */
    public static boolean under(Vec3 v, Heightmap hm)
    {
        return v.getY() < getHeight(v.getX(), v.getZ(), hm);
    }

    /**
     * Finds the intersections between the given line segment and the heightmap using
     * a brute force raymarching algorithm.
     *
     * @param a
     * @param b
     * @param accuracy
     * @param hm
     * @return an array containing all the intersections between the line segment and the heigtmap
     */
    public static Vec3[] getIntersections(Vec3 a, Vec3 b, float accuracy, Heightmap hm)
    {
        Vec3 v = b.subtract(a);
        float len = v.getLength();
        int n = (int) (len / accuracy);
        v = v.multiply(accuracy / len);
        boolean under = under(a, hm);
        Vec3 temp = a.add(v);
        ArrayList<Vec3> intersections = new ArrayList<Vec3>();
        for (int i = 0; i < n; i++)
        {
            boolean under2 = under(temp, hm);
            if (under != under2)
            {
                intersections.add(temp);
            }
            under = under2;
            temp = temp.add(v);
        }
        if (under != under(b,hm))
        {
            intersections.add(b);
        }
        return intersections.toArray(new Vec3[0]);
    }
    
    /**
     * Finds the first intersection of the given line segment to the heightmap using
     * a brute force raymarching algorithm. Faster than getIntersections but only returns a single result.
     *
     * @param a
     * @param b
     * @param accuracy
     * @param hm
     * @return the first intersection between the segment at the heightmap, null if the segment does not intersect.
     */
    public static Vec3 getIntersection(Vec3 a, Vec3 b, float accuracy, Heightmap hm)
    {
        Vec3 v = b.subtract(a);
        float len = v.getLength();
        int n = (int) (len / accuracy);
        v = v.multiply(accuracy / len);
        boolean under = under(a, hm);
        if (under) return a;
        Vec3 temp = a.add(v);
        ArrayList<Vec3> intersections = new ArrayList<Vec3>();
        for (int i = 0; i < n; i++)
        {
            boolean under2 = under(temp, hm);
            if (under != under2)
            {
                return temp;
            }
            under = under2;
            temp = temp.add(v);
        }
        if (under != under(b,hm))
        {
            return b;
        }
        return null;
    }
}
