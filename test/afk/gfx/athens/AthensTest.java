package afk.gfx.athens;

import afk.gfx.GfxEntity;
import afk.gfx.Resource;
import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
    public JLabel testLabel;
    
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
        testLabel = new JLabel();
        instance = new Athens(false);
        instance.setFPSComponent(testLabel);
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
        try
        {
            Field f = AWTEvent.class.getField("focusManagerIsDispatching");
            f.setAccessible(true);
            f.set(e, Boolean.TRUE);
            c.dispatchEvent(e);
        }
        catch (NoSuchFieldException ex){}
        catch (SecurityException ex) {}
        catch (IllegalArgumentException ex) {}
        catch (IllegalAccessException ex) {}
    }
    
    private void load(Athens instance)
    {
        final AtomicBoolean loading = new AtomicBoolean(true);
        instance.dispatchLoadQueue(new Runnable() {

            @Override
            public void run()
            {
                loading.set(false);
            }
        });
        instance.redisplay();
        while (loading.get()) { /* spin */ }
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
        
        KeyEvent down = new KeyEvent(null, KeyEvent.KEY_PRESSED,
                System.currentTimeMillis(), 0, keyCode, 's');
        KeyEvent up = new KeyEvent(null, KeyEvent.KEY_RELEASED,
                System.currentTimeMillis(), 0, keyCode, 's');
        
        Component comp = instance.getAWTComponent();
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
     * Test of loadResource method, of class Athens.
     */
    @Test
    public void testLoadResource()
    {
        System.out.println("loadResource");
        int type = Resource.TEXTURE_2D;
        String name = "monkey";
        Resource expResult = AthensResource.create(type, name);
        Resource result = instance.loadResource(type, name);
        assertEquals(expResult, result);
        assertTrue("Resource not in load queue",
                instance.loadQueue.contains(result));
        assertFalse("Resource already loaded",
                result.isLoaded());
    }

    /**
     * Test of unloadResource method, of class Athens.
     */
    @Test
    public void testUnloadResource()
    {
        System.out.println("unloadResource");
        int type = Resource.TEXTURE_2D;
        String name = "monkey";
        Resource result = instance.loadResource(type, name);
        load(instance);
        instance.unloadResource(result);
        assertTrue("Resource not in unload queue",
                instance.unloadQueue.contains(result));
    }

    /**
     * Test of unloadEverything method, of class Athens.
     */
    @Test
    public void testUnloadEverything()
    {
        System.out.println("unloadEverything");
        instance.loadResource(Resource.TEXTURE_2D, "monkey");
        instance.loadResource(Resource.WAVEFRONT_MESH, "tank");
        load(instance);
        instance.unloadEverything();
        assertTrue("load queue not empty", instance.loadQueue.isEmpty());
        assertFalse("unload queue empty", instance.unloadQueue.isEmpty());
    }

    /**
     * Test of createEntity method, of class Athens.
     */
    @Test
    public void testCreateEntity()
    {
        System.out.println("createEntity");
        int behaviour = GfxEntity.NORMAL;
        GfxEntity result = instance.createEntity(behaviour);
        assertNotNull(result);
    }

    /**
     * Test of dispatchLoadQueue method, of class Athens.
     */
    @Test
    public void testDispatchLoadQueue()
    {
        System.out.println("dispatchLoadQueue");
        Resource resource =
                instance.loadResource(Resource.TEXTURE_2D, "monkey");
        assertFalse("resource already loaded", resource.isLoaded());
        load(instance);
        assertTrue("resource not loaded", resource.isLoaded());
    }

    /**
     * Test of getRootEntity method, of class Athens.
     */
    @Test
    public void testGetRootEntity()
    {
        System.out.println("getRootEntity");
        GfxEntity result = instance.getRootEntity();
        assertNotNull(result);
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