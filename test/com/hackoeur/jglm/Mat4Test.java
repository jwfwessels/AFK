/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hackoeur.jglm;

import java.nio.FloatBuffer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author daniel
 */
public class Mat4Test
{
    
    public Mat4Test()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of multiply method, of class Mat4.
     */
    @Test
    public void testMultiply_Mat4()
    {
        System.out.println("multiply");
        Mat4 m = new Mat4(
            12, -23, 14, 8,
            22, 0, -1, 1,
            9, 18, 8, 0,
            19, -17, 3, 4
        ).transpose();
        Mat4 instance = new Mat4(
            45, 7, -32, 4,
            0, 23, 4, -4,
            1, -7, 18, 20,
            2, 2, 6, 0
        ).transpose();
        Mat4 expResult = new Mat4(
            482, -1679,   379,   383,
            466,   140,    -3,     7,
            400,   -39,   225,    81,
            122,    62,    74,    18
        ).transpose();
        Mat4 result = instance.multiply(m);
        assertEquals(expResult, result);
    }
}