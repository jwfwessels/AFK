package afk.gfx.athens;


import afk.gfx.Camera;
import afk.gfx.GfxEntity;
import afk.gfx.GraphicsEngine;
import afk.gfx.Resource;
import afk.gfx.ResourceNotLoadedException;
import afk.gfx.Updatable;
import afk.gfx.athens.particles.ParticleEmitter;
import com.hackoeur.jglm.*;
import com.jogamp.opengl.util.Animator;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;

public class Athens extends GraphicsEngine
{
    
    // loading queues
    private Queue<AthensResource> loadQueue = new LinkedList<AthensResource>();
    private Queue<AthensResource> unloadQueue = new LinkedList<AthensResource>();
    
    // this gets called when loading is done
    private Runnable onLoadCallback;
    
    // resources
    private Map<String, AthensResource>[] resources
            = new Map[Resource.NUM_RESOURCE_TYPES];
    
    // TODO: decide on list implementation
    private Collection<AthensEntity> entities = new CopyOnWriteArrayList<AthensEntity>();
    
    // TODO: add other resource types here later maybe

    // flag indicating that the load queue has been dispatched
    private boolean loadQueueDispatched = false;
    
    private int w_width, w_height;
    private float aspect;
    
    // TODO: 1024 is enough, but there are some obscure codes in the region of 65000 that WILL make this program crash
    private boolean[] keys = new boolean[1024];
    
    private long lastUpdate;
    private float time = 0.0f;
    private float lastFPS = 0.0f;
    private float fpsInterval = 1.0f;
    
    Camera camera;
    
    Mat4 monkeyWorld, skyboxWorld;
    
    // TODO: move this to a sun/light position
    Vec3 origin_sun = new Vec3(-2.0f,1.5f,5.0f);
    Vec3 sun = new Vec3(origin_sun);
    
    float daytime = 0.0f;
    
    /*Shader sbShader;
    TextureCubeMap skymap;
    SkyBox skybox;*/

    static final float MOUSE_SENSITIVITY = 60.0f;
    /* Amount to move / scale by in one step. */
    static final float DELTA = 5f, ANGLE_DELTA = 30.0f;

    private GLProfile glProfile;
    private GLCapabilities glCaps;
    private GLCanvas glCanvas;
    private Animator animator;
    private String title;
    
    /// TESTING STUFF ///
    
    public Athens(int width, int height, String title, boolean autodraw)
    {
        for (int i = 0; i < resources.length; i++)
            resources[i] = new HashMap<String, AthensResource>();
        
        this.w_width = width;
        this.w_height = height;
        this.title = title;
        
        glProfile = GLProfile.getDefault();
        
        glCaps = new GLCapabilities(glProfile);
        glCaps.setDoubleBuffered(true);
        
        glCanvas = new GLCanvas(glCaps);
        glCanvas.setPreferredSize(new Dimension(width, height));
        
        glCanvas.addKeyListener(new KeyAdapter()
        {
            // TODO: rather make Athens implement KeyListener

            @Override
            public void keyPressed(KeyEvent e) {
                Athens.this.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e)
            {
                Athens.this.keyReleased(e);
            }
            
        });
        
        // TODO: rather make Athens implement MouseListener and MouseMotionListener
        MouseAdapter mouse = new MouseAdapter()
        {
            int cx = glCanvas.getWidth()/2;
            int cy = glCanvas.getHeight()/2;

            @Override
            public void mousePressed(MouseEvent e) {

                cx = e.getX();
                cy = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (e.getX() == cx && e.getY() == cy) return;

                Athens.this.mouseMoved(cx-e.getX(), cy-e.getY());

                cx = e.getX();
                cy = e.getY();
            }
        };
        
        glCanvas.addMouseListener(mouse);
        glCanvas.addMouseMotionListener(mouse);

        glCanvas.addGLEventListener( new GLEventListener() {

            // TODO: rather make Athens implement GLEventListener
            @Override
            public void reshape( GLAutoDrawable glautodrawable, int x, int y, int width, int height ) {
                Athens.this.reshape( glautodrawable.getGL().getGL2(), width, height );
            }
            
            @Override
            public void init( GLAutoDrawable glautodrawable ) {
                GL gl = glautodrawable.getGL().getGL2();
                System.out.println("OpenGL Version: " + gl.glGetString(GL.GL_VERSION));
                Athens.this.init( gl.getGL2() );
            }
            
            @Override
            public void dispose( GLAutoDrawable glautodrawable ) {
                Athens.this.dispose( glautodrawable.getGL().getGL2() );
            }
            
            @Override
            public void display( GLAutoDrawable glautodrawable ) {
                Athens.this.display( glautodrawable.getGL().getGL2());
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

    @Override
    public void redisplay()
    {
        glCanvas.display();
    }
    
    private void dispose(GL2 gl)
    {
        for (int i = 0; i < resources.length; i++)
        {
            for (Map.Entry<String,AthensResource> entry :resources[i].entrySet())
            {
                AthensResource resource = entry.getValue();
                System.out.println("Unloading " + resource);
                resource.unload(gl);
            }
        }
    }

    private void display(GL2 gl)
    {
        
        long nTime = System.nanoTime();
        long nanos = nTime-lastUpdate;
        lastUpdate = nTime;
        float delta = nanos/(float)NANOS_PER_SECOND;
        time += delta;
        lastFPS += delta;
        
        if (lastFPS > fpsInterval)
        {
            // TODO: show FPS somehow :/
            //jFrame.setTitle(title + " - " + (1.0f/delta) + " FPS");
            lastFPS -= fpsInterval;
        }
        
        // TODO: move loading into a loading state rather.
        // This should allow for loading progress bars and such.
        // just put it here for now.
        if (loadQueueDispatched)
        {
            loadResources(gl);
            
            loadQueueDispatched = false;
            
            // notify caller that load is complete
            onLoadCallback.run();
        }
        else
        {
            update(delta);
            
            for (Updatable u :updatables)
                u.update(delta);
            
            render(gl);
        }
    }
    
    // helper function to load/unload all necessary/unnecessary resources
    private void loadResources(GL2 gl)
    {
        
        // unload resources in unload queue
        while (!unloadQueue.isEmpty())
        {
            AthensResource resource = unloadQueue.poll();
            System.out.println("Unloading " + resource);
            
            resources[resource.getType()].remove(resource.getName()).unload(gl);
        }
        
        // load resources in load queue
        while (!loadQueue.isEmpty())
        {
            AthensResource resource = loadQueue.poll();
            System.out.println("Loading " + resource);
            try
            {
                resource.load(gl);
                resources[resource.getType()].put(resource.getName(),resource);
            } catch (IOException ex)
            {
                System.err.println("Unable to load " + resource + ": " + ex.getMessage());
            }
        }
    }
    
    private void update(float delta)
    {
        
        float amount = DELTA*delta;
        
        if (keys[KeyEvent.VK_SHIFT]) amount *= 5;

        if (keys[KeyEvent.VK_W])
        {
           camera.moveForward(amount);
        }
        else if (keys[KeyEvent.VK_S])
        {
           camera.moveForward(-amount);
        }

        if (keys[KeyEvent.VK_D])
        {
           camera.moveRight(amount);
        }
        else if (keys[KeyEvent.VK_A])
        {
           camera.moveRight(-amount);
        }
        else if (keys[KeyEvent.VK_2])
        {
            daytime += 1.0f;
            updateSun();
        }
        else if (keys[KeyEvent.VK_3])
        {
            daytime -= 1.0f;
            updateSun();
        }
        
        updateView();
        
        for (AthensEntity entity : entities)
            entity.update(delta);
    }
    
    private void render(GL2 gl)
    {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        renderScene(gl);

        gl.glFlush();
    }
    
    private void renderScene(GL2 gl)
    {
        renderScene(gl, camera);
    }
    private void renderScene(GL2 gl, Camera cam)
    {
        //renderSkybox(gl, camera, proj);
        //renderFloor(gl, camera, proj);
        //renderMesh(gl, camera, proj);
        
        for (AthensEntity entity :entities)
        {
            entity.draw(gl, cam, sun);
        }
    }
    
    /*void renderSkybox(GL2 gl, Mat4 camera, Mat4 proj)
    {
        gl.glDisable(GL.GL_DEPTH_TEST);
        sbShader.use(gl);
        
        skymap.use(gl, GL.GL_TEXTURE0);
        
        shader.updateUniform(gl, "tex", 0);
        sbShader.updateUniform(gl, "view", camera);
        sbShader.updateUniform(gl, "projection", proj);
        sbShader.updateUniform(gl, "world", skyboxWorld);
        sbShader.updateUniform(gl, "sun", sun);
        
        skybox.draw(gl);
        gl.glEnable(GL.GL_DEPTH_TEST);
    }*/
    
    /*private void renderFloor(GL2 gl, Mat4 camera, Mat4 proj)
    {
        floorShader.use(gl);
        
        //floor.use(gl, GL.GL_TEXTURE0);
        
        //floorShader.updateUniform(gl, "skymap", 0);
        floorShader.updateUniform(gl, "world", new Mat4(1.0f));
        floorShader.updateUniform(gl, "view", camera);
        floorShader.updateUniform(gl, "projection", proj);
        
        quad.draw(gl);
        
    }
    
    private void renderMesh(GL2 gl, Mat4 camera, Mat4 proj)
    {
        tankShader.use(gl);
        
        //tex.use(gl, GL.GL_TEXTURE0);
        
        //shader.updateUniform(gl, "tex", 0);
        tankShader.updateUniform(gl, "world", monkeyWorld);
        tankShader.updateUniform(gl, "view", camera);
        tankShader.updateUniform(gl, "projection", proj);
        
        tankShader.updateUniform(gl, "sun", sun);
        tankShader.updateUniform(gl, "eye", cameraEye); // TODO: not necessary as we don't render specular
        
        tankMesh.draw(gl);
    } */

    private void init(GL2 gl)
    {
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        /*gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);*/
        //gl.glEnable(GL.GL_MULTISAMPLE);
        //gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
        //gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
        
        // set background colour to white
        // TODO: allow this to be set through an interface
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        // initialize camera
        // TODO: allow this to be done through an interface and let additional cameras be set
        camera = new Camera(
                new Vec3(10f, 10f, 10f),
                new Vec3(0f, 0f, 0f),
                new Vec3(0f, 1f, 0f),
                60.0f, 0.1f, 100.0f
            );
        
        // load textures
        // not necessary now
        /*sbShader = new Shader(gl, "skybox");
        skybox = new SkyBox(gl);
        
        try
        {
            skymap = TextureCubeMap.fromFiles(gl, new File[]{
                new File("textures/siege/siege_front.jpg"),
                new File("textures/siege/siege_back.jpg"),
                new File("textures/siege/siege_top.jpg"),
                new File("textures/siege/siege_top.jpg"),
                new File("textures/siege/siege_left.jpg"),
                new File("textures/siege/siege_right.jpg")
            });
            skymap.setParameters(gl, Texture.texParamsSkyMap);
            
            floor = Texture2D.fromFile(gl, new File("textures/floor.jpg"));
            floor.setParameters(gl, Texture.texParamsDefault);
            
            tex = Texture2D.fromFile(gl, new File("textures/tank.jpg"));
            tex.setParameters(gl, Texture.texParamsDefault);
            
        } catch (IOException ex)
        {
            System.err.println("Could not load texture: " + ex.toString());
        }*/

        // initial setup of matrices
        updateProjection(w_width, w_height);
        updateView();
        
        // initialise update time with current time
        lastUpdate = System.nanoTime();
    }

    private void keyPressed(KeyEvent ke)
    {
        char key = ke.getKeyChar();
        if (ke.getKeyCode() < keys.length)
            keys[ke.getKeyCode()] = true;

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
            keys[ke.getKeyCode()] = false;
    }

    private void mouseMoved(int x, int y)
    {
        final float LEFT_RIGHT_ROT = (2.0f*(float)x/(float)w_width) * MOUSE_SENSITIVITY;
        final float UP_DOWN_ROT = (2.0f*(float)y/(float)w_height) * MOUSE_SENSITIVITY;

        camera.rotate(LEFT_RIGHT_ROT, UP_DOWN_ROT);
        
        updateView();

    }

    @Override
    public boolean isMouseDown(int button)
    {
        // TODO: implement isMouseDown
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMouseX()
    {
        // TODO: implement getMouseX
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getMouseY()
    {
        // TODO: implement getMouseY
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
        aspect = (float) width / (float) height;

        camera.updateProjection(aspect);
    }

    private void updateView()
    {
        camera.updateView();
        
        // shift skybox with camera
        skyboxWorld = new Mat4(1.0f);
        skyboxWorld = Matrices.translate(skyboxWorld, camera.eye);
        // also scale it a bit so it doesn't intersect with near clip
        skyboxWorld = Matrices.scale(skyboxWorld, new Vec3(1.5f,1.5f,1.5f));
    }
    
    private void updateSun()
    {
        Mat4 sunWorld = new Mat4(1.0f);
        
        sunWorld = Matrices.rotate(sunWorld, daytime, new Vec3(1,0.2f,0).getUnitVector());
        
        Vec4 sun4 = sunWorld.multiply(new Vec4(origin_sun, 0.0f));
        
        sun = new Vec3(sun4.getX(),sun4.getY(), sun4.getZ());
    }

    @Override
    public Resource loadResource(int type, String name)
    {
        if (loadQueueDispatched)
        {
            // TODO: throw something about already dispatching a load queue
            return null;
        }
        
        AthensResource resource = resources[type].get(name);
        
        // don't bother loading something that's already loaded
        if (resource != null)
            return resource;
            
        resource = AthensResource.create(type, name);
        
        if (!unloadQueue.remove(resource))
        {
            loadQueue.add(resource);
        }
        
        return resource;
    }

    @Override
    public void unloadResource(Resource resource)
    {
        if (loadQueueDispatched)
        {
            // TODO: throw something about already dispatching a load queue
            return;
        }
        
        // can't unload resource that isn't loaded
        if (!resources[resource.getType()].containsKey(resource.getName()))
            return;
        
        if (!loadQueue.remove((AthensResource)resource))
        {
            unloadQueue.add((AthensResource)resource);
        }
    }

    @Override
    public void unloadEverything()
    {
        if (loadQueueDispatched)
        {
            // TODO: throw something about already dispatching a load queue
            return;
        }
        
        loadQueue.clear();
        
        // add every single loaded resource to unload queue
        for (int i = 0; i < resources.length; i++)
        {
            for (Map.Entry<String,AthensResource> entry :resources[i].entrySet())
            {
                unloadQueue.add(entry.getValue());
            }
        }
    }

    @Override
    public GfxEntity createEntity(int behaviour)
    {
        // TODO: research possible performance hit is we use Class.newInstance() instead of using ints
        
        AthensEntity entity;
        switch (behaviour)
        {
            case GfxEntity.NORMAL:
                entity = new AthensEntity();
                break;
            case GfxEntity.BILLBOARD_SPHERICAL:
                entity = new Billboard(true);
                break;
            case GfxEntity.BILLBOARD_CYLINDRICAL:
                entity = new Billboard(false);
                break;
            case GfxEntity.PARTICLE_EMITTER:
                entity = new ParticleEmitter();
                break;
            default:
                // TODO: throw new InvalidEntityBehaviourException();
                return null;
        }
        
        entities.add(entity);
        
        return entity;
    }

    @Override
    public void deleteEntity(GfxEntity entity)
    {
        entities.remove((AthensEntity)entity);
    }

    @Override
    public void attachResource(GfxEntity entity, Resource resource)
            throws ResourceNotLoadedException
    {
        AthensEntity athensEntity = (AthensEntity)entity;
        
        if (resources[resource.getType()].containsKey(resource.getName()))
        {
            athensEntity.attachResource((AthensResource)resource);
        }
        else throw new ResourceNotLoadedException(resource);
    }

    @Override
    public void dispatchLoadQueue(Runnable callback)
    {
        if (loadQueue == null || loadQueue.isEmpty())
        {
            // TODO: throw something
        }
        loadQueueDispatched = true;
        onLoadCallback = callback;
    }
    
}
