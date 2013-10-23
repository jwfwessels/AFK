package afk.gfx.athens;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
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
public class AthensTest
{
    public JFrame testFrame;
    public Athens instance;
    
    public AthensTest()
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
        testFrame = new JFrame("test");
        testFrame.setSize(128, 128);
        instance = new Athens(false);
        testFrame.add(instance.getAWTComponent());
        testFrame.setVisible(true);
    }
    
    @After
    public void tearDown()
    {
        testFrame.dispose();
    }
    
    private void simulateEvent(AWTEvent e, Component c)
    {
        c.requestFocusInWindow();
        while (!c.hasFocus()) { /*spin*/ }
        testFrame.dispatchEvent(e);
    }

    /**
     * Test of getAWTComponent method, of class Athens.
     */
    @Test
    public void testGetAWTComponent()
    {
        System.out.println("getAWTComponent");
        Component result = instance.getAWTComponent();
        assertNotNull(result);
    }

    /**
     * Test of redisplay method, of class Athens.
     */
    @Test
    public void testRedisplay()
    {
        System.out.println("redisplay");
        long fc = instance.getFrameCount();
        instance.redisplay();
        long fc2 = instance.getFrameCount();
        
        assertEquals(fc2, fc+1);
    }

    /**
     * Test of isKeyDown method, of class Athens.
     */
    @Test
    public void testIsKeyDown()
    {
        System.out.println("isKeyDown");
        int keyCode = 83;
        Component comp = instance.getAWTComponent();
        
        KeyEvent down = new KeyEvent(comp, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, keyCode, 's');
        KeyEvent up = new KeyEvent(comp, KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(), 0, keyCode, 's');
        
        simulateEvent(down, comp);

        boolean expResult = true;
        boolean result = instance.isKeyDown(keyCode);
        assertEquals(expResult, result);

        simulateEvent(up, comp);
        expResult = false;
        result = instance.isKeyDown(keyCode);
        assertEquals(expResult, result);
    }

    /**
     * Test of isMouseDown method, of class Athens.
     */
    @Test
    public void testIsMouseDown()
    {
        System.out.println("isMouseDown");
        int button = 0;
        boolean expResult = false;
        boolean result = instance.isMouseDown(button);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMouseX method, of class Athens.
     */
    @Test
    public void testGetMouseX()
    {
        System.out.println("getMouseX");
        int expResult = 0;
        int result = instance.getMouseX();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getMouseY method, of class Athens.
     */
    @Test
    public void testGetMouseY()
    {
        System.out.println("getMouseY");
        int expResult = 0;
        int result = instance.getMouseY();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFPS method, of class Athens.
     */
    @Test
    public void testGetFPS()
    {
        System.out.println("getFPS");
        float result = instance.getFPS();
        assertTrue("fps negative", result >= 0);
    }
}