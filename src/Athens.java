
import com.hackoeur.jglm.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;

public class Athens
{

    /**
     * Character code for the escape key in GLUT.
     */
    static final int KEY_ESCAPE = 27;
    /**
     * The window's initial width.
     */
    static final int WINDOW_WIDTH = 1280;
    /**
     * The window's initial height.
     */
    static final int WINDOW_HEIGHT = 768;
    
    int w_width = WINDOW_WIDTH, w_height = WINDOW_HEIGHT;
    float aspect;
    
    //Texture tex;
    Shader shader;
    Mesh mesh;
    
    //Texture2D floor;
    Shader floorShader;
    Quad quad;
    
    /*Shader sbShader;
    TextureCubeMap skymap;
    SkyBox skybox;*/

    static final float MOUSE_SENSITIVITY = 60.0f;
    /* Amount to move / scale by in one step. */
    static final float DELTA = 5f, ANGLE_DELTA = 30.0f;

    /* Amount of rotation around the x axis. */
    float xRot = 0.0f;
    /* Amount of rotation around the y axis. */
    float yRot = 0.0f;
    /* Amount of rotation around the z axis. */
    float zRot = 0.0f;
    
    float camRotX = 0.0f;
    float camRotY = 0.0f;

    /* Amount to scale the x axis by. */
    float xScale = 1.0f;
    /* Amount to scale the y axis by. */
    float yScale = 1.0f;
    /* Amount to scale the z axis by. */
    float zScale = 1.0f;

    /* Amount to move on the x axis. */
    float xMove = 0.0f;
    /* Amount to move on the y axis. */
    float yMove = 0.0f;
    /* Amount to move on the z axis. */
    float zMove = 0.0f;

    /* Camera location. */
    Vec3 cameraEye = new Vec3(2f, 2f, 2f);
    /* Vector the camera is looking along. */
    Vec3 cameraAt = new Vec3(0f, 0f, 0f);
    /* Up direction from the camera. */
    Vec3 cameraUp = new Vec3(0f, 1f, 0f);

    /* Vertical field-of-view. */
    float FOVY = 60.0f;
    /* Near clipping plane. */
    float NEAR = 0.1f;
    /* Far clipping plane. */
    float FAR = 200.0f;
    
    Mat4 monkeyWorld, skyboxWorld;
           
    Mat4 view, projection;
    
    Vec3 origin_sun = new Vec3(-2.0f,1.5f,5.0f);
    Vec3 sun = new Vec3(origin_sun);
    
    float daytime = 0.0f;
    
    boolean[] keys = new boolean[256];
    
    long lastUpdate;
    float time = 0.0f;
    float lastFPS = 0.0f;
    float fpsInterval = 1.0f;
            
    public static final long NANOS_PER_SECOND = 1000000000l;
    
    void cleanUp(GL2 gl)
    {
        // TODO: release meshes and shaders here. EEEK!!!
    }

    void display(GL2 gl)
    {
        update();
        render(gl);
    }
    
    void update()
    {
        long nTime = System.nanoTime();
        long nanos = nTime-lastUpdate;
        lastUpdate = nTime;
        float delta = nanos/(float)NANOS_PER_SECOND; 
        time += delta;
        lastFPS += delta;
        
        if (lastFPS > fpsInterval)
        {
            Main.setTitle(1.0f/delta + " FPS");
            lastFPS -= fpsInterval;
        }
        
        float step = DELTA;
        
        if (keys[KeyEvent.VK_SHIFT]) step *= 5;
        
        Vec3 d = cameraAt.subtract(cameraEye).getUnitVector().multiply(step*delta);
        Vec3 r = d.cross(cameraUp).getUnitVector().multiply(step*delta);

        if (keys[KeyEvent.VK_W])
        {
           cameraEye = cameraEye.add(d);
           cameraAt = cameraAt.add(d);
        }
        else if (keys[KeyEvent.VK_S])
        {
           cameraEye = cameraEye.subtract(d);
           cameraAt = cameraAt.subtract(d);
        }

        if (keys[KeyEvent.VK_D])
        {
           cameraEye = cameraEye.add(r);
           cameraAt = cameraAt.add(r);
        }
        else if (keys[KeyEvent.VK_A])
        {
           cameraEye = cameraEye.subtract(r);
           cameraAt = cameraAt.subtract(r);
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
        
        float angle = -(float)Math.toRadians(yRot);
        float sin = (float)Math.sin(angle);
        float cos = (float)Math.cos(angle);
        float tdelta = 5*delta;
        
        if (keys[KeyEvent.VK_UP])
        {
            xMove += -(tdelta*sin);
            zMove += (tdelta*cos);
        }
        else if (keys[KeyEvent.VK_DOWN])
        {
            xMove -= -(tdelta*sin);
            zMove -= (tdelta*cos);
        }
        if (keys[KeyEvent.VK_LEFT])
        {
            yRot += tdelta*ANGLE_DELTA;
        }
        else if (keys[KeyEvent.VK_RIGHT])
        {
            yRot -= tdelta*ANGLE_DELTA;
        }
        updateMonkeyWorld();
        
        updateView();
    }
    
    void render(GL2 gl)
    {
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        renderScene(gl);

        gl.glFlush();
    }
    
    void renderScene(GL2 gl)
    {
        renderScene(gl, view);
    }
    void renderScene(GL2 gl, Mat4 camera)
    {
        renderScene(gl, camera, projection);
    }
    void renderScene(GL2 gl, Mat4 camera, Mat4 proj)
    {
        //renderSkybox(gl, camera, proj);
        renderFloor(gl, camera, proj);
        renderMesh(gl, camera, proj);
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
    
    void renderFloor(GL2 gl, Mat4 camera, Mat4 proj)
    {
        floorShader.use(gl);
        
        //floor.use(gl, GL.GL_TEXTURE0);
        
        //floorShader.updateUniform(gl, "skymap", 0);
        floorShader.updateUniform(gl, "world", new Mat4(1.0f));
        floorShader.updateUniform(gl, "view", camera);
        floorShader.updateUniform(gl, "projection", proj);
        
        quad.draw(gl);
        
    }
    
    void renderMesh(GL2 gl, Mat4 camera, Mat4 proj)
    {
        shader.use(gl);
        
        //tex.use(gl, GL.GL_TEXTURE0);
        
        //shader.updateUniform(gl, "tex", 0);
        shader.updateUniform(gl, "world", monkeyWorld);
        shader.updateUniform(gl, "view", camera);
        shader.updateUniform(gl, "projection", proj);
        
        shader.updateUniform(gl, "sun", sun);
        shader.updateUniform(gl, "eye", cameraEye); // TODO: not necessary as we don't render specular
        
        mesh.draw(gl);
    }

    void init(GL2 gl)
    {
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL.GL_CULL_FACE);
        /*gl.glEnable(GL.GL_BLEND);
        gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);*/
        //gl.glEnable(GL.GL_MULTISAMPLE);
        //gl.glEnable(GL.GL_TEXTURE_CUBE_MAP);
        //gl.glPolygonMode( GL.GL_FRONT_AND_BACK, GL2.GL_LINE );
        
        // set background colour to white
        gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        
        // load the tank model
        String meshname = "tank.obj";
        try
        {
            mesh = new WavefrontMesh(gl, meshname);
        } catch (IOException ex)
        {
            System.out.println("Error reading " + meshname + ": " + ex.toString());
        }
        
        // load shader for tank
        shader = new Shader(gl, "monkey");
        
        // create the floor
        quad = new Quad(gl, 100, 100, 0);
        
        // load shader for floor
        floorShader = new Shader(gl, "floor");
        
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
        updateProjection(WINDOW_WIDTH, WINDOW_HEIGHT);
        updateView();
        updateMonkeyWorld();
        
        // initialise update time with current time
        lastUpdate = System.nanoTime();
    }

    void keyPressed(KeyEvent ke)
    {
        char key = ke.getKeyChar();
        if (ke.getKeyCode() < keys.length)
            keys[ke.getKeyCode()] = true;

        switch (key)
        {
            case KEY_ESCAPE:
                System.out.printf("Bye!\n");
                System.exit(0);
                break;
            default:
                break;
        }
    }
    
    void keyReleased(KeyEvent ke)
    {
        if (ke.getKeyCode() < keys.length)
            keys[ke.getKeyCode()] = false;
    }

    void mouseMoved(int x, int y)
    {
        final float LEFT_RIGHT_ROT = (2.0f*(float)x/(float)w_width) * MOUSE_SENSITIVITY;
        final float UP_DOWN_ROT = (2.0f*(float)y/(float)w_height) * MOUSE_SENSITIVITY;

        Vec3 tempD = cameraAt.subtract(cameraEye);
        Vec4 d = new Vec4(tempD.getX(), tempD.getY(), tempD.getZ(), 0.0f);

        Vec3 right = tempD.cross(cameraUp);

        Mat4 rot = new Mat4(1.0f);
        rot = Matrices.rotate(rot, UP_DOWN_ROT, right);
        rot = Matrices.rotate(rot, LEFT_RIGHT_ROT, cameraUp);

        d = rot.multiply(d);

        cameraAt = cameraEye.add(new Vec3(d.getX(),d.getY(),d.getZ()));
        
        updateView();

    }

    void reshape(GL2 gl, int newWidth, int newHeight)
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

    void updateProjection(int width, int height)
    {
        aspect = (float) width / (float) height;

        projection = Matrices.perspective(FOVY, aspect, NEAR, FAR);
    }

    void updateView()
    {
        view = Matrices.lookAt(cameraEye, cameraAt, cameraUp);
        
        // shift skybox with camera
        skyboxWorld = new Mat4(1.0f);
        skyboxWorld = Matrices.translate(skyboxWorld, cameraEye);
        // also scale it a bit so it doesn't intersect with near clip
        skyboxWorld = Matrices.scale(skyboxWorld, new Vec3(1.5f,1.5f,1.5f));
    }

    void updateMonkeyWorld()
    {
        monkeyWorld = new Mat4(1f);

        Vec3 translation = new Vec3(xMove, yMove, zMove);
        monkeyWorld = Matrices.translate(monkeyWorld, translation);

        Vec3 yAxis = new Vec3(0, 1, 0);
        monkeyWorld = Matrices.rotate(monkeyWorld, yRot, yAxis);
        
        Vec3 xAxis = new Vec3(1, 0, 0);
        monkeyWorld = Matrices.rotate(monkeyWorld, xRot, xAxis);

        Vec3 zAxis = new Vec3(0, 0, 1);
        monkeyWorld = Matrices.rotate(monkeyWorld, zRot, zAxis);

        Vec3 scales = new Vec3(xScale, yScale, zScale);
        monkeyWorld = Matrices.scale(monkeyWorld, scales);
    }
    
    void updateSun()
    {
        Mat4 sunWorld = new Mat4(1.0f);
        
        sunWorld = Matrices.rotate(sunWorld, daytime, new Vec3(1,0.2f,0).getUnitVector());
        
        Vec4 sun4 = sunWorld.multiply(new Vec4(origin_sun, 0.0f));
        
        sun = new Vec3(sun4.getX(),sun4.getY(), sun4.getZ());
    }
}
