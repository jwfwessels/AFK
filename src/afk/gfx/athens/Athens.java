package afk.gfx.athens;

import afk.ge.tokyo.ems.components.ImageComponent;
import afk.ge.tokyo.ems.components.Renderable;
import afk.gfx.AbstractCamera;
import afk.gfx.PerspectiveCamera;
import afk.gfx.GfxEntity;
import afk.gfx.GfxHUD;
import afk.gfx.GfxListener;
import afk.gfx.GraphicsEngine;
import com.hackoeur.jglm.*;
import com.jogamp.opengl.util.Animator;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collection;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JLabel;
import static afk.gfx.GfxUtils.*;
import afk.gfx.HUDCamera;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * OpenGL 2.0 Implementation of the AFK Graphics Engine.
 *
 * @author Daniel
 */
public class Athens implements GraphicsEngine
{
    protected Collection<GfxListener> listeners = new ArrayList<GfxListener>();
    protected ResourceManager resourceManager;
    protected TypeFactory typeFactory;
    protected Map<Renderable, AthensEntity> entities = new LinkedHashMap<Renderable, AthensEntity>();
    protected Map<ImageComponent, AthensHUD> huds = new LinkedHashMap<ImageComponent, AthensHUD>();
    protected List<Renderable> removed = new ArrayList<Renderable>();
    private int w_width, w_height;
    private float aspect;
    // TODO: 1024 is enough, but there are some obscure codes in the region of 65000 that WILL make this program crash
    private boolean[] keys = new boolean[1024];
    private long frameCount = 0;
    private long lastUpdate;
    private float time = 0.0f;
    private float lastFPS = 0.0f;
    private float fpsInterval = 1.0f;
    AbstractCamera camera;
    HUDCamera hudCamera;
    Mat4 monkeyWorld, skyboxWorld;
    // TODO: move this to a sun/light position
    Vec3 origin_sun = new Vec3(-2.0f, 1.5f, 5.0f);
    Vec3 sun = new Vec3(origin_sun);
    float daytime = 0.0f;
    static final float MOUSE_SENSITIVITY = 60.0f;
    /* Amount to move / scale by in one step. */
    static final float DELTA = 5f, ANGLE_DELTA = 30.0f;
    private GLProfile glProfile;
    private GLCapabilities glCaps;
    private GLCanvas glCanvas;
//    private GLJPanel glCanvas;
    private Animator animator;
    private float fps = 0.0f;
    private JLabel fpsComp;

    public Athens(boolean autodraw)
    {
        resourceManager = new ResourceManager();
        typeFactory = new TypeFactory(resourceManager);
        
        glProfile = GLProfile.getDefault();

        glCaps = new GLCapabilities(glProfile);
        glCaps.setDoubleBuffered(true);

        glCanvas = new GLCanvas(glCaps);
//        glCanvas = new GLJPanel(glCaps);

        KeyAdapter key = new KeyAdapter()
        {
            @Override
            public void keyTyped(KeyEvent e)
            {
                Athens.this.keyPressed(e);
            }

            @Override
            public void keyPressed(KeyEvent e)
            {
                Athens.this.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                Athens.this.keyReleased(e);
            }
        };

        glCanvas.addKeyListener(key);

        MouseAdapter mouse = new MouseAdapter()
        {
            int cx = glCanvas.getWidth() / 2;
            int cy = glCanvas.getHeight() / 2;

            @Override
            public void mousePressed(MouseEvent e)
            {


                cx = e.getX();
                cy = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (e.getX() == cx && e.getY() == cy)
                {
                    return;
                }

                Athens.this.mouseMoved(cx - e.getX(), cy - e.getY());

                cx = e.getX();
                cy = e.getY();
            }
        };

        glCanvas.addMouseListener(mouse);
        glCanvas.addMouseMotionListener(mouse);

        glCanvas.addGLEventListener(new GLEventListener()
        {
            @Override
            public void reshape(GLAutoDrawable glautodrawable, int x, int y, int width, int height)
            {
                Athens.this.reshape(glautodrawable.getGL().getGL2(), width, height);
            }

            @Override
            public void init(GLAutoDrawable glautodrawable)
            {
                GL gl = glautodrawable.getGL().getGL2();
                System.out.println("OpenGL Version: " + gl.glGetString(GL.GL_VERSION));
                Athens.this.init(gl.getGL2());
            }

            @Override
            public void dispose(GLAutoDrawable glautodrawable)
            {
                Athens.this.dispose(glautodrawable.getGL().getGL2());
            }

            @Override
            public void display(GLAutoDrawable glautodrawable)
            {
                Athens.this.display(glautodrawable.getGL().getGL2());
            }
        });

        if (autodraw)
        {
            animator = new Animator(glCanvas);
            animator.start();
        }

    }

    @Override
    public Component getAWTComponent()
    {

        return glCanvas;
    }

    public long getFrameCount()
    {
        return frameCount;
    }

    @Override
    public void redisplay()
    {
        glCanvas.display();
    }
    
    @Override
    public void addGfxEventListener(GfxListener listener)
    {
        listeners.add(listener);
    }
    
    @Override
    public void removeGfxEventListener(GfxListener listener)
    {
        listeners.remove(listener);
    }

    private void dispose(GL2 gl)
    {
        resourceManager.dispose(gl);
    }

    private void display(GL2 gl)
    {
        long nTime = System.nanoTime();
        long nanos = nTime - lastUpdate;
        lastUpdate = nTime;
        float delta = nanos / (float) NANOS_PER_SECOND;
        time += delta;
        lastFPS += delta;

        if (fpsComp != null && lastFPS >= fpsInterval)
        {
            fps = (1.0f / delta);
            fpsComp.setText(String.format("FPS: %.0f", fps));
            lastFPS = 0;
        }
        
        resourceManager.update(gl);
        /// FIXME: this should go somewhere else
        for (AthensHUD hud : huds.values())
        {
            if (!hud.isLoaded())
                try { hud.load(gl); }
                catch (IOException ioe) {}
            if (hud.isUpdated())
                hud.setup(gl);
        }
        ///
        update(delta);

        for (GfxListener l : listeners)
        {
            l.update(delta);
        }

        render(gl);
        frameCount++;
    }

    private void update(float delta)
    {

        float amount = DELTA * delta;

        if (keys[KeyEvent.VK_SHIFT])
        {
            amount *= 5;
        }

        if (keys[KeyEvent.VK_W])
        {
            camera.moveForward(amount);
        } else if (keys[KeyEvent.VK_S])
        {
            camera.moveForward(-amount);
        }

        if (keys[KeyEvent.VK_D])
        {
            camera.moveRight(amount);
        } else if (keys[KeyEvent.VK_A])
        {
            camera.moveRight(-amount);
        }

        if (keys[KeyEvent.VK_2])
        {
            daytime += 1.0f;
            updateSun();
        } else if (keys[KeyEvent.VK_3])
        {
            daytime -= 1.0f;
            updateSun();
        }

        updateView();
        
        for (Renderable r : removed)
            entities.remove(r);
        removed.clear();

        for (AthensEntity entity : entities.values())
            entity.update(delta);
    }

    private void render(GL2 gl)
    {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        
        gl.glEnable(GL.GL_DEPTH_TEST);

        renderScene(gl, camera);
        
        gl.glDisable(GL.GL_DEPTH_TEST);
        
        renderHUD(gl, hudCamera);

        gl.glFlush();
    }

    private void renderScene(GL2 gl, AbstractCamera camera)
    {
        for (AthensEntity entity : entities.values())
        {
            entity.draw(gl, camera, sun);
        }
    }
    
    private void renderHUD(GL2 gl, HUDCamera hudCamera)
    {
        for (AthensHUD hud : huds.values())
        {
            hud.draw(gl, hudCamera);
        }
    }

    private void init(GL2 gl)
    {
        System.out.println("Initialising");

        gl.glEnable(GL.GL_CULL_FACE);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);

        // set background colour to white
        // TODO: allow this to be set through an interface
        gl.glClearColor(87.0f/256.0f, 220.0f/256.0f, 225.0f/256.0f, 0.0f);

        // initialize camera
        // TODO: allow this to be done through an interface and let additional cameras be set
        camera = new PerspectiveCamera(
                new Vec3(10f, 10f, 10f),
                new Vec3(0f, 0f, 0f),
                new Vec3(0f, 1f, 0f),
                60.0f, 0.1f, 100.0f);
        
        hudCamera = new HUDCamera(0, w_height, 0, w_width); 
        hudCamera.updateView();

        // initial setup of matrices
        updateProjection(w_width, w_height);
        updateView();

        // initialise update time with current time
        lastUpdate = System.nanoTime();
    }

    private void keyPressed(KeyEvent ke)
    {
        if (ke.getKeyCode() < keys.length)
        {
            keys[ke.getKeyCode()] = true;
        }

        switch (ke.getKeyCode())
        {
            default:
                break;
        }
    }

    @Override
    public boolean isKeyDown(int keyCode)
    {
        return keys[keyCode];
    }

    private void keyReleased(KeyEvent ke)
    {
        if (ke.getKeyCode() < keys.length)
        {
            keys[ke.getKeyCode()] = false;
        }
    }

    private void mouseMoved(int x, int y)
    {
        final float LEFT_RIGHT_ROT = (2.0f * (float) x / (float) w_width) * MOUSE_SENSITIVITY;
        final float UP_DOWN_ROT = (2.0f * (float) y / (float) w_height) * MOUSE_SENSITIVITY;

        camera.rotate(LEFT_RIGHT_ROT, UP_DOWN_ROT);

        updateView();

    }

    @Override
    public boolean isMouseDown(int button)
    {
        // TODO: implement isMouseDown
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMouseX()
    {
        // TODO: implement getMouseX
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getMouseY()
    {
        // TODO: implement getMouseY
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private void reshape(GL2 gl, int newWidth, int newHeight)
    {
        w_width = newWidth;
        w_height = newHeight;

        /* Correct the viewport. */
        gl.glViewport(0, 0, newWidth, newHeight);

        /* Correct the projection matrix. */
        updateProjection(newWidth, newHeight);

        /* Note the new width and height of the window. */
        System.out.printf("Resized. New width = %d and new height = %d.\n", newWidth, newHeight);
    }

    private void updateProjection(int width, int height)
    {
        camera.updateProjection(width, height);
        hudCamera.updateProjection(width, height);
    }

    private void updateView()
    {
        camera.updateView();

        // shift skybox with camera
        skyboxWorld = new Mat4(1.0f);
        skyboxWorld = Matrices.translate(skyboxWorld, camera.eye);
        // also scale it a bit so it doesn't intersect with near clip
        skyboxWorld = Matrices.scale(skyboxWorld, new Vec3(1.5f, 1.5f, 1.5f));
    }

    private void updateSun()
    {
        Mat4 sunWorld = new Mat4(1.0f);

        sunWorld = Matrices.rotate(sunWorld, daytime, new Vec3(1, 0.2f, 0).getUnitVector());

        Vec4 sun4 = sunWorld.multiply(new Vec4(origin_sun, 0.0f));

        sun = new Vec3(sun4.getX(), sun4.getY(), sun4.getZ());
    }

    @Override
    public float getFPS()
    {
        return fps;
    }

    @Override
    public void setFPSComponent(Component comp)
    {
        fpsComp = (JLabel) comp;
    }

    @Override
    public void prime()
    {
        for (AthensEntity entity : entities.values())
        {
            entity.used = false;
        }
    }

    @Override
    public GfxEntity getGfxEntity(Renderable renderable)
    {
        AthensEntity entity = entities.get(renderable);
        if (entity == null)
        {
            entity = typeFactory.createInstance(renderable.type);
            entities.put(renderable, entity);
        }
        entity.used = true;
        return entity;
    }
    
    @Override
    public GfxHUD getGfxHUD(ImageComponent image)
    {
        AthensHUD hud = huds.get(image);
        if (hud == null)
        {
            hud = new AthensHUD(image.getImage());
            huds.put(image, hud);
        }
        return hud;
    }

    @Override
    public void post()
    {
        for (Map.Entry<Renderable, AthensEntity> e : entities.entrySet())
        {
            if (!e.getValue().used) removed.add(e.getKey());
        }
    }
    
}
