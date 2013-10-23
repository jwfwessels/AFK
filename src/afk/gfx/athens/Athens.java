package afk.gfx.athens;

import afk.ge.tokyo.ems.components.HUDImage;
import afk.ge.BBox;
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
import afk.gfx.Resource;
import java.util.HashMap;
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
    protected Map<HUDImage, AthensHUD> huds = new HashMap<HUDImage, AthensHUD>();
    protected List<Renderable> removed = new ArrayList<Renderable>();
    protected List<HUDImage> removedHUD = new ArrayList<HUDImage>();
    protected List<AthensEntity> entitiesDebug = new ArrayList<AthensEntity>();
    private int w_width, w_height;
    private boolean[] keys = new boolean[NUM_KEYS];
    private long frameCount = 0;
    private long lastUpdate;
    private float lastFPS = 0.0f;
    private float fpsInterval = 1.0f;
    AbstractCamera camera;
    HUDCamera hudCamera;
    Mat4 monkeyWorld, skyboxWorld;
    // TODO: move this to a sun/light position
    Vec3 origin_sun = new Vec3(-2.47511f, 3.87557f, 3.17864f);
    Vec3 sun = new Vec3(origin_sun);
    float daytime = 0.0f;
    static final float MOUSE_SENSITIVITY = 60.0f;
    private boolean[] mouses = new boolean[NUM_MOUSE_BUTTONS];
    private int mouseX = 0, mouseY = 0;
    /* Amount to move / scale by in one step. */
    static final float DELTA = 5f, ANGLE_DELTA = 30.0f;
    private GLProfile glProfile;
    private GLCapabilities glCaps;
    private GLCanvas glCanvas;
//    private GLJPanel glCanvas;
    private Animator animator;
    private float fps = 0.0f;
    private Vec3 bgColour = new Vec3(87.0f / 256.0f, 220.0f / 256.0f, 225.0f / 256.0f);
    private boolean init = false;

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
            public void mouseMoved(MouseEvent e)
            {
                mouseX = e.getX();
                mouseY = e.getY();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                cx = e.getX();
                cy = e.getY();

                mouses[e.getButton()] = true;
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                mouses[e.getButton()] = false;
            }

            @Override
            public void mouseDragged(MouseEvent e)
            {
                mouseX = e.getX();
                mouseY = e.getY();

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
        lastFPS += delta;

        fps = (1.0f / delta);

        resourceManager.update(gl);
        /// FIXME: this should go somewhere else
        for (AthensHUD hud : huds.values())
        {
            if (!hud.isLoaded())
            {
                try
                {
                    hud.load(gl);
                } catch (IOException ioe)
                {
                }
            }
            if (hud.isUpdated())
            {
                hud.bind(gl);
                hud.setup(gl);
            }
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

        for (Renderable o : removed)
        {
            entities.remove(o);
        }
        removed.clear();

        for (HUDImage r : removedHUD)
        {
            huds.remove(r);
        }
        removedHUD.clear();

        for (AthensEntity entity : entities.values())
        {
            entity.update(delta);
        }
    }

    private void render(GL2 gl)
    {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        gl.glDisable(GL.GL_BLEND);

        renderScene(gl, camera);

        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glDisable(GL.GL_CULL_FACE);
        gl.glEnable(GL.GL_BLEND);

        renderHUD(gl, hudCamera);

        gl.glFlush();
    }

    private void renderScene(GL2 gl, AbstractCamera camera)
    {
        for (AthensEntity entity : entities.values())
        {
            entity.draw(gl, camera, sun);
        }
        for (AthensEntity entity : entitiesDebug)
        {
            entity.draw(gl, camera, sun);
        }
        entitiesDebug.clear();
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
        gl.glClearColor(bgColour.getX(), bgColour.getY(), bgColour.getZ(), 0);

        // initialize camera
        // TODO: allow this to be done through an interface and let additional cameras be set
        camera = new PerspectiveCamera(
                new Vec3(10f, 10f, 10f),
                new Vec3(0f, 0f, 0f),
                new Vec3(0f, 1f, 0f),
                60.0f, 0.1f, 200.0f); // TODO: these constants need to go somewhere else!

        hudCamera = new HUDCamera(0, w_height, 0, w_width);
        hudCamera.updateView();

        // initial setup of matrices
        updateProjection(w_width, w_height);
        updateView();

        // initialise update time with current time
        lastUpdate = System.nanoTime();
        
        init = true;
    }

    @Override
    public boolean isReady()
    {
        return init;
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
    }

    @Override
    public boolean isMouseDown(int button)
    {
        return mouses[button];
    }

    @Override
    public int getMouseX()
    {
        return mouseX;
    }

    @Override
    public int getMouseY()
    {
        return mouseY;
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
    public void prime()
    {
        for (AthensEntity entity : entities.values())
        {
            entity.used = false;
        }
        for (AthensHUD hud : huds.values())
        {
            hud.used = false;
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
    public GfxHUD getGfxHUD(HUDImage image)
    {
        AthensHUD hud = huds.get(image);
        if (hud == null)
        {
            hud = new AthensHUD(image.getImage());
            huds.put(image, hud);
        }
        hud.used = true;
        return hud;
    }

    @Override
    public void post()
    {
        for (Map.Entry<Renderable, AthensEntity> e : entities.entrySet())
        {
            if (!e.getValue().used)
            {
                removed.add(e.getKey());
            }
        }
        for (Map.Entry<HUDImage, AthensHUD> e : huds.entrySet())
        {
            if (!e.getValue().used)
            {
                removedHUD.add(e.getKey());
            }
        }
    }

    @Override
    public int getWidth()
    {
        return w_width;
    }

    @Override
    public int getHeight()
    {
        return w_height;
    }

    @Override
    public AbstractCamera getCamera()
    {
        return camera;
    }

    @Override
    public void setCamera(AbstractCamera camera)
    {
        this.camera = camera;
    }

    @Override
    public GfxEntity getDebugEntity(BBox bbox)
    {
        DebugBox entity = new DebugBox();
        entity.attachResource(resourceManager.getResource(Resource.SHADER, "debug"));
        entity.setV(bbox.getSize());
        entitiesDebug.add(entity);
        return entity;
    }

    @Override
    public GfxEntity getDebugEntity(Vec3[] line)
    {
        DebugLine entity = new DebugLine();
        entity.attachResource(resourceManager.getResource(Resource.SHADER, "debug"));
        entity.set(line);
        entitiesDebug.add(entity);
        return entity;
    }

    @Override
    public void setBackground(Vec3 colour)
    {
        bgColour = colour;
    }
}
