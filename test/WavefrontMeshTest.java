/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author u11306026
 */
public class WavefrontMeshTest {
    
    @Test
    public void testParseVertex() {
        System.out.println("parse 'v'");
        String string = "7";
        int[] expResult = { 7, -1, -1};
        int[] result = WavefrontMesh.parseVertex(string);
        assertArrayEquals(expResult, result);
        
        System.out.println("parse 'v/vt'");
        string = "7/12";
        expResult = new int[]{ 7, 12, -1};
        result = WavefrontMesh.parseVertex(string);
        assertArrayEquals(expResult, result);
        
        System.out.println("parse 'v//vn'");
        string = "7//12";
        expResult = new int[]{ 7, -1, 12};
        result = WavefrontMesh.parseVertex(string);
        assertArrayEquals(expResult, result);
        
        System.out.println("parse 'v/vt/vn'");
        string = "7//12";
        expResult = new int[]{ 7, -1, 12};
        result = WavefrontMesh.parseVertex(string);
        assertArrayEquals(expResult, result);
    }
}
